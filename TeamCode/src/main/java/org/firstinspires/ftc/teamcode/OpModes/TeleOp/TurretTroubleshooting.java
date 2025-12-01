package org.firstinspires.ftc.teamcode.OpModes.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
@TeleOp(name="TurretTroubleshooting")
public class TurretTroubleshooting extends OpMode
{

    private Turret turret;
    private Vision cam;

    @Override
    public void init()
    {
        turret= new Turret(hardwareMap,telemetry);
        cam= new Vision(hardwareMap,telemetry, Maelstrom.Alliance.BLUE);
        turret.startTracking();
    }

    public void loop()
    {
        cam.periodic();
        turret.periodic();
        turret.getTargetAngle(cam.getTargetX(),cam.targetPresent());
        telemetry.update();
    }
}
