package org.firstinspires.ftc.teamcode.OpModes.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleTurretV2;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;

@TeleOp(name="TurretTroubleshooting2")
public class TurretTroubleshooting2 extends OpMode
{
    private SimpleTurretV2 turret;
    private Vision cam;

    @Override
    public void init()
    {
        turret= new SimpleTurretV2(hardwareMap,telemetry);
        cam= new Vision(hardwareMap,telemetry, Maelstrom.Alliance.BLUE);
    }

    @Override
    public void loop()
    {
        cam.periodic();
        turret.periodic();
        turret.trackTag(cam.getTargetX(),cam.targetPresent());
        telemetry.update();
    }
}
