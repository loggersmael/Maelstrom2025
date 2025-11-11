package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Transfer extends SubsystemBase
{
    private Servo kicker;

    public Transfer(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        kicker=aHardwareMap.get(Servo.class,"kicker");
    }

    public void down()
    {
        kicker.setPosition(0);
    }

    public void up()
    {
        kicker.setPosition(0.2);
    }

    public void halfWay()
    {
        kicker.setPosition(0.1);
    }
}
