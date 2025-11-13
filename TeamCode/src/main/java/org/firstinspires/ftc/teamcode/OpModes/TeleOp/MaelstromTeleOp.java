package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.util.TelemetryData;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Spindexer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;


@TeleOp(name = "Maelstrom TeleOp", group = "Competition")
public class MaelstromTeleOp extends CommandOpMode {
    // Subsystems
    private Drivetrain drivetrain;
    private Turret turret;
    private Shooter shooter;
    private Intake intake;
    private Spindexer spindexer;
    
    // Additional hardware
    private ServoEx kicker;
    
    // Gamepads
    private GamepadEx driver1;
    private GamepadEx driver2;
    
    // Telemetry
    private TelemetryData telemetryData;
    
    // Alliance color - change this based on which side you're on
    private Maelstrom.Alliance alliance = Maelstrom.Alliance.BLUE; // Change to RED if needed
    
    // Drive mode - set to false if PedroPathing is not tuned yet
    // true = field-centric (requires tuned odometry), false = robot-centric
    private boolean useFieldCentric = false; // Start with robot-centric until PedroPathing is tuned
    
    @Override
    public void initialize() {
        // Initialize telemetry
        telemetryData = new TelemetryData(telemetry);
        
        // Initialize gamepads
        driver1 = new GamepadEx(gamepad1);
        driver2 = new GamepadEx(gamepad2);
        
        // Initialize subsystems
        drivetrain = new Drivetrain(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry, alliance);
        shooter = new Shooter(hardwareMap, telemetry);
        intake = new Intake(hardwareMap, telemetry);
        spindexer = new Spindexer(hardwareMap, telemetry);
        
        // Initialize additional hardware
        kicker = new ServoEx(hardwareMap, "kicker");
        kicker.set(0);
        
        // Start PedroPathing teleop drive
        drivetrain.follower.startTeleopDrive();
        
        // Reset command scheduler
        super.reset();
        
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Alliance", alliance.toString());
        telemetry.update();
    }
    
    @Override
    public void run() {
        super.run();
        
        // ========== DRIVETRAIN ==========
        // Pass joystick values directly to PedroPathing via setMovementVectors
        // setMovementVectors calls follower.setTeleOpDrive() internally
        double forward = -driver1.getLeftY();
        double strafe = -driver1.getLeftX();
        double rotation = -driver1.getRightX();
        
        // Toggle between field-centric and robot-centric drive
        // Field-centric requires tuned PedroPathing odometry
        if (useFieldCentric) {
            // Field-centric: forward = away from driver station regardless of robot orientation
            drivetrain.setMovementVectors(strafe, forward, rotation, true);
        } else {
            // Robot-centric: forward = direction robot is facing
            drivetrain.setMovementVectors(strafe, forward, rotation, false);
        }
        
        // Toggle drive mode with Y button
        if (driver1.wasJustPressed(GamepadKeys.Button.Y)) {
            useFieldCentric = !useFieldCentric;
            telemetry.addData("Drive Mode", useFieldCentric ? "Field-Centric" : "Robot-Centric");
            telemetry.update();
        }
        
        // Reset heading with right stick button
        if (driver1.getButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)) {
            drivetrain.resetHeading();
        }
        
        // Update drivetrain periodic (updates follower and pose)
        drivetrain.periodic();
        
        // ========== TURRET ==========
        // Turret automatically tracks target via Limelight in periodic()
        // Limelight vision tracking runs automatically when target is visible
        // Update turret periodic (handles Limelight tracking)
        turret.periodic();
        
        // ========== INTAKE ==========
        if (driver2.getButton(GamepadKeys.Button.A)) {
            intake.spinIn();
        } else if (driver2.getButton(GamepadKeys.Button.B)) {
            intake.spinOut();
        } else {
            intake.stop();
        }
        
        // Update intake periodic
        intake.periodic();
        
        // ========== SHOOTER ==========
        if (driver2.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
            shooter.shootFar();
        } else if (driver2.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
            shooter.shootClose();
        } else {
            shooter.stopFlywheel();
        }
        
        // Update shooter periodic
        shooter.periodic();
        
        // ========== SPINDEXER ==========
        // Add spindexer controls as needed
        // Example:
        // if (driver2.getButton(GamepadKeys.Button.X)) {
        //     // Spindexer action
        // }
        
        // Update spindexer periodic
        spindexer.periodic();
        
        // ========== KICKER ==========
        if (driver2.getButton(GamepadKeys.Button.DPAD_UP)) {
            kicker.set(0.2);
        } else if (driver2.getButton(GamepadKeys.Button.DPAD_DOWN)) {
            kicker.set(0);
        }
        
        // ========== TELEMETRY ==========
        telemetryData.addData("=== DRIVETRAIN ===", "");
        telemetryData.addData("Drive Mode", useFieldCentric ? "Field-Centric" : "Robot-Centric");
        telemetryData.addData("X", drivetrain.getX());
        telemetryData.addData("Y", drivetrain.getY());
        telemetryData.addData("Heading (deg)", Math.toDegrees(drivetrain.getHeading()));
        
        telemetryData.addData("=== TURRET ===", "");
        telemetryData.addData("Target X", turret.getTargetX());
        telemetryData.addData("Tag Found", turret.getTag() != null);
        if (turret.getTag() != null) {
            telemetryData.addData("Tag ID", turret.getTag().getFiducialId());
        }
        
        telemetryData.addData("=== SUBSYSTEMS ===", "");
        telemetryData.addData("Drivetrain", "Active");
        telemetryData.addData("Turret", "Active");
        telemetryData.addData("Shooter", "Active");
        telemetryData.addData("Intake", "Active");
        telemetryData.addData("Spindexer", "Active");
        
        telemetryData.addData("=== GAMEPAD ===", "");
        telemetryData.addData("Driver 1 Forward", String.format("%.2f", forward));
        telemetryData.addData("Driver 1 Strafe", String.format("%.2f", strafe));
        telemetryData.addData("Driver 1 Rotation", String.format("%.2f", rotation));
        telemetryData.addData("Press Y to toggle", "drive mode");
        
        telemetryData.update();
    }
}

