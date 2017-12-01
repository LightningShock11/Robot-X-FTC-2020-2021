package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by William on 12/1/17.
 */
@TeleOp(name = "JVControllerCode", group = "Default")

public class JVControllerCode extends OpMode {

    DcMotor frontleftMotor;
    DcMotor frontrightMotor;
    DcMotor backleftMotor;
    DcMotor backrightMotor;

    double power = 0.5;

    frontleftMotor = hardwareMap.dcMotor.get("FrontLeftMotor");
    frontrightMotor = hardwareMap.dcMotor.get("FrontRightMotor");
    backleftMotor = hardwareMap.dcMotor.get("BackLeftMotor");
    backrightMotor = hardwareMap.dcMotor.get("BackRightMotor");

    @Override
    public void init() {

    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        //Forward
        frontleftMotor.setPower(-power);
        frontrightMotor.setPower(power);
        backleftMotor.setPower(-power);
        backrightMotor.setPower(-power);

        //Left
        frontleftMotor.setPower(power);
        frontrightMotor.setPower(power);
        backleftMotor.setPower(-power);
        backrightMotor.setPower(power);

        //Right
        frontleftMotor.setPower(-power);
        frontrightMotor.setPower(-power);
        backleftMotor.setPower(power);
        backrightMotor.setPower(-power);

        //Backward
        frontleftMotor.setPower(power);
        frontrightMotor.setPower(-power);
        backleftMotor.setPower(power);
        backrightMotor.setPower(power);


    }

    @Override
    public void stop() {

    }
}