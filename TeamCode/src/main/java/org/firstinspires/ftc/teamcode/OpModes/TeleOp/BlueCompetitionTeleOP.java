package org.firstinspires.ftc.teamcode.OpModes.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@TeleOp(name="BlueCompetitionTeleOP")
public class BlueCompetitionTeleOP extends OpMode
{
    private Maelstrom Robot;

    @Override
    public void init()
    {
        Robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        Robot.dt.enableTeleop();
        Robot.turret.setOffsetAngle(35);
    }

    @Override
    public void start()
    {
        Robot.turret.startVisionTracking();
    }

    @Override
    public void loop()
    {
        Robot.periodic();
        Robot.controlMap();
    }
}
