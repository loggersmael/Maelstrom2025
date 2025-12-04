package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Subsystems.Turret.TurretState.MANUALPOWER;
import static org.firstinspires.ftc.teamcode.Subsystems.Turret.TurretState.TRACKING;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.angleTolerance;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kF;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kP;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.max;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.maxLimit;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.maxPos;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.minLimit;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.startingPos;

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
        MANUALPOWER,MANUALANGLE,TRACKING,IDLE;
    }
    public TurretState state;
    private MotorEx turretMotor;
    private Motor.Encoder encoder;
    private PIDFController turretController = new PIDFController(kP,kI,kD,kF);

    private Telemetry telemetry;

    private double currentAngle;
    private double targetAngle=0;
    private double manualAngle=0;
    public double manualPower;

    public static double offsetAngle=0;

    public Turret(HardwareMap hMap, Telemetry telemetry)
    {
        this.telemetry=telemetry;
        turretMotor= new MotorEx(hMap,"turret");
        encoder=turretMotor.encoder;
        turretMotor.motorEx.setDirection(DcMotorSimple.Direction.REVERSE);
        turretMotor.setRunMode(Motor.RunMode.RawPower);
        turretMotor.stopAndResetEncoder();
        state=TurretState.IDLE;
    }

    @Override
    public void periodic()
    {
        telemetry.addData("Current Angle: ",getAngle());
        telemetry.addData("Target Angle: ", targetAngle);
        telemetry.addData("Turret Encoder: ", encoder.getPosition());
        telemetry.addData("Inverse Turret Encoder: ", getInversePosition());
        telemetry.addData("Manual Power: ",manualPower);

        switch(state)
        {
            case TRACKING:
                if(Math.abs(getAngle()-targetAngle)>angleTolerance)
                {
                    turretController.setPIDF(kP,kI,kD,kF*Math.signum(targetAngle-getAngle()));
                    turretMotor.set(turretController.calculate(getAngle(), targetAngle));
                }
                else
                {
                    turretMotor.set(0);
                }
                break;
            case MANUALANGLE:
                if(Math.abs(getAngle()-manualAngle)>angleTolerance)
                {
                    turretController.setPIDF(kP,kI,kD,kF*Math.signum(manualAngle-getAngle()));
                    turretMotor.set(turretController.calculate(getAngle(), manualAngle));
                }
                else
                {
                    turretMotor.set(0);
                }
                break;
            case MANUALPOWER:
                turretMotor.set(manualPower);
                break;
            case IDLE:
                turretMotor.set(0);
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

    public void setManualAngle(double angle)
    {
        manualAngle=angle;
    }

    public void aimWithVision()
    {

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

    public void startTracking()
    {
        state= TRACKING;
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
    
}
