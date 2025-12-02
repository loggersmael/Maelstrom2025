package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.Commands.Shoot;
import org.firstinspires.ftc.teamcode.Commands.ShootWithKicker;
import org.firstinspires.ftc.teamcode.Commands.ShootWithSensor;
import org.firstinspires.ftc.teamcode.Paths.SixBallBluePaths;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@Autonomous(name="SixBallCloseBlue")
public class SixBallCloseBlue extends CommandOpMode
{
    private Maelstrom robot;
    private Follower follower;
    private SixBallBluePaths paths;

    @Override
    public void initialize()
    {
        robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        follower=robot.dt.follower;
        follower.setStartingPose(new Pose(25.5,129,Math.toRadians(143)));
        robot.shooter.shootMid();
        paths= new SixBallBluePaths(follower);

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
                        new ShootWithSensor(robot),
                        new FollowPathCommand(follower,paths.Path2,true),
                        new WaitCommand(1000),
                        new InstantCommand(() -> robot.intake.spinIn()),
                        new FollowPathCommand(follower,paths.Path3,true,0.5),
                        new InstantCommand(() -> robot.intake.stop()),
                        new FollowPathCommand(follower,paths.Path4,true),
                        new WaitCommand(1000),
                        new ShootWithSensor(robot),
                        new InstantCommand(() -> robot.shooter.stopFlywheel()),
                        new InstantCommand(() -> robot.turret.setManualAngle(0)),
                        new FollowPathCommand(follower,paths.Path5,true),
                        new InstantCommand(() -> robot.reset())
                )
        );
    }
}
