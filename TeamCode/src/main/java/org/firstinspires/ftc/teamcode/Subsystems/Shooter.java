package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kP;

import com.qualcomm.hardware.limelightvision.Limelight3A;
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
    private MotorEx shooterMotor;
    private ServoEx hoodServo;
    private Limelight3A cam;
    private double currentVelocity;
    private double targetVelocity;

    public Shooter(HardwareMap aHardwaremap, Telemetry telemetry)
    {
        shooterMotor= new MotorEx(aHardwaremap, ShooterConstants.shooterMotorID, Motor.GoBILDA.RPM_435);
        shooterMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        hoodServo= new ServoEx(aHardwaremap, ShooterConstants.hoodServoID);
        cam= aHardwaremap.get(Limelight3A.class,"limelight");
        shooterMotor.setVeloCoefficients(kP,kI,kD);
    }

    public void shootClose()
    {
        shooterMotor.setVelocity(ShooterConstants.closeVelocity);
    }

    public void shootFar()
    {
        shooterMotor.setVelocity(ShooterConstants.farVelocity);
    }

    public void stopFlywheel()
    {
        shooterMotor.motorEx.setPower(0);
    }



}
