package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import java.util.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Vision extends SubsystemBase
{
    public Limelight3A cam;
    private List<LLResultTypes.FiducialResult> tagList;
    private Maelstrom.Alliance alliance;
    private Telemetry telemetry;
    private double targetx;

    public Vision(HardwareMap hMap, Telemetry telemetry, Maelstrom.Alliance color)
    {
        alliance=color;
        this.telemetry=telemetry;
        cam= hMap.get(Limelight3A.class,"limelight");
        
        // CRITICAL: Start the Limelight before trying to get results
        cam.start();
        
        // Initialize tagList as empty - it will be populated in periodic()
        tagList = new ArrayList<>();
    }


    @Override
    public void periodic()
    {
        // Always update tagList from the latest result, but check if result is valid first
        LLResult result = cam.getLatestResult();
        if (result != null && result.isValid()) {
            tagList = result.getFiducialResults();
            
            // Update targetx if we have a valid tag
            LLResultTypes.FiducialResult tag = getTag();
            if (tag != null) {
                targetx = tag.getTargetXDegrees();
            }
        } else {
            // If result is invalid, clear the tag list
            tagList = new ArrayList<>();
        }
        
        telemetry.addData("Target X: ", targetx);
        telemetry.addData("Target Present: ", targetPresent());
        telemetry.addData("Fiducial Count: ", tagList != null ? tagList.size() : 0);
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
        if(getTag()!=null) {
            return getTag().getTargetXDegrees();
        }
        return 0;
    }

    public boolean targetPresent()
    {
        return getTag()!=null;
    }
}
