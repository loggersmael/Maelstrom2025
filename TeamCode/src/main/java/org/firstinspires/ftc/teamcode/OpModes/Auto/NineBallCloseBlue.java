package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.Commands.Shoot;
import org.firstinspires.ftc.teamcode.Paths.NineBallBluePaths;
import org.firstinspires.ftc.teamcode.Paths.SixBallBluePaths;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

public class NineBallCloseBlue extends CommandOpMode
{
    private Maelstrom robot;
    private Follower follower;
    private NineBallBluePaths paths;

    @Override
    public void initialize()
    {
        robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        follower=robot.dt.follower;
        follower.setStartingPose(new Pose(56,9,Math.toRadians(90)));
        robot.shooter.shootMid();
        paths= new NineBallBluePaths(follower);

        schedule(
                new WaitUntilCommand(this::opModeIsActive),
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.shooter.enableFlywheel()),
                                new InstantCommand(() -> robot.turret.setPointMode()),
                                new InstantCommand(() -> robot.turret.setManualAngle(40)),
                                new FollowPathCommand(follower,paths.Path1,true)
                        ),
                        new WaitCommand(1000),
                        new Shoot(robot),
                        new FollowPathCommand(follower,paths.Path2),
                        new WaitCommand(1000),
                        new InstantCommand(() -> robot.intake.spinIn()),
                        new FollowPathCommand(follower,paths.Path3,0.5),
                        new InstantCommand(() -> robot.intake.stop()),
                        new FollowPathCommand(follower,paths.Path4,true),
                        new WaitCommand(1000),
                        new Shoot(robot),
                        new WaitCommand(1000),
                        new FollowPathCommand(follower,paths.Path5,true),
                        new WaitCommand(1000),
                        new InstantCommand(() -> robot.intake.spinIn()),
                        new FollowPathCommand(follower,paths.Path6,0.5),
                        new InstantCommand(() -> robot.intake.stop()),
                        new FollowPathCommand(follower,paths.Path7,true),
                        new WaitCommand(1000),
                        new Shoot(robot),
                        new WaitCommand(1000),
                        new InstantCommand(() -> robot.shooter.stopFlywheel()),
                        new InstantCommand(() -> robot.turret.setManualAngle(0)),
                        new FollowPathCommand(follower,paths.Path8,true),
                        new InstantCommand(() -> robot.reset())
                )
        );
    }
}
