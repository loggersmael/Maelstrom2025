package org.firstinspires.ftc.teamcode.OpModes.Auto;

import static java.lang.Thread.sleep;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@Autonomous(name="LeaveAuto")
public class LeaveAuto extends OpMode
{

    private Maelstrom robot;
    private Timer time;
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;


    @Override
    public void init()
    {
       // robot= new Maelstrom(hardwareMap, telemetry, Maelstrom.Alliance.BLUE, gamepad1, gamepad2);
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
    }

    @Override
    public void start()
    {
        time.resetTimer();
        frontLeft.setPower(1);
        frontRight.setPower(1);
        backLeft.setPower(1);
        backRight.setPower(1);
        //robot.dt.setMovementVectors(0,1,0,false);
    }

    @Override
    public void loop()
    {
        if(time.getElapsedTimeSeconds()>0.5)
        {
            //robot.dt.setMovementVectors(0,0,0,false);
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
        }
    }

}
