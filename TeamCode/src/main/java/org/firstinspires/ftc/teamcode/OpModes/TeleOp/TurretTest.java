package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleTurret;

@TeleOp(name="turretTest")
public class TurretTest extends OpMode
{

    private Maelstrom Robot;

    @Override
    public void init()
    {
        Robot= new Maelstrom(hardwareMap, telemetry, Maelstrom.Alliance.BLUE, gamepad1, gamepad2);
    }
    @Override
    public void loop()
    {
        Robot.periodic();
        if(gamepad1.right_bumper)
        {
            Robot.shooter.setFullPower();
        }
    }

}
