package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

public class FollowPath extends CommandBase {
    private final Follower follower;
    private final PathChain path;
    private boolean holdEnd = true;
    private double maxPower = 1;
    private double completionThreshold = 0.99;

    public FollowPath(Maelstrom robot, PathChain pathChain) {
        this.follower = robot.dt.follower;
        this.path = pathChain;
        addRequirements(robot.dt);
    }

    public FollowPath(Maelstrom robot, PathChain pathChain, double maxPower) {
        this.follower = robot.dt.follower;
        this.path = pathChain;
        this.maxPower = maxPower;
        addRequirements(robot.dt);
    }

    public FollowPath(Maelstrom robot, PathChain pathChain, boolean holdEnd) {
        this.follower = robot.dt.follower;
        this.path = pathChain;
        this.holdEnd = holdEnd;
        addRequirements(robot.dt);
    }

    public FollowPath(Maelstrom robot, PathChain pathChain, boolean holdEnd, double maxPower) {
        this.follower = robot.dt.follower;
        this.path = pathChain;
        this.holdEnd = holdEnd;
        this.maxPower = maxPower;
        addRequirements(robot.dt);
    }

    public FollowPath setHoldEnd(boolean holdEnd) {
        this.holdEnd = holdEnd;
        return this;
    }

    public FollowPath setMaxPower(double power) {
        this.maxPower = power;
        return this;
    }

    public FollowPath setCompletionThreshold(double t) {
        this.completionThreshold = t;
        return this;
    }

    @Override
    public void initialize() {
        follower.setMaxPower(this.maxPower);
        follower.followPath(path, holdEnd);
    }

    @Override
    public boolean isFinished() {
        return follower.atParametricEnd();
    }

    @Override
    public void end(boolean interrupted) {
        follower.setMaxPower(1);
    }
}