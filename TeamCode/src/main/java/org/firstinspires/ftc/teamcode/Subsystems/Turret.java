package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kP;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants;

public class Turret
{
    private Motor turretMotor;
    private Motor.Encoder turretEncoder;
    private PIDFController turretcontrol = new PIDFController(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
    public Limelight3A cam;
    public Turret(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        turretMotor= new Motor(aHardwareMap,"turret");
        cam= new Limelight3A();
        turretMotor.setInverted(false);
        turretMotor.setRunMode(Motor.RunMode.RawPower);
    }
}
