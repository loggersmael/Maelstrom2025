package org.firstinspires.ftc.teamcode.Utilities.Constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
@Config
public class DrivetrainConstants
{
    public static final String fLMotorID = "frontLeft";
    public static final String fRMotorID = "frontRight";
    public static final String bLMotorID = "backLeft";
    public static final String bRMotorID = "backRight";

    public static double kP=1;
    public static double kD=0.1;
    public static Pose startPose= new Pose(0,0,Math.toRadians(0));

    public static Pose redReset= new Pose(8.8,8.4,0);
    public static Pose blueReset= redReset.mirror();
}
