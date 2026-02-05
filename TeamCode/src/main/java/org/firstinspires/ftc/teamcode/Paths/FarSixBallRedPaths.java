package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarSixBallRedPaths
{
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;

    public FarSixBallRedPaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 9.000).mirror(),

                                new Pose(56.000, 18.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 18.000).mirror(),

                                new Pose(46.371, 35.591).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(46.371, 35.591).mirror(),

                                new Pose(7.800, 36.102).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(7.800, 36.102).mirror(),

                                new Pose(56.000, 18.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.000, 18.000).mirror(),

                                new Pose(43.000, 18.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();
    }
}
