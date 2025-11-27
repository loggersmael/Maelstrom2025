package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class SixBallRedPaths
{
    public PathChain line1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain line5;

    public SixBallRedPaths(Follower follower) {
        line1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(25.500, 129.000).mirror(), new Pose(58.300, 85.000).mirror())
                )
                .setLinearHeadingInterpolation(Math.toRadians(53), Math.toRadians(0))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(58.300, 85.000).mirror(), new Pose(40.800, 84.200).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.800, 84.200).mirror(), new Pose(18.000, 84.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(18.000, 84.000).mirror(), new Pose(58.300, 85.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        line5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000).mirror(),
                                new Pose(54.000, 76.100).mirror(),
                                new Pose(19.800, 72.600).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
}
