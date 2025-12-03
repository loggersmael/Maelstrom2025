package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class TwelveBallBluePaths
{
    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    public PathChain Path8;
    public PathChain Path9;
    public PathChain Path10;
    public PathChain Path11;
    public PathChain Path12;

    public TwelveBallBluePaths(Follower follower) {
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
                        new BezierLine(new Pose(40.800, 84.200), new Pose(18.000, 84.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(18.000, 84.000),
                                new Pose(26.300, 76.400),
                                new Pose(16.100, 71.400)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(16.100, 71.400), new Pose(58.300, 85.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000),
                                new Pose(61.400, 60.300),
                                new Pose(40.800, 59.800)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path7 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.800, 59.800), new Pose(18.000, 59.800))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path8 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(18.000, 59.800),
                                new Pose(54.000, 60.000),
                                new Pose(58.300, 85.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path9 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(58.300, 85.000),
                                new Pose(60.000, 33.900),
                                new Pose(40.800, 36.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path10 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.800, 36.000), new Pose(18.000, 36.000))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path11 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(18.000, 36.000),
                                new Pose(52.000, 34.300),
                                new Pose(58.300, 85.000)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Path12 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(58.300, 85.000), new Pose(36.000, 84.600))
                )
                .setTangentHeadingInterpolation()
                .build();
    }
}
