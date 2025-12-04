package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.Commands.FollowPath;
import org.firstinspires.ftc.teamcode.Commands.Shoot;
import org.firstinspires.ftc.teamcode.Commands.ShootWithKicker;
import org.firstinspires.ftc.teamcode.Commands.ShootWithSensor;
import org.firstinspires.ftc.teamcode.Paths.SixBallBluePaths;
import org.firstinspires.ftc.teamcode.Paths.SixBallBluePaths2;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@Autonomous(name="SixBallCloseBlueV2")
public class SixBallCloseBlueV2 extends CommandOpMode
{
    private Maelstrom robot;
    private Follower follower;
    private SixBallBluePaths2 paths;

    @Override
    public void initialize()
    {
        robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        follower=robot.dt.follower;
        follower.setStartingPose(new Pose(25.5,129 ,Math.toRadians(143)));
        follower.setMaxPower(1);
        robot.shooter.shootMid();
        paths= new SixBallBluePaths2(follower);

        schedule(
                new WaitUntilCommand(this::opModeIsActive),
                new SequentialCommandGroup(
                        new InstantCommand(() -> robot.shooter.hoodUp()),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> robot.shooter.enableFlywheel()),
                                new InstantCommand(() -> robot.turret.setPointMode()),
                                new InstantCommand(() -> robot.turret.setManualAngle(45)),
                                new FollowPathCommand(follower,paths.Path1,true)
                        ),
                        new WaitCommand(50),
                        new ShootWithSensor(robot),
                        new InstantCommand(() -> robot.intake.spinIn()),
                        new FollowPathCommand(follower,paths.Path2,true,1),
                        new WaitCommand(500),
                        new InstantCommand(() -> robot.intake.stop()),
                        new FollowPathCommand(follower,paths.Path3,true),
                        new WaitCommand(50),
                        new ShootWithSensor(robot),
                        new InstantCommand(() -> robot.shooter.stopFlywheel()),
                        new InstantCommand(() -> robot.turret.setManualAngle(0)),
                        new FollowPathCommand(follower,paths.Path4,true),
                        new InstantCommand(() -> robot.reset())
                )
        );
    }

    @Override
    public void run()
    {
        CommandScheduler.getInstance().run();
        telemetry.update();
    }
}
