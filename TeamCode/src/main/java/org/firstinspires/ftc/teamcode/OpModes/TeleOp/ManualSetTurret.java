package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@TeleOp(name="ManualSetTurret")
public class ManualSetTurret extends OpMode
{
    private Turret turret;

    @Override
    public void init()
    {
        turret= new Turret(hardwareMap,telemetry);
        turret.setPointMode();
    }

    public void loop()
    {
        turret.periodic();
        turret.setManualAngle(30);
        telemetry.update();
    }
}
