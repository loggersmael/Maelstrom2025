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
        
        // CRITICAL: Set pipeline before starting (pipeline 0 is typically AprilTag detection)
        cam.pipelineSwitch(0);
        
        // Start polling for data - must be called after pipelineSwitch
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
        // Use result.getTx() for primary target (works with any tag, not just alliance-specific)
        if (result != null && result.isValid() && result.getFiducialResults() != null && !result.getFiducialResults().isEmpty()) {
            trackTarget();
        } else {
            turretMotor.setPower(0);
        }

        // Telemetry
        telemetry.addData("Turret Angle: ", getCurrentAngle());
        telemetry.addData("Target X Degrees (tx): ", getTargetXDegrees());
        telemetry.addData("Has Target: ", hasTarget());
        telemetry.addData("Result Valid: ", result != null && result.isValid());
        telemetry.addData("Fiducial Count: ", tagList != null ? tagList.size() : 0);
        
        // Show primary target tx value directly from result
        if (result != null && result.isValid()) {
            telemetry.addData("Result tx: ", result.getTx());
        }
        
        // Debug: Show all detected tag IDs
        if (tagList != null && !tagList.isEmpty()) {
            StringBuilder tagIds = new StringBuilder();
            for (LLResultTypes.FiducialResult tag : tagList) {
                if (tag != null) {
                    if (tagIds.length() > 0) tagIds.append(", ");
                    tagIds.append(tag.getFiducialId());
                }
            }
            telemetry.addData("Detected Tag IDs: ", tagIds.toString());
        } else {
            telemetry.addData("Detected Tag IDs: ", "none");
        }
        
        if (result != null) {
            telemetry.addData("Pipeline Index: ", cam.getStatus().getPipelineIndex());
        }
    }

    /**
     * Get the target April tag based on alliance
     * @return FiducialResult for the target tag, or null if not found
     */
    public LLResultTypes.FiducialResult getTag() {
        LLResultTypes.FiducialResult target = null;
        
        if (tagList == null || alliance == null) {
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
     * Get the current encoder position
     * @return Current encoder position in ticks
     */
    private int getCurrentPosition() {
        return turretMotor.getCurrentPosition();
    }

    /**
     * Check if turret is at or beyond the maximum limit
     * @return true if at/beyond max limit
     */
    private boolean isAtMaxLimit() {
        return getCurrentPosition() >= TurretConstants.maxLimit;
    }

    /**
     * Check if turret is at or beyond the minimum limit
     * @return true if at/beyond min limit
     */
    private boolean isAtMinLimit() {
        return getCurrentPosition() <= TurretConstants.minLimit;
    }

    /**
     * Normalize angle to range [0, 360)
     * @param angle Angle in degrees
     * @return Normalized angle
     */
    private double normalizeAngle(double angle) {
        while (angle < 0) angle += 360;
        while (angle >= 360) angle -= 360;
        return angle;
    }

    /**
     * Calculate the shortest angular distance between two angles
     * @param from Starting angle in degrees
     * @param to Target angle in degrees
     * @return Shortest angular distance in degrees (positive = clockwise, negative = counterclockwise)
     */
    private double shortestAngularDistance(double from, double to) {
        double diff = to - from;
        if (diff > 180) {
            diff -= 360;
        } else if (diff < -180) {
            diff += 360;
        }
        return diff;
    }

    /**
     * Track the target using PIDF control with unwinding logic
     * Prevents full rotations by flipping to opposite direction when needed
     */
    private void trackTarget() {
        double targetXDegrees = getTargetXDegrees();
        double currentAngle = getCurrentAngle();
        int currentPosition = getCurrentPosition();
        
        // Check if we're at limits and need to unwind
        if (isAtMaxLimit()) {
            // At max limit, unwind to starting position
            double unwindTarget = (TurretConstants.startingPos * 360.0 / 1024.0) / 3.0;
            double power = pidfController.calculate(currentAngle, unwindTarget);
            power = Math.max(-1.0, Math.min(1.0, power));
            turretMotor.setPower(power);
            return;
        }
        
        if (isAtMinLimit()) {
            // At min limit, unwind to max position
            double unwindTarget = (TurretConstants.maxPos * 360.0 / 1024.0) / 3.0;
            double power = pidfController.calculate(currentAngle, unwindTarget);
            power = Math.max(-1.0, Math.min(1.0, power));
            turretMotor.setPower(power);
            return;
        }
        
        // Calculate target angle: current angle minus the offset to center the target
        // If target is at +5 degrees, we need to rotate -5 degrees to center it
        double targetAngle = currentAngle - targetXDegrees;
        targetAngle = normalizeAngle(targetAngle);
        
        // Calculate the shortest angular distance (returns value in [-180, 180])
        double angularDistance = shortestAngularDistance(currentAngle, targetAngle);
        
        // Calculate what the new encoder position would be after rotation
        double newAngle = normalizeAngle(currentAngle + angularDistance);
        double newPosition = (newAngle * 3.0 * 1024.0) / 360.0;
        
        // Check if tracking would exceed limits
        boolean wouldExceedMax = newPosition > TurretConstants.maxLimit;
        boolean wouldExceedMin = newPosition < TurretConstants.minLimit;
        
        // Check if rotation would require more than 90 degrees (threshold for unwinding)
        // If so, unwind to opposite limit instead of completing a large rotation
        boolean requiresLargeRotation = Math.abs(angularDistance) > 90;
        
        if (wouldExceedMax) {
            // Would exceed max limit, unwind to starting position (opposite limit)
            targetAngle = (TurretConstants.startingPos * 360.0 / 1024.0) / 3.0;
        } else if (wouldExceedMin) {
            // Would exceed min limit, unwind to max position (opposite limit)
            targetAngle = (TurretConstants.maxPos * 360.0 / 1024.0) / 3.0;
        } else if (requiresLargeRotation) {
            // Requires large rotation (>90 degrees), unwind to opposite limit to prevent full rotation
            if (angularDistance > 0) {
                // Large clockwise rotation needed, unwind to starting position
                targetAngle = (TurretConstants.startingPos * 360.0 / 1024.0) / 3.0;
            } else {
                // Large counterclockwise rotation needed, unwind to max position
                targetAngle = (TurretConstants.maxPos * 360.0 / 1024.0) / 3.0;
            }
        }

        // Calculate PIDF output
        double power = pidfController.calculate(currentAngle, targetAngle);

        // Clamp power to motor limits
        power = Math.max(-1.0, Math.min(1.0, power));

        // Apply power to motor
        turretMotor.setPower(power);
    }
}
