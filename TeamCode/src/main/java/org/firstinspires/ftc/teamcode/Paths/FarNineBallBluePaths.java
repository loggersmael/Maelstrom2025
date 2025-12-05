package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarNineBallBluePaths
{
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;

    public FarNineBallBluePaths(Follower follower) {
        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 9.000), new Pose(56.000, 18.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 18.000),
                                new Pose(57.173, 42.465),
                                new Pose(44.000, 37.000),
                                new Pose(7.800, 38.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(7.800, 38.000),
                                new Pose(49.582, 31.077),
                                new Pose(56.000, 18.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 18.000),
                                new Pose(21.351, 17.792),
                                new Pose(9.015, 37.008),
                                new Pose(10.201, 6.643)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(250))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(10.201, 6.643), new Pose(56.100, 18.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(250), Math.toRadians(180))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.100, 18.000), new Pose(43.000, 18.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }
}
