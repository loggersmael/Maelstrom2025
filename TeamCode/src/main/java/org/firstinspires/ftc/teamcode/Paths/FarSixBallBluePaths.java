package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarSixBallBluePaths
{
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;

    public FarSixBallBluePaths(Follower follower) {
        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 9.000), new Pose(56.000, 16.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 16.000),
                                new Pose(57.173, 42.465),
                                new Pose(39.855, 35.110),
                                new Pose(7.800, 35.822)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(7.800, 35.822),
                                new Pose(49.582, 31.077),
                                new Pose(56.000, 16.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 16.000), new Pose(39.000, 13.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }
}
