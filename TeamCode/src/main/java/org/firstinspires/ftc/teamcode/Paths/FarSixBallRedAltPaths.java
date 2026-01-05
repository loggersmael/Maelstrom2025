package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class FarSixBallRedAltPaths
{
    public PathChain Start;
    public PathChain Pickup1;
    public PathChain Return1;
    public PathChain Leave;

    public FarSixBallRedAltPaths(Follower follower) {
        Start = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 9.000).mirror(), new Pose(56.000, 18.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        Pickup1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 18.000).mirror(),
                                new Pose(9.600, 27.000).mirror(),
                                new Pose(11.700, 21.000).mirror(),
                                new Pose(7.900, 6.500).mirror()
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        Return1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(7.900, 6.500).mirror(),
                                new Pose(13.100, 34.500).mirror(),
                                new Pose(20.600, 19.600).mirror(),
                                new Pose(56.000, 18.000).mirror()
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(340), Math.toRadians(0))
                .setReversed()
                .build();

        Leave = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 18.000).mirror(), new Pose(43.000, 18.000).mirror())
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }
}
