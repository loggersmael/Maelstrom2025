package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarSixBallBluePaths
{
    public PathChain Path1;
    public PathChain line2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;

    public FarSixBallBluePaths(Follower follower) {
        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 9.000), new Pose(56.165, 15.747))
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        line2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.165, 15.747),
                                new Pose(55.815, 37.269),
                                new Pose(40.768, 36.044)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.768, 36.044), new Pose(23.096, 36.219))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(23.096, 36.219), new Pose(56.000, 9.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
                .setReversed()
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 9.000), new Pose(37.269, 12.948))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();
    }
}
