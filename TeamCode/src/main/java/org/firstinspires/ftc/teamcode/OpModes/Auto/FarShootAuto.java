
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
            robot.shooter.toggleFlywheel();

        }
    }
}
