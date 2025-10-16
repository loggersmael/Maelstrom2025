package org.firstinspires.ftc.teamcode.Utilities.Constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class GlobalConstants {
    public enum OpModeType {
        AUTO,
        TELEOP
    }

    public static OpModeType currentOpMode;
}
