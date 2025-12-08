package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@Disabled
@TeleOp(name="test2")
public class Test2 extends OpMode
{
    private DcMotor intake;
    private DcMotor shooter;
    @Override
    public void init()
    {        intake= hardwareMap.get(DcMotor.class,"intake");
        shooter= hardwareMap.get(DcMotor.class,"shooter");
    }
    @Override
    public void loop()
    {
        if(gamepad1.a)
        {
            intake.setPower(-1);
        }
        else {
            intake.setPower(0);
        }

        if(gamepad1.b)
        {
            intake.setPower(1);
        }
        else {
            intake.setPower(0);
        }

        if(gamepad1.right_bumper)
        {
            shooter.setPower(1);
        }
        else {
            shooter.setPower(0);
        }

        if(gamepad1.left_bumper)
        {
            shooter.setPower(-1);
        }
        else {
            shooter.setPower(0);
        }
    }
}
