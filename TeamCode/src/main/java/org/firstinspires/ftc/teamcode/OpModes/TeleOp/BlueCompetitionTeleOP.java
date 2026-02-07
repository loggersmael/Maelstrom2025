package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Utilities.Storage;

@TeleOp(name="BlueCompetitionTeleOP")
public class BlueCompetitionTeleOP extends OpMode
{
    private Maelstrom Robot;
    private Timer loop;

    @Override
    public void init()
    {
        loop= new Timer();
        Robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        Robot.dt.enableTeleop();
        telemetry.addData("tempOffset: ", Storage.turretOffset);
        //Robot.turret.setOffsetAngle(0);
    }

    @Override
    public void start()
    {
        Robot.turret.updateOffset();
        Robot.turret.startPoseTracking();
        loop.resetTimer();
    }

    @Override
    public void loop()
    {
        Robot.periodic();
        Robot.controlMap();
        //telemetry.addData("Loop times: ", loop.getElapsedTime());
        loop.resetTimer();
    }
}
