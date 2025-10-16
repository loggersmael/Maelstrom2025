package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.SpindexerConstants.kD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.SpindexerConstants.kF;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.SpindexerConstants.kI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.SpindexerConstants.kP;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.AbsoluteAnalogEncoder;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Spindexer extends SubsystemBase
{
    private CRServoEx spin;
    private PIDFController spincontrol= new PIDFController(kP,kI,kD,kF);
    private AbsoluteAnalogEncoder encoder;
    private CRServoEx transfer;
    private RevColorSensorV3 slot1;
    private RevColorSensorV3 slot2;
    private RevColorSensorV3 slot3;

    public Spindexer(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        spin= new CRServoEx(aHardwareMap,"spin");
        transfer= new CRServoEx(aHardwareMap, "transfer");
        encoder= new AbsoluteAnalogEncoder(aHardwareMap, "encoder");
    }

    public double getScaledAngle()
    {
        if(encoder.getCurrentPosition()>=180)
        {
            return (encoder.getCurrentPosition()-180)*2;
        }
        else
        {
            return encoder.getCurrentPosition() * 2;
        }
    }
    }
