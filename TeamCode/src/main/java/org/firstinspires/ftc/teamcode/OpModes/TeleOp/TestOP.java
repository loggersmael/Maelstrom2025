package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@TeleOp
public class TestOP extends OpMode
{
    private Drivetrain dt;
    private Motor intake;
    private Motor turret;
    private Motor transfer;
    private Motor shooter;

    private GamepadEx driver1;
    private GamepadEx driver2;

    @Override
    public void init()
    {
       dt= new Drivetrain(hardwareMap, telemetry);
       intake= new Motor(hardwareMap,"intake");
       turret= new Motor(hardwareMap,"turret");
       transfer= new Motor(hardwareMap, "transfer");
       shooter= new Motor(hardwareMap, "hang");
       intake.setRunMode(Motor.RunMode.RawPower);
       turret.setRunMode(Motor.RunMode.RawPower);
       transfer.setRunMode(Motor.RunMode.RawPower);
       shooter.setRunMode(Motor.RunMode.RawPower);
       driver1= new GamepadEx(gamepad1);
       driver2= new GamepadEx(gamepad2);
    }

    public void loop()
    {
        dt.setMovementVectors(driver2.getLeftX(),driver2.getLeftY(), driver2.getRightX(),false);
        dt.periodic();

        if(driver2.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON))
        {
            dt.resetHeading();
        }

        if(driver2.getButton(GamepadKeys.Button.A))
        {
            intake.set(1);
        }
        else
        {
            intake.set(0);
        }
        if(driver2.getButton(GamepadKeys.Button.B))
        {
            intake.set(-1);
        }
        else
        {
            intake.set(0);
        }
        if(driver2.getButton(GamepadKeys.Button.X))
        {
            turret.set(0.1);
        }
        else
        {
            intake.set(0);
        }
        if(driver2.getButton(GamepadKeys.Button.Y))
        {
            turret.set(-0.1);
        }
        else
        {
            intake.set(0);
        }
        if(driver2.getButton(GamepadKeys.Button.RIGHT_BUMPER))
        {
            shooter.set(1);
        }
        else
        {
            shooter.set(0);
        }
        if(driver2.getButton(GamepadKeys.Button.LEFT_BUMPER))
        {
            shooter.set(-1);
        }
        else
        {
            shooter.set(0);
        }
        if(driver2.getButton(GamepadKeys.Button.DPAD_RIGHT))
        {
            transfer.set(1);
        }
        else
        {
            transfer.set(0);
        }
        if(driver2.getButton(GamepadKeys.Button.DPAD_LEFT))
        {
            transfer.set(-1);
        }
        else
        {
            transfer.set(0);
        }
    }

}
