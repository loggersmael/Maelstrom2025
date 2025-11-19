package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

public class Intake extends CommandBase
{
    private Maelstrom robot;

    public Intake(Maelstrom bot)
    {
        this.robot=bot;
    }
}
