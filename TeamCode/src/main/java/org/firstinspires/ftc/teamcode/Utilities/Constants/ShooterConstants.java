package org.firstinspires.ftc.teamcode.Utilities.Constants;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;

@Configurable
@Config
public class ShooterConstants
{
    public static String shooterMotorID="shooter";
    public static String hoodServoID="hood";

    public static double knownDistance=0;
    public static double knownArea=0;
    public static double llAngle=0;
    public static double targetHeight=0;
    public static double shooterHeight=0;
    public static double transferEfficiency=0.8;
    public static double wheelRadius=0;
    public static double closeVelocity=1400;
    public static double farVelocity=1950;
    public static double midVelocity=1600;
    public static double velocityTolerance=20;

    public static double kP=0.003;
    public static double kI=0;
    public static double kD=0.00005;
    public static double kF=0.00055;
}
