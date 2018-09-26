package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XGamepad;
import robotx.libraries.XOpMode;

// Created by William on 9/14/2018

@TeleOp(name = "Omni_Stealth1", group = "Default")

public class Omni_Stealth1 extends XOpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;

    double power = 0.7;

    public void init() {
        super.init();

        leftMotor = hardwareMap.dcMotor.get("LeftMotor");
        rightMotor = hardwareMap.dcMotor.get("RightMotor");
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();

        /*float forwardBackAxis = xGamepad1.left_stick_y;
        float rotate = xGamepad1.right_stick_x;

        leftMotor.setPower(forwardBackAxis + rotate);        //going forward and backward
        rightMotor.setPower(forwardBackAxis - rotate);       //going forward and backward

        */

        if (xGamepad1.dpad_up.isDown())
        {
            leftMotor.setPower(power);
            rightMotor.setPower(-power);
        }

        if (xGamepad1.dpad_down.isDown())
        {
            leftMotor.setPower(-power);
            rightMotor.setPower(power);
        }

        if (xGamepad1.dpad_left.isDown())
        {
            leftMotor.setPower(0);
            rightMotor.setPower(power);
        }

        if (xGamepad1.dpad_right.isDown())
        {
            leftMotor.setPower(-power);
            rightMotor.setPower(0);
        }

        else
        {
            leftMotor.setPower(0.0);
            rightMotor.setPower(0.0);
        }
    }
}
