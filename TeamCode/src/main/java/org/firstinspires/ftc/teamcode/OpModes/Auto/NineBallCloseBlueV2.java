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

import org.firstinspires.ftc.teamcode.Commands.FinalShootCommand;
import org.firstinspires.ftc.teamcode.Commands.FollowPath;
import org.firstinspires.ftc.teamcode.Commands.Shoot;
import org.firstinspires.ftc.teamcode.Commands.ShootCommandV2;
import org.firstinspires.ftc.teamcode.Commands.ShootWithKicker;
import org.firstinspires.ftc.teamcode.Commands.ShootWithSensor;
import org.firstinspires.ftc.teamcode.Paths.NineBallBluePaths;
import org.firstinspires.ftc.teamcode.Paths.NineBallBluePaths2;
import org.firstinspires.ftc.teamcode.Paths.SixBallBluePaths;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@Autonomous(name="NineBallCloseBlueV2")
public class NineBallCloseBlueV2 extends CommandOpMode
{
    private Maelstrom robot;
    private Follower follower;
    private NineBallBluePaths2 paths;

    @Override
    public void initialize()
    {
        robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        follower=robot.dt.follower;
        follower.setStartingPose(new Pose(25.5,129,Math.toRadians(143)));
        robot.shooter.shootMid();
        paths= new NineBallBluePaths2(follower);

        schedule(
                new WaitUntilCommand(this::opModeIsActive),
                new SequentialCommandGroup(
                        new WaitUntilCommand(this::opModeIsActive),
                        new SequentialCommandGroup(
                                new InstantCommand(() -> robot.shooter.setHood(0.15)),
                                new InstantCommand(() -> robot.turret.setTempOffset(-47)),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> robot.shooter.enableFlywheel()),
                                        new InstantCommand(() -> robot.turret.setPointMode()),
                                        new InstantCommand(() -> robot.turret.setManualAngle(-47)),
                                        new FollowPathCommand(follower,paths.Path1,true)
                                ),
                                new WaitCommand(50),
                                new ShootCommandV2(robot),
                                new InstantCommand(() -> robot.intake.spinIn()),
                                new FollowPathCommand(follower,paths.Path2,true,1),
                                new WaitCommand(500),
                                new InstantCommand(() -> robot.intake.idle()),
                                new FollowPathCommand(follower,paths.Path3,true),
                                new InstantCommand(robot.intake::stop),
                                new WaitCommand(50),
                                new ShootCommandV2(robot),
                                new InstantCommand(() -> robot.intake.spinIn()),
                                new FollowPath(robot,paths.Path4,true,0.9).withTimeout(2500),
                                new WaitCommand(500),
                                new InstantCommand(() -> robot.intake.idle()),
                                new FollowPathCommand(follower,paths.Path5),
                                new InstantCommand(() -> robot.intake.stop()),
                                new ShootCommandV2(robot),
                                new FollowPathCommand(follower,paths.Path6),
                                new InstantCommand(() -> robot.shooter.stopFlywheel()),
                                //new InstantCommand(() -> robot.turret.setManualAngle(0)),
                                new InstantCommand(() -> robot.reset())
                        )
                )
        );
    }
}
