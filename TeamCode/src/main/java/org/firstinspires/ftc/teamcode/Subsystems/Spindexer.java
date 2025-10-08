package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Spindexer
{
    private CRServoEx spin;
    private CRServoEx transfer;
    private RevColorSensorV3 slot1;
    private RevColorSensorV3 slot2;
    private RevColorSensorV3 slot3;

    public Spindexer(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        transfer= new CRServoEx(aHardwareMap, "transfer");
    }
}
