package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.Paths.DemoPaths;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

@TeleOp(name="DanceDemo")
public class DanceDemo extends LinearOpMode
{

    public Maelstrom robot;
    public DemoPaths paths;

    @Override
    public void runOpMode()
    {
        robot= new Maelstrom(hardwareMap,telemetry, Maelstrom.Alliance.BLUE,gamepad1,gamepad2);
        paths= new DemoPaths(robot.dt.follower);
        robot.dt.follower.setStartingPose(new Pose(36,108,Math.toRadians(270)));
        waitForStart();

        while(opModeIsActive())
        {
            robot.periodic();
            if(gamepad1.a)
            {
                if(!robot.dt.follower.isBusy()) {
                    robot.dt.follower.setPose(new Pose(36,108,Math.toRadians(270)));
                    robot.dt.follower.followPath(paths.Path1, true);
                }
            }

            if(gamepad1.b)
            {
                if(!robot.dt.follower.isBusy())
                {
                    robot.dt.follower.setPose(new Pose(38,33,Math.toRadians(270)));
                    robot.dt.follower.followPath(paths.Path2,true);
                }
            }
        }
    }
}
