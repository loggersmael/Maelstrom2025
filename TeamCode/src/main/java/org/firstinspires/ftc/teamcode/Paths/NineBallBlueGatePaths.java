package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class NineBallBlueGatePaths {
    public PathChain Start;
    public PathChain Pickup1;
    public PathChain Gate;
    public PathChain Return1;
    public PathChain Pickup2;
    public PathChain Return2;
    public PathChain Leave;

    public NineBallBlueGatePaths(Follower follower) {
        Start = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.500, 129.000),

                                new Pose(58.300, 85.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))

                .build();

        Pickup1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000),
                                new Pose(55.700, 57.400),
                                new Pose(43.200, 55.000),
                                new Pose(5.000, 54.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Gate = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(5.000, 54.000),
                                new Pose(30.366, 56.936),
                                new Pose(18.000, 70.513)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Return1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(18.000, 70.513),
                                new Pose(62.000, 70.000),
                                new Pose(58.300, 85.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Pickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.300, 85.000),

                                new Pose(18.000, 84.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Return2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(18.000, 84.000),

                                new Pose(58.300, 85.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();

        Leave = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.300, 85.000),

                                new Pose(38.000, 85.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(180))

                .build();
    }
}