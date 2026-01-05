package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarNineBallRedPaths
{
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;

    public FarNineBallRedPaths(Follower follower) {
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
                                new Pose(44.000, 37.000).mirror(),
                                new Pose(7.800, 38.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(7.800, 38.000).mirror(),
                                new Pose(49.582, 31.077).mirror(),
                                new Pose(56.000, 18.000).mirror()
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 18.000).mirror(),
                                new Pose(21.351, 17.792).mirror(),
                                new Pose(9.015, 37.008).mirror(),
                                new Pose(10.201, 6.643).mirror()
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(340))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(10.201, 6.643).mirror(),
                                new Pose(7.591, 38.906).mirror(),
                                new Pose(34.399, 18.504).mirror(),
                                new Pose(56.100, 18.000).mirror()
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(340), Math.toRadians(0))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.100, 18.000).mirror(), new Pose(43.000, 18.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
}
