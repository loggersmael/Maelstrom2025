package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kP;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants;

import java.util.List;

public class Turret extends SubsystemBase
{
    private Maelstrom.Alliance alliance;
    private Motor turretMotor;
    private Motor.Encoder turretEncoder;
    private PIDFController turretcontrol = new PIDFController(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
    public Limelight3A cam;
    private LLResult result;
    private List<LLResultTypes.DetectorResult> tagList;
    public Turret(HardwareMap aHardwareMap, Telemetry telemetry, Maelstrom.Alliance color)
    {
        alliance=color;
        turretMotor= new Motor(aHardwareMap,"turret");
        cam= aHardwareMap.get(Limelight3A.class, "limelight");
        cam.pipelineSwitch(1);
        result= cam.getLatestResult();
        tagList=result.getDetectorResults();
        turretMotor.setInverted(false);
        turretMotor.setRunMode(Motor.RunMode.RawPower);
    }

    @Override
    public void periodic()
    {
        result= cam.getLatestResult();
        tagList=result.getDetectorResults();
    }

    public void trackTag()
    {
        if(alliance.equals(Maelstrom.Alliance.BLUE))
        {
            if (result!=null)
            {

            }
        }
        else if(alliance.equals(Maelstrom.Alliance.RED))
        {

        }
    }


}
