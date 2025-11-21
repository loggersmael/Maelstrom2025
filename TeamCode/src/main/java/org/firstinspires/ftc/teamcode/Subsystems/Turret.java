package org.firstinspires.ftc.teamcode.Subsystems;

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
    private MotorEx turretMotor;
    private Motor.Encoder encoder;
    private PIDFController turretController = new PIDFController(kP,kI,kD,kF);

    private Telemetry telemetry;

    private double currentAngle;
    private double targetAngle=0;
    private boolean setAngle;
    private double manualAngle=0;
    public boolean manualControl;
    public double manualPower;

    public Turret(HardwareMap hMap, Telemetry telemetry)
    {
        turretMotor= new MotorEx(hMap,"turret");
        encoder=turretMotor.encoder;
        turretMotor.motorEx.setDirection(DcMotorSimple.Direction.FORWARD);
        turretMotor.setRunMode(Motor.RunMode.RawPower);
        manualControl=false;
        setAngle=false;
    }

    @Override
    public void periodic()
    {
        turretController.setPIDF(kP,kI,kD,kF);
        telemetry.addData("Current Angle: ",getAngle());
        telemetry.addData("Target Angle: ", targetAngle);
        telemetry.addData("Turret Encoder: ", encoder.getPosition());
        telemetry.addData("Inverse Turret Encoder: ", getInversePosition());
        telemetry.addData("Manual Power: ",manualPower);
        if(!manualControl)
        {
            if(!setAngle)
            {
            turretMotor.set(turretController.calculate(getAngle(), targetAngle));
            }
            else
            {
            turretMotor.set(turretController.calculate(getAngle(), manualAngle));
            }
        }
        else
        {
            turretMotor.set(manualPower);
        }
    }

    public void getTargetAngle(double tx,boolean hasTarget)
    {
        if(hasTarget)
        {
            targetAngle = getAngle() + tx;
            targetAngle = Math.max(0, Math.min(320, targetAngle));
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
        return (encoder.getPosition()*(360.0/1024))/3;
    }

    public double getInversePosition()
    {
        return -encoder.getPosition();
    }
    public void setPower(double p)
    {
        manualPower=p;
    }

    public void setManualControl(boolean b)
    {
        manualControl=b;
    }

    public void setPointMode(boolean b)
    {
        setAngle=b;
    }
    
}
