package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kF;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants.kP;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SimpleTurretV2 extends SubsystemBase
{
    private MotorEx turretMotor;
    private Motor.Encoder encoder;
    private PIDFController turretController = new PIDFController(kP,kI,kD,kF);

    private Telemetry telemetry;
    private double power=0;

    public SimpleTurretV2(HardwareMap hMap, Telemetry telemetry)
    {
        turretMotor= new MotorEx(hMap,"turret");
        encoder=turretMotor.encoder;
        turretMotor.motorEx.setDirection(DcMotorSimple.Direction.FORWARD);
        turretMotor.setRunMode(Motor.RunMode.RawPower);
        this.telemetry=telemetry;
    }

    @Override
    public void periodic()
    {
        turretController.setPIDF(kP,kI,kD,kF);
        telemetry.addData("Turret Encoder: ", encoder.getPosition());
        telemetry.addData("Inverse Turret Encoder: ", getInversePosition());
        turretMotor.set(power);
    }

    public void trackTag(double tx, boolean hasTarget)
    {
        if(hasTarget)
        {
            power=turretController.calculate(tx,0);
        }
    }

    public double getInversePosition()
    {
        return -encoder.getPosition();
    }
}
