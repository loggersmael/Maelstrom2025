package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.Paths.DemoPaths;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@Autonomous(name="DemoAuto")
public class DemoAuto extends CommandOpMode
{

    private Maelstrom robot;
    private DemoPaths chain;

    @Override
    public void initialize()
    {
        robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        robot.dt.follower.setStartingPose(new Pose(36,108,Math.toRadians(270)));
        chain= new DemoPaths(robot.dt.follower);

        schedule(
                new WaitUntilCommand(this::opModeIsActive),
                new SequentialCommandGroup(
                        new FollowPathCommand(robot.dt.follower, chain.Path1),
                        new WaitCommand(250),
                        new FollowPathCommand(robot.dt.follower, chain.Path2),
                        new InstantCommand(() -> robot.reset())
                ));
    }

    /*
    @Override
    public void runOpMode()
    {
        initialize();
        waitForStart();

        while (opModeIsActive())
        {
            run();
        }
        reset();
    } */

}
