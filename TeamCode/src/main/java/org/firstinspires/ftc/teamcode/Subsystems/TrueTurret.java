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

    private PIDFController positionController;
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
        turretMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cam=hMap.get(Limelight3A.class,"limelight");
        tagList= cam.getLatestResult().getFiducialResults();
        positionController = new PIDFController(0.1,0,0.01,0);
        frictionController = new SimpleMotorFeedforward(0,0,0);
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
                turretMotor.setPower(0);
                systemState =  SystemState.IDLE;
            case HOME:
                systemState = SystemState.HOME;
            case FINDING_POSITION:
                systemState = SystemState.FINDING_POSITION;
            case RELOCALIZING:
                systemState = SystemState.RELOCALIZING;
            case TARGET_POSITION:
                systemState = SystemState.TARGET_POSITION;
            case MANUAL:
                systemState = SystemState.MANUAL;
        }

        return systemState;
    }

    public void applyStates() {
        switch(systemState) {
            case IDLE:
            case HOME:
            case FINDING_POSITION:
            case RELOCALIZING:
                relocalize();
            case TARGET_POSITION:
                if (hasTarget) {
                    aimWithVision();
                } else {
                    turretMotor.setPower(0);
                }
            case MANUAL:
        }
    }

    private void aimWithVision() {
        double currentAngle = getAngle();
        double targetAngle = currentAngle + tx;

        // Clamp to safe range
        targetAngle = Math.max(0, Math.min(360, targetAngle));

        double power = positionController.calculate(currentAngle, targetAngle);
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
    private double getAngle() {
        return (turretMotor.getCurrentPosition() * (360.0 / 1024)) / 13;
    }
}