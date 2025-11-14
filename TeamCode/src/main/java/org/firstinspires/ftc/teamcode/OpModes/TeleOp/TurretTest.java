package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.hardware.limelightvision.LLFieldMap;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.SimpleTurret;
import org.firstinspires.ftc.teamcode.Subsystems.TrueTurret;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;

import java.util.List;

@TeleOp(name="turretTest")
public class TurretTest extends OpMode
{

    private Maelstrom Robot;
    private TrueTurret turr;
    private Vision cam;
    private LLResultTypes.FiducialResult target;
    private List<LLResultTypes.FiducialResult> tagList;
    private Maelstrom.Alliance alliance= Maelstrom.Alliance.BLUE;
    @Override
    public void init()
    {
        Robot= new Maelstrom(hardwareMap, telemetry, Maelstrom.Alliance.BLUE, gamepad1, gamepad2);
        cam= new Vision(hardwareMap, telemetry, Maelstrom.Alliance.BLUE);
        turr= TrueTurret.getInstance(hardwareMap);
        turr.startTracking();
    }
    @Override
    public void loop()
    {
        cam.periodic();
        Robot.periodic();
        turr.periodic();
        
        // Always update limelight data, even when no target is present
        // This ensures hasTarget is updated correctly
        turr.setLimelightData(cam.getTargetX(), cam.targetPresent());
        
        if(gamepad1.right_bumper)
        {
            Robot.shooter.setFullPower();
        }
    }

}
