package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities.Constants.IntakeConstants;

public class Intake extends SubsystemBase
{
    public enum intakeState{
        SPININ,SPINOUT,KICKING
    }
    private DcMotorEx intakeMotor;
    private Servo kicker;
    private Motor.Encoder intakeEncoder;
    private Telemetry telemetry;
    public Intake(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        this.telemetry=telemetry;
        kicker= aHardwareMap.get(Servo.class,"kicker");
        intakeMotor= aHardwareMap.get(DcMotorEx.class,"intake");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void periodic()
    {

    }
    public void spinIn()
    {
        intakeMotor.setPower(1);
    }
    public void spinOut()
    {
        intakeMotor.setPower(-1);
    }
    public void stop()
    {
        intakeMotor.setPower(0);
    }

    public void kickerDown()
    {
        kicker.setPosition(0.02);
    }

    public void kickerUp()
    {
        kicker.setPosition(0.35);
    }

    public void kickerHalfway()
    {
        kicker.setPosition(0.2);
    }

}
