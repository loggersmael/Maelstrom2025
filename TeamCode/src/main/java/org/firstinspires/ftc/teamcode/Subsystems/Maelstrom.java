package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Maelstrom
{
    public enum Alliance{
        RED,BLUE;
    }
    private Drivetrain dt;
    private Intake intake;
    private Shooter shooter;
    private Turret turret;

    public Maelstrom(HardwareMap hMap, Telemetry telemetry, Alliance color)
    {
        dt= new Drivetrain(hMap,telemetry);

    }
}
