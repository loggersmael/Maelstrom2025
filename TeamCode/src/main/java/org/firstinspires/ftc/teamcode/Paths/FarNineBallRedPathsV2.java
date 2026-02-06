package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarNineBallRedPathsV2
{
    public PathChain Start;
    public PathChain Pickup1;
    public PathChain Pickup12;
    public PathChain Pickup13;
    public PathChain Pickup14;
    public PathChain Return1;
    public PathChain Pickup2;
    public PathChain Pickup22;
    public PathChain Return2;
    public PathChain Leave;

    public FarNineBallRedPathsV2(Follower follower) {
        Start = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 9.000).mirror(),

                                new Pose(56.000, 18.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Pickup1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 18.000).mirror(),

                                new Pose(15.574, 9.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Pickup12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.574, 9.000).mirror(),

                                new Pose(8.500, 8.500).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Pickup13 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(8.500, 8.500).mirror(),

                                new Pose(15.490, 8.500).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Pickup14 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.490, 8.500).mirror(),

                                new Pose(8.500, 8.500).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Return1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(8.500, 8.500).mirror(),

                                new Pose(56.000, 18.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Pickup2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 18.000).mirror(),

                                new Pose(46.371, 35.591).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Pickup22 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.371, 35.591).mirror(),

                                new Pose(7.800, 36.102).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Return2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(7.800, 36.102).mirror(),

                                new Pose(56.000, 18.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Leave = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 18.000).mirror(),

                                new Pose(43.000, 18.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();
    }
}
