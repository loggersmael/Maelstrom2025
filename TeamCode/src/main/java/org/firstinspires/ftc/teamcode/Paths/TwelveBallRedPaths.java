package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class TwelveBallRedPaths
{
    public PathChain Start;
    public PathChain Pickup1;
    public PathChain Gate;
    public PathChain Return1;
    public PathChain Pickup2;
    public PathChain Return2;
    public PathChain Pickup3;
    public PathChain Return3;
    public PathChain Leave;

    public TwelveBallRedPaths(Follower follower) {
        Start = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(25.500, 129.000).mirror(), new Pose(58.300, 85.000).mirror())
                )
                .setLinearHeadingInterpolation(Math.toRadians(37), Math.toRadians(0))
                .build();

        Pickup1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(58.300, 85.000).mirror(), new Pose(18.000, 84.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Gate = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(18.000, 84.000).mirror(),
                                new Pose(31.320, 76.287).mirror(),
                                new Pose(18.000, 70.513).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Return1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(18.000, 70.513).mirror(),
                                new Pose(51.000, 69.000).mirror(),
                                new Pose(58.300, 85.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Pickup2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000).mirror(),
                                new Pose(55.700, 57.400).mirror(),
                                new Pose(43.200, 55.000).mirror(),
                                new Pose(5.000, 54.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Return2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(5.000, 54.000).mirror(),
                                new Pose(53.891, 52.316).mirror(),
                                new Pose(58.300, 85.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Pickup3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000).mirror(),
                                new Pose(57.000, 38.700).mirror(),
                                new Pose(65.400, 33.800).mirror(),
                                new Pose(5.000, 34.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Return3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(5.000, 34.000).mirror(),
                                new Pose(58.090, 32.019).mirror(),
                                new Pose(58.300, 85.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Leave = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(58.300, 85.000).mirror(), new Pose(38.000, 85.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
}
