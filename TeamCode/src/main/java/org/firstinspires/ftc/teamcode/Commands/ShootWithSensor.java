package org.firstinspires.ftc.teamcode.Commands;

import com.pedropathing.util.Timer;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Maelstrom;

public class ShootWithSensor extends CommandBase
{
    private final Maelstrom robot;
    private int state=0;
    private Timer tTimer= new Timer();

    public ShootWithSensor(Maelstrom bot)
    {
        this.robot=bot;
        addRequirements(robot.intake);
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
                robot.intake.slowSpinOut();
                setState(2);
                break;
            case 2:
                if(tTimer.getElapsedTimeSeconds()>0.1)
                {
                    robot.intake.stop();
                    setState(3);
                }
                break;
            case 3:
                if(tTimer.getElapsedTimeSeconds()>0.1)
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
                break;
            case 6:
                if(tTimer.getElapsedTimeSeconds()>1 || robot.intake.ballReady())
                {
                    robot.intake.stop();
                    setState(7);
                }
                break;
            case 7:
                if(tTimer.getElapsedTimeSeconds()>0.5 /*&& robot.shooter.atSpeed()*/)
                {
                    robot.intake.kickerUp();
                    setState(8);
                }
                break;
            case 8:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    robot.intake.kickerDown();
                    setState(9);
                }
                break;
            case 9:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    robot.intake.spinIn();
                    setState(10);
                }
                break;
            case 10:
                if(tTimer.getElapsedTimeSeconds()>2 /*&& robot.shooter.atSpeed()*/ || robot.intake.ballReady())
                {
                    robot.intake.kickerUp();
                    setState(11);
                }
                break;
            case 11:
                if(tTimer.getElapsedTimeSeconds()>0.5)
                {
                    robot.intake.kickerDown();
                    robot.intake.stop();
                    setState(-1);
                }
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
