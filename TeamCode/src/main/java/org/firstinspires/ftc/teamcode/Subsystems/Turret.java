package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants;

import java.util.ArrayList;
import java.util.List;

public class Turret extends SubsystemBase {
    private DcMotorEx turretMotor;
    private Limelight3A cam;
    private PIDFController pidfController;
    private Maelstrom.Alliance alliance;
    private Telemetry telemetry;
    private List<LLResultTypes.FiducialResult> tagList;

    public Turret(HardwareMap hardwareMap, Telemetry telemetry, Maelstrom.Alliance alliance) {
        this.telemetry = telemetry;
        this.alliance = alliance;

        // Initialize motor
        turretMotor = hardwareMap.get(DcMotorEx.class, "turret");
        turretMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        turretMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize Limelight camera
        cam = hardwareMap.get(Limelight3A.class, "limelight");
        cam.start();
        cam.setPollRateHz(25);

        // Initialize tagList as empty - will be populated in periodic()
        tagList = new ArrayList<>();

        // Initialize PIDF controller with constants from TurretConstants
        pidfController = new PIDFController(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
    }

    @Override
    public void periodic() {
        // Update camera results with proper validation
        LLResult result = cam.getLatestResult();
        if (result != null && result.isValid()) {
            tagList = result.getFiducialResults();
        } else {
            tagList = new ArrayList<>();
        }

        // Track target if available, otherwise set power to 0
        if (hasTarget()) {
            trackTarget();
        } else {
            turretMotor.setPower(0);
        }

        // Telemetry
        telemetry.addData("Turret Angle: ", getCurrentAngle());
        telemetry.addData("Target X Degrees: ", getTargetXDegrees());
        telemetry.addData("Has Target: ", hasTarget());
    }

    /**
     * Get the target April tag based on alliance
     * @return FiducialResult for the target tag, or null if not found
     */
    public LLResultTypes.FiducialResult getTag() {
        LLResultTypes.FiducialResult target = null;
        
        if (tagList == null) {
            return null;
        }

        int targetId = (alliance.equals(Maelstrom.Alliance.BLUE)) ? 20 : 24;

        for (LLResultTypes.FiducialResult tag : tagList) {
            if (tag != null && tag.getFiducialId() == targetId) {
                target = tag;
                break;
            }
        }

        return target;
    }

    /**
     * Get the current turret angle in degrees
     * Formula: (encoderPosition * 360.0 / 1024) / 3
     * @return Current angle in degrees
     */
    public double getCurrentAngle() {
        return (turretMotor.getCurrentPosition() * 360.0 / 1024.0) / 3.0;
    }

    /**
     * Get the target X offset in degrees from Limelight
     * @return Target X degrees (0 = centered), or 0 if no target
     */
    public double getTargetXDegrees() {
        LLResultTypes.FiducialResult tag = getTag();
        if (tag == null) {
            return 0;
        }
        return tag.getTargetXDegrees();
    }

    /**
     * Check if a valid target is detected
     * @return true if target exists, false otherwise
     */
    public boolean hasTarget() {
        return getTag() != null;
    }

    /**
     * Track the target using PIDF control
     * Calculates motor power to center the target (track to 0 degrees offset)
     */
    private void trackTarget() {
        double targetXDegrees = getTargetXDegrees();
        double currentAngle = getCurrentAngle();
        
        // Calculate target angle: current angle minus the offset to center the target
        // If target is at +5 degrees, we need to rotate -5 degrees to center it
        double targetAngle = currentAngle - targetXDegrees;

        // Calculate PIDF output
        double power = pidfController.calculate(currentAngle, targetAngle);

        // Clamp power to motor limits
        power = Math.max(-1.0, Math.min(1.0, power));

        // Apply power to motor
        turretMotor.setPower(power);
    }
}
