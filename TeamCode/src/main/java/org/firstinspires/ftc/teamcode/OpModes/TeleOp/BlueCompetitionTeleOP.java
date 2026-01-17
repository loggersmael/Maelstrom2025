package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@TeleOp(name="BlueCompetitionTeleOP")
public class BlueCompetitionTeleOP extends OpMode
{
    private Maelstrom Robot;

    @Override
    public void init()
    {
        Robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        Robot.dt.enableTeleop();
        telemetry.addData("tempOffset: ", Turret.tempOffset);
        //Robot.turret.setOffsetAngle(0);
    }

    @Override
    public void start()
    {
        //Robot.turret.updateOffset();
        Robot.turret.startPoseTracking();
    }

    @Override
    public void loop()
    {
        Robot.periodic();
        Robot.controlMap();
    }
}
