package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.IntakeConstants;

public class Intake extends SubsystemBase
{
    private Motor intakeMotor;
    private Motor.Encoder intakeEncoder;
    public Intake(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        intakeMotor= new Motor(aHardwareMap, IntakeConstants.intakeID, Motor.GoBILDA.RPM_435);
        intakeMotor.setRunMode(Motor.RunMode.PositionControl);
        intakeMotor.setInverted(false);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void spinIn()
    {
        intakeMotor.motor.setPower(1);
    }
    public void spinOut()
    {
        intakeMotor.motor.setPower(-1);
    }
    public void stop()
    {
        intakeMotor.motor.setPower(0);
    }


}
