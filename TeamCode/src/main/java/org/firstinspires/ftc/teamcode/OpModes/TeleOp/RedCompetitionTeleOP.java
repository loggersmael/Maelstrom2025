package org.firstinspires.ftc.teamcode.OpModes.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@TeleOp(name="RedCompetitionTeleOP")
public class RedCompetitionTeleOP extends OpMode
{

    private Maelstrom Robot;

    @Override
    public void init()
    {
        Robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.RED,gamepad1,gamepad2);
        Robot.dt.enableTeleop();
    }
    @Override
    public void loop()
    {
        Robot.controlMap();
    }
}
