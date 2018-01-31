package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XGamepad;
import robotx.libraries.XOpMode;

/**
 * Created by William on 12/1/17.
 */
@TeleOp(name = "JVControllerCodeWithJoysticks", group = "Default")

public class JVControllerCodeWithJoysticks extends XOpMode {

    DcMotor frontleftMotor;
    DcMotor frontrightMotor;
    DcMotor backleftMotor;
    DcMotor backrightMotor;
    Servo leftServo;
    Servo rightServo;
    DcMotor liftMotor;

    double power = 0.7;
    double power2 = 1.0;

    @Override

    public void init() {
        super.init();
        frontleftMotor = hardwareMap.dcMotor.get("FrontLeftMotor");
        frontrightMotor = hardwareMap.dcMotor.get("FrontRightMotor");
        backleftMotor = hardwareMap.dcMotor.get("BackLeftMotor");
        backrightMotor = hardwareMap.dcMotor.get("BackRightMotor");

        backrightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontrightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
/*
        rightServo = hardwareMap.servo.get("RightServo");
        leftServo = hardwareMap.servo.get("LeftServo");
*/
        rightServo = hardwareMap.servo.get("rightServo");
        leftServo = hardwareMap.servo.get("leftServo");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");
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
/*
        if (xGamepad2.right_bumper.isDown()) {

            rightServo.setPosition(1);
            leftServo.setPosition(1);

        } else if (xGamepad2.left_bumper.isDown()){

            rightServo.setPosition(1);
            leftServo.setPosition(1);

        } else {

            rightServo.setPosition(0);
            leftServo.setPosition(0);

        }
        */

        telemetry.addData("Front Left Motor:", frontleftMotor.getCurrentPosition());

        //DRIVING CODE
        double x = xGamepad1.left_stick_x;

        double y = xGamepad1.left_stick_y;

        //double rotationPower = xGamepad1.right_stick_x;
        double rotationPower = controlRamp(xGamepad1.right_stick_x);

        double rotX = x*0.707 - y*0.707;
        double rotY = x*0.707 + y*0.707;

        frontleftMotor.setPower(rotX+rotationPower);
        frontrightMotor.setPower(rotY+rotationPower);
        backleftMotor.setPower(rotY-rotationPower);
        backrightMotor.setPower(rotX-rotationPower);


        //GRABBER CODE

        double LServo = 0;
        double RServo = 0;
        boolean LPressed = false;
        boolean RPressed = false;

        telemetry.addData("Right Servo Position:",rightServo.getPosition());
        telemetry.addData("Left Servo Position:",leftServo.getPosition());

        if (xGamepad2.x.isDown())       //x will open the claw all the way
        {
            rightServo.setPosition(0.85);
            leftServo.setPosition(0.1);
        }

        else if (xGamepad2.y.isDown()) //y will set it to the optimal, middle position
        {
            rightServo.setPosition(0.35);
            leftServo.setPosition(0.55);

        }

        else if (xGamepad2.b.isDown())  //b will close the claw all the way
        {
            rightServo.setPosition(0.0);
            leftServo.setPosition(0.9);
        }


            //ADDITIONAL GRABBER CODE FOR BUMPERS
        if (xGamepad2.left_bumper.isDown())    //Left opens it
        {
            LPressed = true;
        }
        else if (xGamepad2.left_bumper.isUp())
        {
            LPressed = false;
        }

        if (LPressed == true)
        {
            LServo -= .05;
            RServo += .05;
            leftServo.setPosition(LServo);
            rightServo.setPosition(RServo);
        }

        if (xGamepad2.right_bumper.isDown())   //Right closes it
        {
            RPressed = true;
        }
        else if (xGamepad2.right_bumper.isUp())
        {
            RPressed = false;
        }

        if (RPressed == true)
        {
            LServo += .05;
            RServo -= .05;
            leftServo.setPosition(LServo);
            rightServo.setPosition(RServo);
        }


        //LIFT SYSTEM
        if (xGamepad2.dpad_up.isDown())
            {
                liftMotor.setPower(power2);

            }

            //Down
            else if (xGamepad2.dpad_down.isDown())
            {
                liftMotor.setPower(-power2);
            }

            else
            {
                liftMotor.setPower(0.0);
            }
    }


    public double controlRamp(double input) {

        double output = input;
        double coeff = 1.0;
        if (xGamepad1.right_stick_button.isDown()) {
            coeff = coeff / 1.0;
        }

        else if (xGamepad1.left_bumper.isDown()) {
            coeff = coeff / 4.0;
        }

        else if (xGamepad1.right_bumper.isDown()) {
            coeff = coeff / 4.0;
        }

        else
        {
            coeff = coeff / 2.0;
        }

        return output * coeff;
    }


    @Override
    public void stop()
    {
        super.stop();
    }
}
