package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kP;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.midVelocity;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.velocityTolerance;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    public DcMotorEx shooterMotor;
    private Servo hoodServo;
    private Servo light;
    private Limelight3A cam;
    private Maelstrom.Alliance alliance;
    private Telemetry telemetry;
    public double currentVelocity;
    public double targetVelocity;
    public boolean flywheelOn;
    public PIDFController velocityController= new PIDFController(kP,kI,kD,ShooterConstants.kF);

    public Shooter(HardwareMap aHardwareMap, Telemetry telemetry, Maelstrom.Alliance color) {
        this.telemetry = telemetry;
        this.alliance = color;

        shooterMotor = aHardwareMap.get(DcMotorEx.class, "shooter");
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setVelocityPIDFCoefficients(kP, kI, kD, 0);

        hoodServo = aHardwareMap.get(Servo.class, "hood");
        hoodServo.setDirection(Servo.Direction.REVERSE);
        hoodServo.setPosition(0);

        light = aHardwareMap.get(Servo.class, "light");
        cam = aHardwareMap.get(Limelight3A.class, "limelight");

        flywheelOn = false;
        targetVelocity = ShooterConstants.closeVelocity; // start with some default
    }

    @Override
    public void periodic() {
        // Update shooter velocity
        if (flywheelOn) {
            shooterMotor.setPower(velocityController.calculate(currentVelocity,targetVelocity));
        } else {
            shooterMotor.setPower(0);
        }

        currentVelocity = shooterMotor.getVelocity();

        // Keep all servo positions intact
        setLight();

        telemetry.addData("Flywheel On", flywheelOn);
        telemetry.addData("Current Velocity", currentVelocity);
        telemetry.addData("Target Velocity", targetVelocity);
    }

    public void shootClose() {
        targetVelocity = ShooterConstants.closeVelocity;
    }

    public void shootMid() {
        targetVelocity = midVelocity;
    }

    public void shootFar() {
        targetVelocity = ShooterConstants.farVelocity;
    }

    public void shootFarAuto()
    {
        targetVelocity= 2000;
    }

    public void reverseWheel() {
        shooterMotor.setPower(-0.1);
        flywheelOn = false;
    }

    public void toggleFlywheel() {
        flywheelOn = !flywheelOn;
    }

    public void stopFlywheel() {
        shooterMotor.setPower(0);
        flywheelOn = false;
    }

    public void setHoodServo(double angle) {
        hoodServo.setPosition(angle);
    }

    public void setFullPower() {
        shooterMotor.setPower(1);
    }

    public void setLight() {
        if (atSpeed()) {
            light.setPosition(0.5);
        } else {
            light.setPosition(0.277);
        }
    }

    public boolean atSpeed() {
        return Math.abs(currentVelocity - targetVelocity) < velocityTolerance;
    }

    public void hoodUp()
    {
        hoodServo.setPosition(0.5);
    }

}
