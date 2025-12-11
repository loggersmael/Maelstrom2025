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

    public FarSixBallRedPaths(Follower follower) {
        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 9.000).mirror(), new Pose(56.000, 18.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 18.000).mirror(),
                                new Pose(57.173, 42.465).mirror(),
                                new Pose(44.125, 37.957).mirror(),
                                new Pose(7.800, 38.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(7.800, 35.822).mirror(),
                                new Pose(49.582, 31.077).mirror(),
                                new Pose(56.000, 18.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 18.000).mirror(), new Pose(39.000, 13.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
}
