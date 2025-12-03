package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

public class blueClose {
    private final Follower f;
    private static final double h1 = Math.toRadians(90);

    private Pose p0;
    private Pose p1a;

    private Pose p1p;
    private Pose p1s;

    private Pose p2a;
    private Pose p2p;
    private Pose p2c;
    private Pose p2s;

    private Pose p3a;
    private Pose p3p;
    private Pose p3s;

    private int idx = 0;
    private static final int tot = 9;

    public blueClose(Maelstrom robot) {
        this.f = robot.dt.follower;
        init();
    }

    private void init() {
        p0 = new Pose(53.73, 142.6, h1);

        p1a = new Pose(40.78, 94.09, h1);
        p1p = new Pose(16.57, 93.03, h1);
        p1s = new Pose(84.74, 53.95, h1);

        p2a = new Pose(43.33, 69.03, h1);
        p2p = new Pose(16.57, 69.45, h1);
        p2c = new Pose(63.08, 53.31, h1);
        p2s = new Pose(51.19, 30.58, h1);

        p3a = new Pose(36.96, 44.81, h1);
        p3p = new Pose(12.74, 44.81, h1);
        p3s = new Pose(49.91, 23.58, h1);

        idx = 0;
    }

    public PathChain a1() {
        return f.pathBuilder()
                .addPath(new BezierLine(p0, p1a))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain t1() {
        return f.pathBuilder()
                .addPath(new BezierLine(p1a, p1p))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain s1() {
        return f.pathBuilder()
                .addPath(new BezierLine(p1p, p1s))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain a2() {
        return f.pathBuilder()
                .addPath(new BezierLine(p1s, p2a))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain t2() {
        return f.pathBuilder()
                .addPath(new BezierLine(p2a, p2p))
                .setConstantHeadingInterpolation(h1)
                .setReversed()
                .build();
    }

    public PathChain s2() {
        return f.pathBuilder()
                .addPath(new BezierCurve(
                        p2p,
                        p2c,
                        p2s
                ))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain a3() {
        return f.pathBuilder()
                .addPath(new BezierLine(p2s, p3a))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain t3() {
        return f.pathBuilder()
                .addPath(new BezierLine(p3a, p3p))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain s3() {
        return f.pathBuilder()
                .addPath(new BezierLine(p3p, p3s))
                .setConstantHeadingInterpolation(h1)
                .build();
    }

    public PathChain next() {
        switch (idx++) {
            case 0:  return a1();
            case 1:  return t1();
            case 2:  return s1();
            case 3:  return a2();
            case 4:  return t2();
            case 5:  return s2();
            case 6:  return a3();
            case 7:  return t3();
            case 8:  return s3();
            default: return null;
        }
    }

    public boolean hasNext() {
        return idx < tot;
    }

    public void reset() {
        idx = 0;
    }

    public int getCurrentIndex() {
        return idx;
    }

    public Pose getStartPose() {
        return p0;
    }
}