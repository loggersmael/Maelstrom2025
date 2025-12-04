package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Utilities.Constants.IntakeConstants;

public class Intake extends SubsystemBase
{
    public enum intakeState{
        SPININ,SPINOUT,KICKING
    }
    private DcMotorEx intakeMotor;
    private Servo kicker;
    private RevColorSensorV3 sensor1;
    private RevColorSensorV3 sensor2;
    private NormalizedRGBA colors1;
    private NormalizedRGBA colors2;
    private Motor.Encoder intakeEncoder;
    private Telemetry telemetry;
    public Intake(HardwareMap aHardwareMap, Telemetry telemetry)
    {
        this.telemetry=telemetry;
        kicker= aHardwareMap.get(Servo.class,"kicker");
        intakeMotor= aHardwareMap.get(DcMotorEx.class,"intake");
        sensor1= aHardwareMap.get(RevColorSensorV3.class,"sensor1");
        sensor2= aHardwareMap.get(RevColorSensorV3.class,"sensor2");
        colors1=sensor1.getNormalizedColors();
        colors2=sensor2.getNormalizedColors();
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        kicker.setPosition(0.15);
    }

    @Override
    public void periodic()
    {
        colors1=sensor1.getNormalizedColors();
        colors2=sensor2.getNormalizedColors();

        telemetry.addData("Sensor 1 distance: ", sensor1.getDistance(DistanceUnit.INCH));
        telemetry.addData("Sensor 2 Distance: ", sensor2.getDistance(DistanceUnit.INCH));
        telemetry.addData("Sensor 1 Color: ",colors1.red + " " + colors1.green + " " + colors1.blue );
        telemetry.addData("Sensor 2 Color: ",colors2.red + " " + colors2.green + " " + colors2.blue );
    }
    public void spinIn()
    {
            intakeMotor.setPower(1);
    }
    public void slowSpinOut()
    {
        intakeMotor.setPower(-0.5);
    }
    public void spinOut()
    {
        intakeMotor.setPower(-1);
    }
    public void stop()
    {
        intakeMotor.setPower(0);
    }

    public boolean ballReady()
    {
        return sensor2.getDistance(DistanceUnit.INCH)<4;
    }

    public void kickerDown()
    {
        kicker.setPosition(0.15);
    }

    public void kickerUp()
    {
        kicker.setPosition(0.5);
    }

    public void kickerHalfway()
    {
        kicker.setPosition(0.15);
    }

}
