package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.TurretConstants;

import java.util.List;


public class TrueTurret extends SubsystemBase {
    private DcMotorEx turretMotor;
    private DigitalChannel leftHomingSwitch;
    private Limelight3A cam;
    private Maelstrom.Alliance color;
    private Telemetry telemetry;

    public enum SystemState {
        IDLE,
        HOME,
        FINDING_POSITION,
        RELOCALIZING,
        TARGET_POSITION,
        MANUAL
    }

    public enum WantedState {
        IDLE,
        HOME,
        FINDING_POSITION,
        RELOCALIZING,
        TARGET_POSITION,
        MANUAL
    }

    public PIDFController positionController;
    private SimpleMotorFeedforward frictionController;

    private WantedState wantedState = WantedState.IDLE;
    private SystemState systemState = SystemState.IDLE;

    public static TrueTurret instance;
    public static synchronized TrueTurret getInstance(HardwareMap hMap) {
        if(instance == null) {
            instance = new TrueTurret(hMap);
        }

        return instance;
    }

    private List<LLResultTypes.FiducialResult> tagList;
    private double tx;
    private boolean hasTarget;

    private TrueTurret(HardwareMap hMap) {
        turretMotor = hMap.get(DcMotorEx.class, "turret");
        turretMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        cam = hMap.get(Limelight3A.class, "limelight");
        
        cam.start();
        
        tagList = cam.getLatestResult().getFiducialResults();
        positionController = new PIDFController(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
        frictionController = new SimpleMotorFeedforward(0, 0, 0);
        
        // Initialize hasTarget and tx
        hasTarget = false;
        tx = 0;
    }

    @Override
    public void periodic() {
        tagList=cam.getLatestResult().getFiducialResults();
        systemState = handleTransition();
        applyStates();
    }

    private SystemState handleTransition() {
        double angle = getAngle();

        if (angle > 360 && systemState != SystemState.RELOCALIZING) {
            return SystemState.RELOCALIZING;
        }

        switch(wantedState) {
            case IDLE:
                return SystemState.IDLE;
            case HOME:
                return SystemState.HOME;
            case FINDING_POSITION:
                return SystemState.FINDING_POSITION;
            case RELOCALIZING:
                return SystemState.RELOCALIZING;
            case TARGET_POSITION:
                return SystemState.TARGET_POSITION;
            case MANUAL:
                return SystemState.MANUAL;
            default:
                return SystemState.IDLE;
        }
    }

    public void applyStates() {
        switch(systemState) {
            case IDLE:
                turretMotor.setPower(0);
                break;
            case HOME:
            case FINDING_POSITION:
            case RELOCALIZING:
                relocalize();
                break;
            case TARGET_POSITION:
                if (hasTarget) {
                    aimWithVision();
                } else {
                    // Even without a target, allow manual movement or keep at zero
                    // You might want to add a search pattern here
                    turretMotor.setPower(0);
                }
                break;
            case MANUAL:
                // Manual control is handled by setManualPowerControl()
                break;
        }
    }

    private void aimWithVision() {
        double currentAngle = getAngle();
        double targetAngle = currentAngle - tx;

        // Clamp to safe range
        targetAngle = Math.max(0, Math.min(360, targetAngle));

        double power = -positionController.calculate(currentAngle, targetAngle);
        
        // Add minimum power threshold to ensure motor moves
        if (Math.abs(power) < 0.05 && Math.abs(tx) > 0.5) {
            power = Math.signum(tx) * 0.05; // Minimum 5% power if there's a significant error
        }
        
        turretMotor.setPower(power);
    }

    private void relocalize() {
        double currentAngle = getAngle();
        double target = 0;

        double power = positionController.calculate(currentAngle, target);
        turretMotor.setPower(power);

        // When close enough, stop and go to idle or tracking
        if (Math.abs(currentAngle - 0) < 2.0) {  // 2 degree tolerance
            turretMotor.setPower(0);
            wantedState = WantedState.TARGET_POSITION; // automatically resume
        }
    }

    public void setManualPowerControl(double power) {
        wantedState = WantedState.MANUAL;
        turretMotor.setPower(power);
    }

    public void setLimelightData(double tx, boolean hasTarget) {
        this.tx = tx;
        this.hasTarget = hasTarget;
    }

    public void startTracking() {
        wantedState = WantedState.TARGET_POSITION;
    }

    public void forceReturnToZero() {
        wantedState = WantedState.RELOCALIZING;
    }
    public double getAngle() {
        return (turretMotor.getCurrentPosition() * (360.0 / 1024)) / 3;
    }
}