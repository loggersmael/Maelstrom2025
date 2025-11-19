package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class DemoPaths
{
    public PathChain Path1;
    public PathChain Path2;

    public DemoPaths(Follower follower) {
        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(36.000, 108.000), new Pose(38.000, 33.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(38.000, 33.000),
                                new Pose(82.411, 71.038),
                                new Pose(36.000, 108.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();
    }
}
