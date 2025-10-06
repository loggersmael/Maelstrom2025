package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.DrivetrainConstants.startPose;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizer;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import com.seattlesolvers.solverslib.geometry.Pose2d;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.solversHardware.SolversMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Utilities.Constants.DrivetrainConstants;
import org.firstinspires.ftc.teamcode.Utilities.Constants.GlobalConstants;
import org.firstinspires.ftc.teamcode.Utilities.Constants.GlobalConstants.OpModeType;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Drivetrain extends SubsystemBase {
    private Motor leftFront;
    private Motor rightFront;
    private Motor leftRear;
    private Motor rightRear;


    public Follower follower;
    private Pose reset;

    private Telemetry telemetry;

    public Drivetrain(HardwareMap aHardwareMap, Telemetry telemetry) {
        leftFront = new Motor(aHardwareMap, DrivetrainConstants.fLMotorID, Motor.GoBILDA.RPM_312);
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

        leftFront.setInverted(false);
        rightFront.setInverted(false);
        leftRear.setInverted(false);
        rightRear.setInverted(true);



        follower = new Follower(aHardwareMap);
        poseUpdater = new PoseUpdater(aHardwareMap);

        reset= new Pose(0,0,0);

        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        follower.update();
        reset.setX(follower.getPose().getX());
        reset.setY(follower.getPose().getY());
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

    public void enableTeleop() {
        follower.startTeleopDrive(true);
        follower.setStartingPose(startPose);
    }

    public void resetHeading()
    {
        follower.setCurrentPoseWithOffset(new Pose(follower.getXOffset(), follower.getYOffset(), Math.toRadians(0)));
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

