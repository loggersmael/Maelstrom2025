package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kD;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kI;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.kP;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.knownArea;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.midVelocity;
import static org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants.velocityTolerance;

import android.sax.StartElementListener;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.ShooterConstants;

import java.util.List;

public class Shooter extends SubsystemBase
{
    private DcMotorEx shooterMotor;
    private Servo hoodServo;
    private Servo light;

   private Limelight3A cam;
   private Maelstrom.Alliance alliance;
    private Telemetry telemetry;
    private double currentVelocity;
    private double targetVelocity;
    public boolean flywheelOn;
    private List<LLResultTypes.FiducialResult> tagList;

    public Shooter(HardwareMap aHardwaremap, Telemetry telemetry, Maelstrom.Alliance color)
    {
        this.telemetry=telemetry;
        shooterMotor= aHardwaremap.get(DcMotorEx.class,"shooter");
        hoodServo= aHardwaremap.get(Servo.class,"hood");
        hoodServo.setDirection(Servo.Direction.REVERSE);
        light= aHardwaremap.get(Servo.class,"light");
        cam= aHardwaremap.get(Limelight3A.class,"limelight");
        tagList= cam.getLatestResult().getFiducialResults();
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setVelocityPIDFCoefficients(kP,kI,kD,0);
        alliance=color;
    }

    @Override
    public void periodic()
    {
        if(flywheelOn)
        {
            shooterMotor.setVelocity(targetVelocity);
        }
        else{
            shooterMotor.setPower(0);
        }
        currentVelocity=shooterMotor.getVelocity();
        setLight();
        telemetry.addData("Current Velocity: ", currentVelocity);
        telemetry.addData("Target Velocity: ", targetVelocity);
    }
    public void shootClose()
    {
        targetVelocity=ShooterConstants.closeVelocity;
    }

    public void shootFar()
    {
        targetVelocity=ShooterConstants.farVelocity;
    }
    public void shootMid()
    {
        targetVelocity=midVelocity;
    }

    public void reverseWheel()
    {
        shooterMotor.setPower(-0.1);
    }
    public void toggleFlywheel()
    {
        flywheelOn=!flywheelOn;
    }
    public LLResultTypes.FiducialResult getTag()
    {
        LLResultTypes.FiducialResult target= null;
        if(alliance.equals(Maelstrom.Alliance.BLUE))
        {
            if (tagList!=null)
            {
                for(LLResultTypes.FiducialResult tar:tagList)
                {
                    if(tar!=null && tar.getFiducialId() == 20)
                    {
                        target=tar;
                        break;
                    }
                }
            }
        }
        else if(alliance.equals(Maelstrom.Alliance.RED))
        {
            if(tagList!=null)
            {
                for(LLResultTypes.FiducialResult tar:tagList)
                {
                    if(tar!=null && tar.getFiducialId() == 24)
                    {
                        target=tar;
                        break;
                    }
                }
            }
        }
        return target;
    }

    public double getTargetDistance()
    {
        LLResultTypes.FiducialResult targ=getTag();
        if (targ==null)
        {
            return -320923;
        }
        double area=targ.getTargetArea();
        double hypotenuse= ShooterConstants.knownDistance*Math.sqrt(knownArea/area);
        return hypotenuse*Math.cos(ShooterConstants.llAngle);
    }
    public void stopFlywheel()
    {
        shooterMotor.setPower(0);
    }

    public void setHoodServo(double angle)
    {
        hoodServo.setPosition(angle);
    }

    public void setFullPower()
    {
        shooterMotor.setPower(1);
    }

    public void setLight()
    {
        if(atSpeed())
        {
            light.setPosition(0.5);
        }
        else
        {
            light.setPosition(0.277);
        }
    }

    public boolean atSpeed()
    {
        return Math.abs(currentVelocity-targetVelocity)<velocityTolerance;
    }
}
