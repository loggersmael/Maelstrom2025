package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

import java.lang.reflect.MalformedParameterizedTypeException;

public class Shoot extends CommandBase
{
    private final Maelstrom robot;

    public Shoot(Maelstrom bot)
    {
        this.robot=bot;
    }



}
