package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Robot;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Maelstrom extends Robot
{
    public enum Alliance{
        RED,BLUE;
    }
    public Drivetrain dt;
    public Intake intake;
    public Shooter shooter;
    public Turret turret;
    public Vision cams;
    public GamepadEx driver1;
    public GamepadEx driver2;
    private Telemetry telemetry;
    private int transferState=-1;
    private Timer tTimer;

    public Maelstrom(HardwareMap hMap, Telemetry telemetry, Alliance color, Gamepad d1, Gamepad d2)
    {
        dt= new Drivetrain(hMap,telemetry);
        this.telemetry=telemetry;
        intake= new Intake(hMap,telemetry);
        intake.stop();
        shooter= new Shooter(hMap,telemetry,color);
        turret= new Turret(hMap,telemetry);
        cams= new Vision(hMap,telemetry,color);
        driver1= new GamepadEx(d1);
        driver2= new GamepadEx(d2);
        tTimer=new Timer();
        this.telemetry=telemetry;
        register(dt,intake,shooter,turret,cams);

    }

    public void periodic()
    {
        dt.periodic();
        cams.periodic();
        intake.periodic();
        shooter.periodic();
        turret.periodic();
        turret.getTargetAngle(cams.getTargetX(),cams.targetPresent());
        transferAndShoot();
    }

    @Override
    public void reset()
    {
        intake.stop();
        shooter.flywheelOn=false;
        Drivetrain.startPose=dt.follower.getPose();
    }

    public void controlMap()
    {
        dt.setMovementVectors(driver1.getLeftX(), driver1.getLeftY(), driver1.getRightX(),true);
        if(driver1.getButton(GamepadKeys.Button.TOUCHPAD))
        {
            dt.resetHeading();
        }

        //turret.turretWithManualLimits(-driver2.getLeftX());


        if(driver1.getButton(GamepadKeys.Button.RIGHT_BUMPER))
        {
            startTransfer();
        }
        if(driver1.getButton(GamepadKeys.Button.LEFT_BUMPER))
        {
            cancelTransfer();
        }


        if(driver1.getButton(GamepadKeys.Button.LEFT_BUMPER))
        {

        }

        if(driver2.getButton(GamepadKeys.Button.RIGHT_BUMPER))
        {
            shooter.flywheelOn=true;
        }
        if(driver2.getButton(GamepadKeys.Button.LEFT_BUMPER))
        {
            shooter.flywheelOn=false;
        }

        if(driver2.getButton(GamepadKeys.Button.Y))
        {
            shooter.shootFar();
        }

        if(driver2.getButton(GamepadKeys.Button.B))
        {
            shooter.shootClose();
        }
        if(driver2.getButton(GamepadKeys.Button.X))
        {
            shooter.shootMid();
        }

        if(transferState==-1) {
            if (driver2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
            {
                intake.spinOut();
            }
            else if (driver2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
            {
                intake.spinIn();
            }
            else
            {
                intake.stop();
            }

            if (driver2.getButton(GamepadKeys.Button.DPAD_UP))
            {
                intake.kickerUp();
            }
            else if (!driver2.getButton(GamepadKeys.Button.DPAD_RIGHT))
            {
                intake.kickerDown();
            }

            if (driver2.getButton(GamepadKeys.Button.DPAD_RIGHT))
            {
                intake.kickerHalfway();
            } 
            else if (!driver2.getButton(GamepadKeys.Button.DPAD_UP))
            {
                intake.kickerDown();
            }
        }

        if(!shooter.flywheelOn)
        {
            if(driver2.getButton(GamepadKeys.Button.DPAD_DOWN))
            {
                shooter.reverseWheel();
            }
            else
            {
                shooter.stopFlywheel();
            }
        }

        if(driver1.getButton(GamepadKeys.Button.DPAD_DOWN))
        {
            shooter.setHoodServo(0);
        }
        if(driver1.getButton(GamepadKeys.Button.DPAD_UP))
        {
            shooter.setHoodServo(0.5);
        }
    }

    private void setState(int x)
    {
        transferState=x;
        tTimer.resetTimer();
    }
    private void startTransfer()
    {
        setState(1);
    }
    private void cancelTransfer()
    {
        setState(-1);
    }

    private void transferAndShoot()
    {
        switch(transferState)
        {
            case 1:
                intake.slowSpinOut();
                setState(2);
                break;
            case 2:
                if(tTimer.getElapsedTimeSeconds()>0.2)
                {
                    intake.stop();
                    setState(3);
                }
                break;
            case 3:
                if(tTimer.getElapsedTimeSeconds()>0.1)
                {
                    intake.kickerUp();
                    setState(4);
                }
                break;
            case 4:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    intake.kickerDown();
                    setState(5);
                }
                break;
            case 5:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    intake.spinIn();
                    setState(6);
                }
                break;
            case 6:
                if(tTimer.getElapsedTimeSeconds()>1 || intake.ballReady())
                {
                    intake.stop();
                    setState(7);
                }
                break;
            case 7:
                if(tTimer.getElapsedTimeSeconds()>0.5 && shooter.atSpeed())
                {
                    intake.kickerUp();
                    setState(8);
                }
                break;
            case 8:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    intake.kickerDown();
                    setState(9);
                }
                break;
            case 9:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    intake.spinIn();
                    setState(10);
                }
                break;
            case 10:
                if((tTimer.getElapsedTimeSeconds()>2 || intake.ballReady()) && shooter.atSpeed())
                {
                    intake.kickerUp();
                    setState(11);
                }
                break;
            case 11:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    intake.kickerDown();
                    intake.stop();
                    setState(-1);
                }
                break;

        }
    }
}
