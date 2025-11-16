
package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

import java.lang.reflect.MalformedParameterizedTypeException;

@Autonomous(name="FarShootAuto")
public class FarShootAuto extends LinearOpMode
{

    private Maelstrom robot;
    private Timer time;
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    @Override
    public void runOpMode()
    {
        robot= new Maelstrom(hardwareMap, telemetry, Maelstrom.Alliance.BLUE, gamepad1, gamepad2);
        time= new Timer();
        //robot.dt.enableTeleop();
        frontLeft= hardwareMap.get(DcMotor.class,"frontLeft");
        frontRight= hardwareMap.get(DcMotor.class,"frontRight");
        backLeft= hardwareMap.get(DcMotor.class,"backLeft");
        backRight= hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        if(opModeIsActive())
        {
            time.resetTimer();
            robot.shooter.shootFarAuto();
            while(time.getElapsedTimeSeconds()<2)
            {
                robot.shooter.flywheelOn=true;
                robot.shooter.periodic();
            }
            time.resetTimer();
            while(time.getElapsedTimeSeconds()<2)
            {
                    robot.intake.spinIn();
                robot.shooter.periodic();
            }
            time.resetTimer();
            while(time.getElapsedTimeSeconds()<2)
            {
                robot.intake.stop();
                robot.intake.kickerUp();
                robot.shooter.periodic();
            }
            time.resetTimer();
            while(time.getElapsedTimeSeconds()<0.5)
            {
                robot.intake.kickerDown();
                robot.shooter.periodic();
            }
            time.resetTimer();
            while(time.getElapsedTimeSeconds()<5)
            {
                robot.intake.spinIn();
                robot.shooter.periodic();
            }
            time.resetTimer();
            while(time.getElapsedTimeSeconds()<2)
            {
                robot.intake.kickerUp();
                robot.shooter.periodic();
            }
            time.resetTimer();
            while(time.getElapsedTimeSeconds()<0.5)
            {
                robot.intake.kickerDown();
                robot.intake.stop();
                robot.shooter.periodic();
            }
            time.resetTimer();
            robot.shooter.flywheelOn=false;
            time.resetTimer();
            while(time.getElapsedTimeSeconds()<0.5)
            {
                frontLeft.setPower(1);
                frontRight.setPower(1);
                backLeft.setPower(1);
                backRight.setPower(1);
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
        }
    }
}
