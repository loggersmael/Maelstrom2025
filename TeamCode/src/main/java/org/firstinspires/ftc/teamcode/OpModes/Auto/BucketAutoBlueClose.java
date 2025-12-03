package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Commands.FollowPath;
import org.firstinspires.ftc.teamcode.Paths.blueClose;

@Autonomous
public class BucketAutoBlueClose extends com.seattlesolvers.solverslib.command.CommandOpMode {

    private Maelstrom r;
    private GamepadEx g1, g2;

    @Override
    public void initialize(){

        r = new Maelstrom(hardwareMap, telemetry, Maelstrom.Alliance.BLUE, gamepad1, gamepad2);

        blueClose p = new blueClose(r);
        r.dt.follower.setStartingPose(p.getStartPose());


        schedule(
                new SequentialCommandGroup(
                        new WaitCommand(100),

                        // BUG: Missing wait between paths
                        new FollowPath(r, p.a1()),
                        new FollowPath(r, p.t1()),
                        new WaitCommand(500),
                        new FollowPath(r, p.s1()),
                        new WaitCommand(1500),


                        new FollowPath(r, p.a2()),
                        new FollowPath(r, p.t2()),
                        new FollowPath(r, p.s2()),

                        new WaitCommand(200),

                        new FollowPath(r, p.a3()),
                        new FollowPath(r, p.t3()),
                        new FollowPath(r, p.s3()),

                        new WaitCommand(200)

                )
        );
    }

    @Override
    public void run() {
        super.run();
    }
}