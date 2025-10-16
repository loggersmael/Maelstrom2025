package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants;

public class Shooter extends SubsystemBase
{
    public MotorEx shooterMotor;
    public ServoEx hoodServo;

    public Shooter(HardwareMap aHardwaremap, Telemetry telemetry)
    {
        shooterMotor= new MotorEx(aHardwaremap, ShooterConstants.shooterMotorID);
        hoodServo= new ServoEx(aHardwaremap, ShooterConstants.hoodServoID);
    }
}
