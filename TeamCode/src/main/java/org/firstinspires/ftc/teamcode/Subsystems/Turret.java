package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kP;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.max;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.maxLimit;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.maxPos;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.minLimit;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.startingPos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
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
    public enum SystemState
    {
        IDLE,RESETTING,AT_TARGET,FINDING_TARGET;
    }
    private SystemState turretState=SystemState.IDLE;
    private Maelstrom.Alliance alliance;
    private Telemetry telemetry;
    private MotorEx turretMotor;
    private MotorEx.Encoder turretEncoder;
    private PIDFController turretcontrol = new PIDFController(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
    public Limelight3A cam;
    private List<LLResultTypes.FiducialResult> tagList;
    private double crosshairX=0;
    private boolean useTracking=true;
    private double turretPowerCoef=0.3;
    private double turretPower=0;
    private int unwindTarget= startingPos;
    private boolean manualControl=false;
    public Turret(HardwareMap aHardwareMap, Telemetry telemetry, Maelstrom.Alliance color)
    {
        alliance=color;
        this.telemetry=telemetry;
        turretMotor= new MotorEx(aHardwareMap,"turret");
        cam= aHardwareMap.get(Limelight3A.class, "limelight");
        cam.pipelineSwitch(1);
        tagList=cam.getLatestResult().getFiducialResults();
        turretMotor.setInverted(false);
        turretMotor.setRunMode(Motor.RunMode.RawPower);
    }

    @Override
    public void periodic()
    {
        if(!manualControl) {
            tagList = cam.getLatestResult().getFiducialResults();

            switch (turretState) {
                case RESETTING:
                    continueUnwind();
                    break;
                case FINDING_TARGET:
                    checkForunWind();
                    powerToTarget();
                    break;
                default:
                    checkForunWind();
                    break;
            }
            turretMotor.motorEx.setPower(turretPower * turretPowerCoef);
        }
        telemetry.addData("Current Encoder Pos: ",turretEncoder.getPosition());
    }

    public void reset()
    {
        turretMotor.stopAndResetEncoder();
    }
    public LLResultTypes.FiducialResult getTag()
    {
        LLResultTypes.FiducialResult target= null;
        if(alliance.equals(Maelstrom.Alliance.BLUE))
        {
            if (tagList!=null)
            {
                for(LLResultTypes.FiducialResult tar:tagList)
                {
                    if(tar!=null && tar.getFiducialId() == 20)
                    {
                        target=tar;
                        break;
                    }
                }
            }
        }
        else if(alliance.equals(Maelstrom.Alliance.RED))
        {
            if(tagList!=null)
            {
                for(LLResultTypes.FiducialResult tar:tagList)
                {
                    if(tar!=null && tar.getFiducialId() == 24)
                    {
                        target=tar;
                        break;
                    }
                }
            }
        }
        return target;
    }

    public double getTargetX()
    {
        LLResultTypes.FiducialResult targ=getTag();
        if (targ==null)
        {
            return -320923;
        }
        return targ.getTargetXPixels();
    }

    public void powerToTarget()
    {
        double power= 0;
        if(getTargetX()!=-320923)
        {
            power= turretcontrol.calculate(getTargetX(),crosshairX);
        }
        turretPower=power;
    }

    public void powerToTick(double tar)
    {
        turretPower= turretcontrol.calculate(turretEncoder.getPosition(),tar);
    }
    public void checkForunWind()
    {
        if (turretEncoder.getPosition()>maxLimit)
        {
            turretMotor.stopMotor();
            turretState=SystemState.RESETTING;
            unwindTarget=startingPos;
        }
        else if(turretEncoder.getPosition()<minLimit)
        {
            turretMotor.stopMotor();
            turretState=SystemState.RESETTING;
            unwindTarget=maxPos;
        }
    }

    public void continueUnwind()
    {
        double pos = turretEncoder.getPosition();
        double error = Math.abs(pos - unwindTarget);
        if(error<=10)
        {
            turretMotor.stopMotor();
            turretState= SystemState.FINDING_TARGET;
        }
        else {
            powerToTick(unwindTarget);
        }
    }

    public void enableManual()
    {
        manualControl=true;
    }
    public void disableManual()
    {
        manualControl=false;
    }
}
