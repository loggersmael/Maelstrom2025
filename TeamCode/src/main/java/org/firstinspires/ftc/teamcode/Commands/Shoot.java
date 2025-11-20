package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.util.Timer;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

import java.lang.reflect.MalformedParameterizedTypeException;

public class Shoot extends CommandBase
{
    private final Maelstrom robot;
    private int state=1;
    private Timer tTimer= new Timer();

    public Shoot(Maelstrom bot)
    {
        this.robot=bot;
    }

    @Override
    public void initialize()
    {
        setState(1);
    }

    @Override
    public void execute()
    {
        switch(state)
        {
            case 1:
                robot.intake.spinIn();
                setState(2);
                break;
            case 2:
                if(tTimer.getElapsedTimeSeconds()>1)
                {
                    robot.intake.stop();
                    setState(3);
                }
                break;
            case 3:
                if(tTimer.getElapsedTimeSeconds()>0.5 && robot.shooter.atSpeed())
                {
                    robot.intake.kickerUp();
                    setState(4);
                }
                break;
            case 4:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    robot.intake.kickerDown();
                    setState(5);
                }
                break;
            case 5:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    robot.intake.spinIn();
                    setState(6);
                }
                tTimer.resetTimer();
                break;
            case 6:
                if(tTimer.getElapsedTimeSeconds()>2 && robot.shooter.atSpeed())
                {
                    robot.intake.kickerUp();
                    setState(7);
                }
                tTimer.resetTimer();
                break;
            case 7:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    robot.intake.kickerDown();
                    robot.intake.stop();
                    setState(-1);
                }
                tTimer.resetTimer();
                break;

        }
    }

    @Override
    public boolean isFinished()
    {
        return state==-1;
    }

    public void setState(int x)
    {
        state=x;
        tTimer.resetTimer();
    }
}
