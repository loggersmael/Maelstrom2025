package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;

@TeleOp(name="Manual Turret")
public class ManualTurret extends OpMode
{
    private Turret turret;

    @Override
    public void init()
    {
        turret= new Turret(hardwareMap,telemetry);
        turret.manualControl=true;
    }

    public void loop()
    {
        turret.periodic();
        turret.setPower(gamepad1.left_stick_x);
        telemetry.update();
    }
}
