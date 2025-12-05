package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;


public class FinalShootCommand extends SequentialCommandGroup
{
    private Intake intake;
    private Shooter shooter;

    public FinalShootCommand(Maelstrom bot)
    {
        intake=bot.intake;
        shooter=bot.shooter;
        addCommands(
                new InstantCommand(intake::slowSpinOut),
                new WaitCommand(200),
                new InstantCommand(intake::stop),
                new WaitCommand(100),
                new InstantCommand(intake::kickerUp),
                new WaitCommand(500),
                new InstantCommand(intake::kickerDown),
                new WaitCommand(200),
                new InstantCommand(intake::spinIn),
                new WaitUntilCommand(intake::ballReady).withTimeout(1000),
                new WaitCommand(350),
                new InstantCommand(intake::stop),
                new InstantCommand(intake::spinOut),
                new WaitCommand(200),
                new InstantCommand(intake::stop),
                new WaitUntilCommand(shooter::atSpeed).alongWith(new WaitCommand(500)),
                new InstantCommand(intake::kickerUp),
                new WaitCommand(500),
                new InstantCommand(intake::kickerDown),
                new WaitCommand(300),
                new InstantCommand(intake::spinIn),
                new WaitUntilCommand(intake::ballReady).raceWith(new WaitCommand(2000)).alongWith(new WaitUntilCommand(shooter::atSpeed)),
                new WaitCommand(500),
                new InstantCommand(intake::kickerDown),
                new InstantCommand(intake::stop)
        );

        addRequirements(intake,shooter);
    }
}
