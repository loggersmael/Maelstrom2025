package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.redGoal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@TeleOp(name="TurretPls")
public class TurretPls extends OpMode
{
    private Drivetrain dt;
    private Turret turret;

    @Override
    public void init()
    {
        dt= new Drivetrain(hardwareMap,telemetry);
        turret= new Turret(hardwareMap,telemetry);
        turret.startPoseTracking();
        dt.enableTeleop();
    }

    @Override
    public void loop()
    {
        turret.periodic();
        dt.periodic();
        telemetry.update();
        turret.calculatePoseAngle(redGoal,dt.getPose());
    }
}
