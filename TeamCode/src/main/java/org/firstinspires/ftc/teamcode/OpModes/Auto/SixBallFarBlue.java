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
import org.firstinspires.ftc.teamcode.Commands.ShootCommandV2;
import org.firstinspires.ftc.teamcode.Commands.ShootWithSensor;
import org.firstinspires.ftc.teamcode.Paths.FarSixBallBluePaths;
import org.firstinspires.ftc.teamcode.Paths.NineBallBluePaths2;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@Autonomous(name="SixBallFarBlue")
public class SixBallFarBlue extends CommandOpMode
{

    private Maelstrom robot;
    private Follower follower;
    private FarSixBallBluePaths paths;

    @Override
    public void initialize()
    {
        robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        follower=robot.dt.follower;
        follower.setStartingPose(new Pose(56,9,Math.toRadians(180)));
        robot.shooter.setTargetVelocity(2000);
        paths= new FarSixBallBluePaths(follower);

        schedule(
                new WaitUntilCommand(this::opModeIsActive),
                new SequentialCommandGroup(
                        new InstantCommand(() -> robot.shooter.setHood(0.7)),
                        //new InstantCommand(() -> robot.turret.setTempOffset(-72)),
                        new ParallelCommandGroup(
                                        new InstantCommand(() -> robot.shooter.enableFlywheel()),
                                        new InstantCommand(() -> robot.turret.setPointMode()),
                                        new InstantCommand(() -> robot.turret.setManualAngle(-72)),
                                        new FollowPathCommand(follower,paths.Path1,true)
                        ),
                        new WaitCommand(500),
                        new ShootCommandV2(robot),
                        new InstantCommand(() -> robot.intake.spinIn()),
                        new FollowPath(robot,paths.Path2,true,1).withTimeout(2500),
                        new WaitCommand(750),
                        new InstantCommand(() -> robot.intake.idle()),
                        new FollowPathCommand(follower,paths.Path3),
                        new WaitCommand(500),
                        new InstantCommand(() -> robot.intake.stop()),
                        new ShootCommandV2(robot),
                        new FollowPathCommand(follower,paths.Path4),
                        new InstantCommand(() -> robot.shooter.stopFlywheel()),
                        new InstantCommand(() -> robot.turret.setManualAngle(0)),
                        new InstantCommand(() -> robot.reset())
                )
        );
    }
}
