package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class NineBallBluePaths
{
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    public PathChain Path8;

    public NineBallBluePaths(Follower follower) {
        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(25.500, 129.000), new Pose(58.300, 85.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(180))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(58.300, 85.000), new Pose(40.800, 84.200))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.800, 84.200), new Pose(17.000, 84.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(17.000, 84.000), new Pose(58.300, 85.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000),
                                new Pose(59.840, 62.814),
                                new Pose(41.118, 60.190)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(41.118, 60.190), new Pose(17.547, 59.665))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path7 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(17.547, 59.665),
                                new Pose(52.841, 64.914),
                                new Pose(58.3, 85)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        Path8 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000),
                                new Pose(54.000, 76.100),
                                new Pose(19.800, 72.600)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }
}
