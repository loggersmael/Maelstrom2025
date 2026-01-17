package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class TwelveBallBluePaths
{
    public PathChain Start;
    public PathChain Pickup1;
    public PathChain Gate;
    public PathChain Return1;
    public PathChain Pickup2;
    public PathChain Return2;
    public PathChain Pickup3;
    public PathChain Return3;

    public TwelveBallBluePaths(Follower follower) {
        Start = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(25.500, 129.000), new Pose(58.300, 85.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))
                .build();

        Pickup1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000),
                                new Pose(55.700, 59.400),
                                new Pose(43.200, 57.000),
                                new Pose(5.000, 56.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Gate = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(5.000, 54.000),
                                new Pose(36, 62),
                                new Pose(18.000, 70.513)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Return1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(18.000, 70.513),
                                new Pose(62.000, 70.000),
                                new Pose(58.300, 85.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Pickup2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(58.300, 85.000), new Pose(17.000, 84.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Return2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(18.000, 84.000), new Pose(58.300, 85.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Pickup3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000),
                                new Pose(57.000, 37.700),
                                new Pose(65.400, 32.800),
                                new Pose(5.000, 33.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Return3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(5.000, 34.000),
                                new Pose(58.090, 32.019),
                                new Pose(57.000, 107.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(143))
                .build();
    }
}
