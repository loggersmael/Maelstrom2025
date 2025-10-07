package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.IntakeConstants;

public class Intake
{
    private Motor intakeMotor;

    public Intake(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        intakeMotor= new Motor(aHardwareMap, IntakeConstants.intakeID, Motor.GoBILDA.RPM_312);
        intakeMotor.setRunMode(Motor.RunMode.VelocityControl);
    }

}
