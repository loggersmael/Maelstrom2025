package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class NineBallRedPaths
{
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;

    public NineBallRedPaths(Follower follower) {
        Path1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.500, 129.000).mirror(),

                                new Pose(58.300, 85.000).mirror()
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(0))

                .build();

        Path2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.300, 85.000).mirror(),

                                new Pose(18.000, 84.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(18.000, 84.000).mirror(),

                                new Pose(58.300, 85.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000).mirror(),
                                new Pose(56.225, 56.000).mirror(),
                                new Pose(44.600, 57.275).mirror(),
                                new Pose(14.000, 57.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path5 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(14.000, 57.000).mirror(),
                                new Pose(49.927, 55.478).mirror(),
                                new Pose(58.300, 85.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Path6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.300, 85.000).mirror(),

                                new Pose(38.000, 85.000).mirror()
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();
    }
}
