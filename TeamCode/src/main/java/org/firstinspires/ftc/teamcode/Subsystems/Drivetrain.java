package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.DrivetrainConstants.startPose;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizer;
import com.pedropathing.geometry.Pose;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import com.seattlesolvers.solverslib.geometry.Pose2d;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Utilities.Constants.DrivetrainConstants;
import org.firstinspires.ftc.teamcode.Utilities.Constants.GlobalConstants;
import org.firstinspires.ftc.teamcode.Utilities.Constants.GlobalConstants.OpModeType;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Drivetrain extends SubsystemBase {
   // private Motor leftFront;
   // private Motor rightFront;
   // private Motor leftRear;
   // private Motor rightRear;

    public Follower follower;
    public static Pose startPose= new Pose(0,0,0);

    private Telemetry telemetry;

    public Drivetrain(HardwareMap aHardwareMap, Telemetry telemetry) {
      /*  leftFront = new Motor(aHardwareMap, DrivetrainConstants.fLMotorID, Motor.GoBILDA.RPM_312);
        rightFront = new Motor(aHardwareMap, DrivetrainConstants.fRMotorID, Motor.GoBILDA.RPM_312);
        leftRear = new Motor(aHardwareMap, DrivetrainConstants.bLMotorID, Motor.GoBILDA.RPM_312);
        rightRear = new Motor(aHardwareMap, DrivetrainConstants.bRMotorID, Motor.GoBILDA.RPM_312);

        leftFront.setRunMode(Motor.RunMode.RawPower);
        rightFront.setRunMode(Motor.RunMode.RawPower);
        leftRear.setRunMode(Motor.RunMode.RawPower);
        rightRear.setRunMode(Motor.RunMode.RawPower);

        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        leftFront.setInverted(true);
        rightFront.setInverted(true);
        leftRear.setInverted(false);
        rightRear.setInverted(false); */



        follower = Constants.createFollower(aHardwareMap);

        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        follower.updatePose();
        follower.update();
        telemetry.addData("Drivetrain Pose X", follower.getPose().getX());
        telemetry.addData("Drivetrain Pose Y", follower.getPose().getY());
        telemetry.addData("Drivetrain Heading", follower.getPose().getHeading());
    }

    public void setMovementVectors(double strafe, double forward, double rotation, boolean feildCentric) {

        follower.setTeleOpDrive(forward, -strafe, -rotation, !feildCentric);
    }

    public void setMovementVectors(double strafe, double forward, double rotation) {
        follower.setTeleOpDrive(forward, -strafe, -rotation, false);
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
    }
    public void setStartingPose(Pose pose)
    {
        startPose=pose;
    }

    public void enableTeleop() {
        follower.setStartingPose(startPose);
        follower.startTeleopDrive(true);
    }

    public void resetHeading()
    {
        follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), Math.toRadians(0)));
    }

    public void hold()
    {
        follower.holdPoint(follower.getPose());
    }

    public double getHeading()
    {
        return follower.getPose().getHeading();
    }

    public double getX()
    {
        return follower.getPose().getX();
    }

    public double getY()
    {
        return follower.getPose().getY();
    }
}

