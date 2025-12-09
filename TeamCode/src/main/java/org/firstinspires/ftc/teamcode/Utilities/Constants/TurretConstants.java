package org.firstinspires.ftc.teamcode.Utilities.Constants;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

@Configurable
@Config
public class TurretConstants
{
    public static int startingPos=0;
    public static int maxPos=0;
    public static int minLimit=0;
    public static int maxLimit=2800;

    public static double kP= 0.03;
    public static double kI= 0;
    public static double kD= 0.001;
    public static double kF= 0.0005;

    public static double sP= 0.02125; //0.04;
    public static double sI=0;
    public static double sD=0.0035;
    public static double sF=0.0005;
    public static double max=0;

    public static double PIDFSwitch=20;

    public static double angleTolerance=10;

    public static Pose blueGoal= new Pose(0,144);
    public static Pose redGoal= new Pose(144,144);
}
