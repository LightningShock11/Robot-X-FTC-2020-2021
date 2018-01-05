package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.XOpMode;

/**
 * Created by William on 12/1/17.
 */
@TeleOp(name = "JVControllerCode", group = "Default")

public class JVControllerCode extends XOpMode {

    DcMotor frontleftMotor;
    DcMotor frontrightMotor;
    DcMotor backleftMotor;
    DcMotor backrightMotor;

    double power = 0.5;

    @Override
    public void init() {
        super.init();
        frontleftMotor = hardwareMap.dcMotor.get("FrontLeftMotor");
        frontrightMotor = hardwareMap.dcMotor.get("FrontRightMotor");
        backleftMotor = hardwareMap.dcMotor.get("BackLeftMotor");
        backrightMotor = hardwareMap.dcMotor.get("BackRightMotor");

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

        //Forward
        if (xGamepad1.dpad_up.isDown()) {

            frontleftMotor.setPower(-power);
            frontrightMotor.setPower(power);
            backleftMotor.setPower(-power);
            backrightMotor.setPower(-power);
        }

        //Left
        else if (xGamepad1.dpad_left.isDown()) {

            frontleftMotor.setPower(power);
            frontrightMotor.setPower(power);
            backleftMotor.setPower(-power);
            backrightMotor.setPower(power);
        }

        //Right
        else if (xGamepad1.dpad_right.isDown()) {

            frontleftMotor.setPower(-power);
            frontrightMotor.setPower(-power);
            backleftMotor.setPower(power);
            backrightMotor.setPower(-power);
        }

        //Backward
        else if (xGamepad1.dpad_down.isDown()) {

            frontleftMotor.setPower(power);
            frontrightMotor.setPower(-power);
            backleftMotor.setPower(power);
            backrightMotor.setPower(power);
        }

        else {
            frontleftMotor.setPower(0.0);
            frontrightMotor.setPower(0.0);
            backleftMotor.setPower(0.0);
            backrightMotor.setPower(0.0);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}