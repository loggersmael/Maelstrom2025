package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Maelstrom
{
    public enum Alliance{
        RED,BLUE;
    }
    public Drivetrain dt;
    public Intake intake;
    public Shooter shooter;
    public SimpleTurret turret;
    public GamepadEx driver1;
    public GamepadEx driver2;
    private Telemetry telemetry;

    public Maelstrom(HardwareMap hMap, Telemetry telemetry, Alliance color, Gamepad d1, Gamepad d2)
    {
        dt= new Drivetrain(hMap,telemetry);
        this.telemetry=telemetry;
        intake= new Intake(hMap,telemetry);
        shooter= new Shooter(hMap,telemetry,color);
        turret= new SimpleTurret(hMap,telemetry,color);
        driver1= new GamepadEx(d1);
        driver2= new GamepadEx(d2);
    }

    public void periodic()
    {
        dt.periodic();
        intake.periodic();
        shooter.periodic();
        turret.periodic();
        telemetry.update();
    }

    public void controlMap()
    {
        dt.setMovementVectors(driver1.getLeftX(), driver1.getLeftY(), driver1.getRightX(),true);
        if(driver1.getButton(GamepadKeys.Button.TOUCHPAD))
        {
            dt.resetHeading();
        }

        if(driver2.getButton(GamepadKeys.Button.RIGHT_BUMPER))
        {
            shooter.toggleFlywheel();
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

        if(driver2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>0.5)
        {
            intake.spinOut();
        }
        else if(driver2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>0.5)
        {
            intake.spinIn();
        }
        else
        {
            intake.stop();
        }

        if(driver2.getButton(GamepadKeys.Button.DPAD_UP))
        {
            intake.kickerUp();
        }
        else
        {
            intake.kickerDown();
        }

        if(driver2.getButton(GamepadKeys.Button.DPAD_RIGHT))
        {
            intake.kickerHalfway();
        }
        else
        {
            intake.kickerDown();
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
    }
}
