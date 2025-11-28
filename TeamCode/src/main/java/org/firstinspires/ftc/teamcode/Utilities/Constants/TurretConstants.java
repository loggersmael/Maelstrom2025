package org.firstinspires.ftc.teamcode.Utilities.Constants;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;

@Configurable
@Config
public class TurretConstants
{
    public static int startingPos=0;
    public static int maxPos=0;
    public static int minLimit=0;
    public static int maxLimit=2800;

    public static double kP= 0.05;
    public static double kI= 0;
    public static double kD= 0.0026;
    public static double kF= 0;
    public static double max=0;

    public static double angleTolerance=0;
}
