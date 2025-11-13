package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.startingPos;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.SimpleTurretConstants;
import org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants;

import java.util.List;

public class SimpleTurret extends SubsystemBase
{
    private Maelstrom.Alliance alliance;
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
    public SimpleTurret(HardwareMap aHardwareMap, Telemetry telemetry, Maelstrom.Alliance color)
    {
        alliance=color;
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
        tagList=cam.getLatestResult().getFiducialResults();
        checkLimitAndGo();
        turretMotor.motorEx.setPower(turretPower * turretPowerCoef);
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

    public double powerToTarget()
    {
        double power= 0;
        if(getTargetX()!=-320923)
        {
            power= turretcontrol.calculate(getTargetX(),crosshairX);
        }
        else {
            power=0;
        }
        return power;
    }

    public void powerToTick(double tar)
    {
        turretPower= turretcontrol.calculate(turretEncoder.getPosition(),tar);
    }

    public void checkLimitAndGo()
    {
        if(turretEncoder.getPosition()> SimpleTurretConstants.maxLimit && powerToTarget()>0)
        {
            turretPower=0;
        }
        else if(turretEncoder.getPosition()<SimpleTurretConstants.minLimit && powerToTarget()<0)
        {
            turretPower=0;
        }
        else
        {
            turretPower=powerToTarget();
        }
    }
}
