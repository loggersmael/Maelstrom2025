package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

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
    public Transfer kicker;
    public GamepadEx driver1;
    public GamepadEx driver2;

    public Maelstrom(HardwareMap hMap, Telemetry telemetry, Alliance color, Gamepad d1, Gamepad d2)
    {
        dt= new Drivetrain(hMap,telemetry);
        kicker= new Transfer(hMap,telemetry);
        intake= new Intake(hMap,telemetry);
        shooter= new Shooter(hMap,telemetry,color);
        turret= new SimpleTurret(hMap,telemetry,color);
        driver1= new GamepadEx(d1);
        driver2= new GamepadEx(d2);
    }

    public void periodic()
    {
        dt.periodic();
        kicker.periodic();
        intake.periodic();
        shooter.periodic();
        turret.periodic();
    }

    public void controlMap()
    {
        dt.setMovementVectors(driver1.getLeftX(), driver1.getLeftY(), driver1.getRightX(),true);
        if(driver1.getButton(GamepadKeys.Button.TOUCHPAD))
        {
            dt.resetHeading();
        }

        if(driver2.isDown(GamepadKeys.Button.RIGHT_BUMPER))
        {

        }
    }
}
