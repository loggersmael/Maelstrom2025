package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants;

public class Turret extends SubsystemBase {
    private DcMotorEx turretMotor;
    private Limelight3A cam;
    private PIDFController pidfController;
    private Maelstrom.Alliance alliance;
    private Telemetry telemetry;

    public enum SystemState {
        IDLE,
        TARGET_POSITION,
        MANUAL
    }

    public enum WantedState {
        IDLE,
        TARGET_POSITION,
        MANUAL
    }

    private WantedState wantedState = WantedState.IDLE;
    private SystemState systemState = SystemState.IDLE;

    // Limelight data (read internally from camera)
    private double tx = 0;
    private boolean hasTarget = false;

    // Constants for encoder calculations
    // 1024 ticks per revolution, 1:3 gear ratio
    // COUNTS_PER_DEGREE = (1024 * 3) / 360 = 3072 / 360 = 8.533...
    private static final double COUNTS_PER_DEGREE = (1024.0 * 3.0) / 360.0;

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

        // Initialize PIDF controller with constants from TurretConstants
        pidfController = new PIDFController(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
    }

    @Override
    public void periodic() {
        // Read Limelight data internally
        updateLimelightData();

        systemState = handleTransition();
        applyStates();

        // Telemetry
        telemetry.addData("Turret State: ", systemState);
        telemetry.addData("Turret Angle: ", getCurrentAngle());
        telemetry.addData("tx: ", tx);
        telemetry.addData("Has Target: ", hasTarget);
        telemetry.addData("Encoder Position: ", getEncoderPosition());
    }

    /**
     * Updates Limelight data internally from camera
     */
    private void updateLimelightData() {
        LLResult result = cam.getLatestResult();
        if (result != null && result.isValid()) {
            // Get tx value (horizontal offset in degrees)
            tx = result.getTx();
            
            // Check if any fiducials are detected
            hasTarget = result.getFiducialResults() != null && !result.getFiducialResults().isEmpty();
        } else {
            tx = 0;
            hasTarget = false;
        }
    }

    /**
     * Handles state transitions based on wanted state
     */
    private SystemState handleTransition() {
        switch(wantedState) {
            case IDLE:
                return SystemState.IDLE;
            case TARGET_POSITION:
                return SystemState.TARGET_POSITION;
            case MANUAL:
                return SystemState.MANUAL;
            default:
                return SystemState.IDLE;
        }
    }

    /**
     * Applies the current system state
     */
    private void applyStates() {
        switch(systemState) {
            case IDLE:
                turretMotor.setPower(0);
                break;
            case TARGET_POSITION:
                if (hasTarget) {
                    aimWithVision();
                } else {
                    turretMotor.setPower(0);
                }
                break;
            case MANUAL:
                // Manual control is handled by setManualPowerControl()
                break;
        }
    }

    /**
     * Aims turret at target using vision data
     */
    private void aimWithVision() {
        int currentPosition = getEncoderPosition();
        
        // Simple limit checks - stop if at limits
        if (currentPosition >= TurretConstants.maxLimit) {
            turretMotor.setPower(0);
            return;
        }
        
        if (currentPosition <= TurretConstants.minLimit) {
            turretMotor.setPower(0);
            return;
        }
        
        double currentAngle = getAngle();
        double targetAngle = currentAngle - tx;

        // Clamp to safe range
        targetAngle = Math.max(0, Math.min(360, targetAngle));

        // Calculate PIDF output (negate for correct direction)
        double power = -pidfController.calculate(currentAngle, targetAngle);
        
        // Add minimum power threshold to ensure motor moves
        if (Math.abs(power) < 0.05 && Math.abs(tx) > 0.5) {
            power = Math.signum(tx) * 0.05; // Minimum 5% power if there's a significant error
        }
        
        // Check if power would exceed limits
        double targetPosition = targetAngle * COUNTS_PER_DEGREE;
        if (targetPosition > TurretConstants.maxLimit || targetPosition < TurretConstants.minLimit) {
            power = 0; // Stop if would exceed limits
        }
        
        // Clamp power to motor limits
        power = Math.max(-1.0, Math.min(1.0, power));

        turretMotor.setPower(power);
    }


    /**
     * Sets manual power control for the turret
     */
    public void setManualPowerControl(double power) {
        wantedState = WantedState.MANUAL;
        turretMotor.setPower(power);
    }


    /**
     * Starts tracking targets
     */
    public void startTracking() {
        wantedState = WantedState.TARGET_POSITION;
    }

    /**
     * Stops tracking and goes to idle
     */
    public void stopTracking() {
        wantedState = WantedState.IDLE;
    }


    /**
     * Gets the current turret angle in degrees
     * Uses encoder counts and accounts for gear ratio
     */
    private double getAngle() {
        int encoderCounts = turretMotor.getCurrentPosition();
        // Convert encoder counts to degrees
        // encoderCounts / COUNTS_PER_DEGREE gives us degrees
        return encoderCounts / COUNTS_PER_DEGREE;
    }

    /**
     * Gets the current encoder position
     */
    public int getEncoderPosition() {
        return turretMotor.getCurrentPosition();
    }

    /**
     * Gets the current angle in degrees
     */
    public double getCurrentAngle() {
        return getAngle();
    }

    /**
     * Gets the current system state
     */
    public SystemState getSystemState() {
        return systemState;
    }

    /**
     * Gets the current tx value (horizontal offset from Limelight)
     */
    public double getTx() {
        return tx;
    }

    /**
     * Gets whether a target is currently detected
     */
    public boolean hasTarget() {
        return hasTarget;
    }
}
