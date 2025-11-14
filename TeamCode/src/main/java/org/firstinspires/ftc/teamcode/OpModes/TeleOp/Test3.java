package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@TeleOp(name = "Test3")
public class Test3 extends OpMode
{
    private Drivetrain dt;
    private Servo kicker;
    private Servo hood;
    private DcMotor intake;
    private DcMotor turret;
    private DcMotor transfer;
    private DcMotor shooter;

    private GamepadEx driver1;
    private GamepadEx driver2;

    @Override
    public void init()
    {
        dt= new Drivetrain(hardwareMap, telemetry);
        dt.follower.startTeleopDrive();
        intake= hardwareMap.get(DcMotor.class,"intake");
        turret= hardwareMap.get(DcMotor.class,"turret");
        transfer= hardwareMap.get(DcMotor.class,"transfer");
        shooter= hardwareMap.get(DcMotor.class,"shooter");
        kicker= hardwareMap.get(Servo.class,"kicker");
        hood= hardwareMap.get(Servo.class,"hood");
        driver1= new GamepadEx(gamepad1);
        driver2= new GamepadEx(gamepad2);
        kicker.setPosition(0);
        hood.setPosition(0);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void loop()
    {
        dt.setMovementVectors(driver2.getLeftX(),driver2.getLeftY(), driver2.getRightX(),false);
        dt.periodic();

        if(driver2.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON))
        {
            dt.resetHeading();
        }

        if(gamepad2.a)
        {
            intake.setPower(1);
        }
        else if(gamepad2.b)
        {
            intake.setPower(-1);
        }
        else
        {
            intake.setPower(0);
        }

        if(gamepad2.x)
        {
            turret.setPower(0.9);
        }
        else if(gamepad2.y)
        {
            turret.setPower(-0.9);
        }
        else
        {
            turret.setPower(0);
        }

        if(gamepad2.right_bumper)
        {
            shooter.setPower(1);
        }
        else if(gamepad2.left_bumper)
        {
            shooter.setPower(-1);
        }
        else
        {
            shooter.setPower(0);
        }

        if(gamepad2.dpad_right)
        {
            transfer.setPower(1);
        }
        else
        {
            transfer.setPower(0);
        }
        if(gamepad2.dpad_left)
        {
            transfer.setPower(-1);
        }
        else
        {
            transfer.setPower(0);
        }
        if(gamepad2.dpad_up)
        {
            kicker.setPosition(0.35);
        }
        if(gamepad2.dpad_down)
        {
            kicker.setPosition(0.02);
        }
        if(gamepad2.touchpad)
        {
            hood.setPosition(1);
        }
        if(gamepad2.left_stick_button)
        {
            hood.setPosition(0.2);
        }

    }

}