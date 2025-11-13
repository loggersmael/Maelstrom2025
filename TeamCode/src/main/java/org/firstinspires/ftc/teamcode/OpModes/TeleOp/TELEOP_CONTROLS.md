# Maelstrom TeleOp Controls Documentation

## Overview
This document describes all button mappings and controls for the Maelstrom robot TeleOp mode.

**OpMode Name:** `Maelstrom TeleOp`  
**Group:** `Competition`

---

## Gamepad 1 (Driver 1) - Drivetrain Control

### Joysticks
| Control | Function | Description |
|---------|----------|-------------|
| **Left Stick Y** | Forward/Backward | Controls robot forward and backward movement |
| **Left Stick X** | Strafe Left/Right | Controls robot left and right strafing |
| **Right Stick X** | Rotation | Controls robot rotation (turning) |

### Buttons
| Button | Function | Description |
|--------|----------|-------------|
| **Y** | Toggle Drive Mode | Toggles between Field-Centric and Robot-Centric drive |
| **Right Stick Button** | Reset Heading | Resets the robot's heading to 0 degrees (useful for field-centric) |

### Drive Modes
- **Robot-Centric (Default):** Forward = direction robot is facing (works without tuning)
- **Field-Centric:** Forward = away from driver station (requires tuned PedroPathing odometry)
  - Toggle with **Y button** during TeleOp
  - Check telemetry to see current mode

---

## Gamepad 2 (Driver 2) - Subsystem Control

### Intake Controls
| Button | Function | Description |
|--------|----------|-------------|
| **A** | Intake In | Spins intake motor forward to collect game elements |
| **B** | Intake Out | Spins intake motor backward to eject game elements |
| *(No button)* | Stop | Intake stops when no button is pressed |

### Shooter Controls
| Button | Function | Description |
|--------|----------|-------------|
| **Right Bumper** | Shoot Far | Sets shooter to far velocity (high speed) |
| **Left Bumper** | Shoot Close | Sets shooter to close velocity (low speed) |
| *(No button)* | Stop Flywheel | Shooter stops when no button is pressed |

### Turret Controls
| Control | Function | Description |
|---------|----------|-------------|
| **Automatic** | Limelight Tracking | Turret automatically tracks AprilTags via Limelight vision |
| | | - Blue Alliance: Tracks Tag ID 20 |
| | | - Red Alliance: Tracks Tag ID 24 |
| | | - Tracking runs continuously in background |

### Kicker Controls
| Button | Function | Description |
|--------|----------|-------------|
| **D-Pad Up** | Extend Kicker | Sets kicker servo to extended position (0.2) |
| **D-Pad Down** | Retract Kicker | Sets kicker servo to retracted position (0.0) |

### Spindexer Controls
| Button | Function | Description |
|--------|----------|-------------|
| *(Not yet mapped)* | - | Spindexer controls to be added as needed |

---

## Subsystems

### Active Subsystems
All subsystems are automatically initialized and updated:

1. **Drivetrain** - PedroPathing follower with odometry
2. **Turret** - Limelight vision tracking system
3. **Shooter** - Flywheel and hood control
4. **Intake** - Intake motor control
5. **Spindexer** - Spinner mechanism

### Subsystem Periodic Updates
All subsystems' `periodic()` methods are called automatically each loop cycle:
- Drivetrain updates pose and follower
- Turret updates Limelight tracking
- Other subsystems maintain their state

---

## Telemetry Information

The TeleOp displays the following information:

### Drivetrain
- Drive Mode (Field-Centric / Robot-Centric)
- X Position (inches)
- Y Position (inches)
- Heading (degrees)

### Turret
- Target X (pixels from center)
- Tag Found (true/false)
- Tag ID (when tag is visible)

### Subsystems Status
- Shows which subsystems are active

### Gamepad Inputs
- Driver 1 joystick values (Forward, Strafe, Rotation)

---

## Configuration

### Alliance Color
Change alliance in code (line 39):
```java
private Maelstrom.Alliance alliance = Maelstrom.Alliance.BLUE; // or RED
```

### Default Drive Mode
Change default drive mode in code (line 43):
```java
private boolean useFieldCentric = false; // true for field-centric, false for robot-centric
```

---

## Notes

1. **Field-Centric Drive:** Requires PedroPathing odometry to be tuned. If not tuned, use Robot-Centric mode.

2. **Turret Tracking:** The turret automatically tracks AprilTags when visible. No manual control needed unless you want to override.

3. **Subsystem Methods:** All subsystems use their respective subsystem classes:
   - `Drivetrain` - Uses PedroPathing follower
   - `Turret` - Uses Limelight vision
   - `Shooter` - Uses velocity control
   - `Intake` - Uses power control
   - `Spindexer` - Ready for controls

4. **CommandOpMode:** This TeleOp extends `CommandOpMode` from SolversLib, which provides:
   - Command scheduling system
   - Subsystem management
   - Structured code organization

---

## Quick Reference

### Driver 1 (Gamepad 1)
- **Left Stick:** Drive
- **Right Stick:** Rotate
- **Y:** Toggle drive mode
- **Right Stick Button:** Reset heading

### Driver 2 (Gamepad 2)
- **A:** Intake in
- **B:** Intake out
- **Right Bumper:** Shoot far
- **Left Bumper:** Shoot close
- **D-Pad Up:** Kicker extend
- **D-Pad Down:** Kicker retract

---

## Troubleshooting

### Robot doesn't move
- Check if PedroPathing follower is initialized
- Verify drivetrain motors are configured correctly

### Turret not tracking
- Check Limelight is connected and configured
- Verify correct pipeline is selected (pipeline 1)
- Check alliance color matches game setup
- Ensure AprilTag is visible to camera

### Field-centric not working
- Ensure PedroPathing odometry is tuned
- Use Robot-Centric mode until tuning is complete
- Check telemetry for pose accuracy

---

**Last Updated:** 2025  
**Robot:** Maelstrom  
**Season:** 2025

