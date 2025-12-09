package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Subsystems.Turret.TurretState.MANUALPOWER;
import static org.firstinspires.ftc.teamcode.Subsystems.Turret.TurretState.POSETRACKING;
import static org.firstinspires.ftc.teamcode.Subsystems.Turret.TurretState.VISIONTRACKING;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.PIDFSwitch;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.angleTolerance;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kF;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kP;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.max;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.maxLimit;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.maxPos;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.minLimit;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.sD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.sF;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.sI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.sP;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.startingPos;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants;

import java.util.List;

public class Turret extends SubsystemBase
{
    public enum TurretState{
        MANUALPOWER,MANUALANGLE,VISIONTRACKING,POSETRACKING,IDLE;
    }
    public TurretState state;
    private MotorEx turretMotor;
    private Motor.Encoder encoder;
    private PIDFController turretController = new PIDFController(kP,kI,kD,kF);
    private PIDFController secondaryController = new PIDFController(sP,sI,sD,sF);

    private Telemetry telemetry;

    private double currentAngle;
    private double targetAngle=0;
    private double targetPoseAngle=0;
    private double manualAngle=0;
    public double manualPower;

    public static double offsetAngle=0;
    public static double atan=0;

    public Turret(HardwareMap hMap, Telemetry telemetry)
    {
        this.telemetry=telemetry;
        turretMotor= new MotorEx(hMap,"turret");
        encoder=turretMotor.encoder;
        turretMotor.motorEx.setDirection(DcMotorSimple.Direction.FORWARD);
        turretMotor.setRunMode(Motor.RunMode.RawPower);
        turretMotor.stopAndResetEncoder();
        state=TurretState.IDLE;
        secondaryController.setTolerance(angleTolerance);
        turretController.setTolerance(angleTolerance);
    }

    @Override
    public void periodic()
    {
        telemetry.addData("Current Angle: ",getAngle());
        telemetry.addData("Target Angle: ", targetAngle);
        telemetry.addData("Turret Encoder: ", encoder.getPosition());
        telemetry.addData("Inverse Turret Encoder: ", getInversePosition());
        telemetry.addData("Manual Power: ",manualPower);
        telemetry.addData("Error: ", Math.abs(getAngle()-targetPoseAngle));
        telemetry.addData("kF: ", sF);

        turretController.setPIDF(kP,kI,kD,kF);
        secondaryController.setPIDF(sP,sI,sD,sF);

        switch(state)
        {
            case VISIONTRACKING:
                if(Math.abs(getAngle()-targetAngle)>PIDFSwitch) {
                    turretMotor.set(turretController.calculate(getAngle(), targetAngle));
                }
                else {
                    turretMotor.set(secondaryController.calculate(getAngle(), targetAngle));
                }
                break;
            case POSETRACKING:
                if(Math.abs(getAngle()-targetPoseAngle)>PIDFSwitch)
                {
                    turretMotor.set(turretController.calculate(getAngle(),targetPoseAngle));
                    telemetry.addData("Motor Power: ", turretController.calculate(getAngle(),targetPoseAngle));
                }
                else {
                    turretMotor.set(secondaryController.calculate(getAngle(),targetPoseAngle));
                }
                break;
            case MANUALANGLE:
                if(Math.abs(getAngle()-manualAngle)>PIDFSwitch) {
                    turretMotor.set(turretController.calculate(getAngle(), manualAngle));
                }
                else {
                    turretMotor.set(secondaryController.calculate(getAngle(), manualAngle));
                }
                break;
            case MANUALPOWER:
                turretMotor.set(manualPower);
                break;
            case IDLE:
                turretMotor.set(0);
                break;
        }
    }

    public void getTargetAngle(double tx,boolean hasTarget)
    {
        if(hasTarget)
        {
            targetAngle = getAngle() + tx;
            targetAngle = Math.max(0, Math.min(300, targetAngle));
        }
    }

    public void calculatePoseAngle(Pose targetPose, Pose robotPose)
    {
        double angleToTargetFromCenter = Math.atan2(targetPose.getY() - robotPose.getY(), targetPose.getX() - robotPose.getX());
        telemetry.addData("Atan: ", Math.toDegrees(angleToTargetFromCenter));
        double robotAngleDiff = normalizeAngle(angleToTargetFromCenter - robotPose.getHeading());
        telemetry.addData("RobotAngleDiff: ", Math.toDegrees(robotAngleDiff));
        robotAngleDiff= Math.max(Math.min(robotAngleDiff,Math.toRadians(150)),Math.toRadians(-150));
        targetPoseAngle= Math.toDegrees(robotAngleDiff);
    }

    public void setManualAngle(double angle)
    {
        manualAngle=angle;
    }

    public double getAngle()
    {
        return (encoder.getPosition()*(360.0/4096))/3 + offsetAngle;
    }

    public double getInversePosition()
    {
        return -encoder.getPosition();
    }
    public void setPower(double p)
    {
        manualPower=p;
    }

    public void setManualControl()
    {
        state= MANUALPOWER;
    }

    public void startVisionTracking()
    {
        state= TurretState.VISIONTRACKING;
    }
    public void startPoseTracking()
    {
        state=POSETRACKING;
    }


    public void setPointMode()
    {
        state=TurretState.MANUALANGLE;
    }

    public void stopAndReset()
    {
        turretMotor.stopAndResetEncoder();
    }

    public void setOffsetAngle(double angle)
    {
        offsetAngle=angle;
    }

    public boolean atTarget()
    {
        return secondaryController.atSetPoint();
    }

    public static double normalizeAngle(double angleRadians) {
        double angle = angleRadians % (Math.PI * 2D);
        if (angle <= -Math.PI) angle += Math.PI * 2D;
        if (angle > Math.PI) angle -= Math.PI * 2D;
        return angle;
    }
}
