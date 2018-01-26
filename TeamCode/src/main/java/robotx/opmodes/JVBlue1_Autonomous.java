package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "JVBlue1_Autonomous", group = "Default")

public class JVBlue1_Autonomous extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor frontleftMotor;
    DcMotor frontrightMotor;
    DcMotor backleftMotor;
    DcMotor backrightMotor;
    Servo leftServo;
    Servo rightServo;
    DcMotor liftMotor;

    ColorSensor armColor;
    Servo jewelServo;
    DcMotor jewelMotor;

    double power = 0.5;
    double power2 = 1.0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontleftMotor = hardwareMap.dcMotor.get("FrontLeftMotor");
        frontrightMotor = hardwareMap.dcMotor.get("FrontRightMotor");
        backleftMotor = hardwareMap.dcMotor.get("BackLeftMotor");
        backrightMotor = hardwareMap.dcMotor.get("BackRightMotor");

        rightServo = hardwareMap.servo.get("rightServo");
        leftServo = hardwareMap.servo.get("leftServo");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");

        armColor = hardwareMap.colorSensor.get("colorSensor");
        jewelServo = hardwareMap.servo.get("jewelServo");
        jewelMotor = hardwareMap.dcMotor.get("JewelMotor");

        waitForStart();
        runtime.reset();

            middleGrabber(0.1);
            jewelArmLower(2.5);
            stopRobot(3.0);
            colorEval(3.0);
            jewelArmRaise(2.5);
            liftUp(1.4);
            driveForward(0.8);
            rotateLeft(0.54);
            liftDown(1.4);
            driveForward(1.1);
            driveLeft(0.35);
            driveForward(0.8);
            openGrabber(0.1);
            driveBackward(0.2);
            closeGrabber(0.1);
            driveForward(1.0);
            driveBackward(0.2);
            jewelArmLower(0.2);
            stopRobot(0.1);

        frontleftMotor.setPower(0.0);
        frontrightMotor.setPower(0.0);
        backleftMotor.setPower(0.0);
        backrightMotor.setPower(0.0);


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
            // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
            // leftMotor.setPower(-gamepad1.left_stick_y);
            // rightMotor.setPower(-gamepad1.right_stick_y);
        }
    }

    //THE JEWEL ARM CODE

    boolean leftBallIsBlue;
    boolean leftBallIsRed;



    //servo positions (looking at the front)
    //right: 0.0
    //upright: 0.4
    //Left: 1.0

    public void jewelArmLower(double seconds)
    {
        jewelServo.setPosition(0.4);
        jewelMotor.setPower(0.5);

        rest(seconds);
    }

    public void colorEval(double seconds) {
        if (armColor.blue() > armColor.red()) {
            leftBallIsBlue = true;                  //when i say "left," i mean "left while looking at the balls from inside the field"
            leftBallIsRed = false;
        } else if (armColor.blue() < armColor.red()) {
            leftBallIsRed = true;
            leftBallIsBlue = false;
        }


        if (leftBallIsBlue)
        {
            telemetry.addData("Amount of Red: ", armColor.red());
            telemetry.addData("Amount of Blue", armColor.blue());
            telemetry.update();

            jewelServo.setPosition(0.0);

            rest(seconds);
        }

        else if (leftBallIsRed) {
            telemetry.addData("Amount of Red: ", armColor.red());
            telemetry.addData("Amount of Blue: ", armColor.blue());
            telemetry.update();

            jewelServo.setPosition(1.0);

            rest(seconds);
        }
    }


    public void jewelArmRaise(double seconds)
    {
        jewelMotor.setPower(-0.5);

        rest(seconds);
    }

    public void stopDriving(double seconds) {

        frontleftMotor.setPower(0.0);
        frontrightMotor.setPower(0.0);
        backleftMotor.setPower(0.0);
        backrightMotor.setPower(0.0);

        rest(seconds);
    }

    public void stopLifting(double seconds) {
        liftMotor.setPower(0.0);
    }

    public void driveForward(double seconds) {

        frontleftMotor.setPower(-power);
        frontrightMotor.setPower(power);
        backleftMotor.setPower(-power);
        backrightMotor.setPower(-power);

        rest(seconds);
        stopDriving(0);
    }

    public void stopRobot (double seconds) {

        frontleftMotor.setPower(0.0);
        frontrightMotor.setPower(0.0);
        backleftMotor.setPower(0.0);
        backrightMotor.setPower(0.0);
        liftMotor.setPower(0.0);
        jewelMotor.setPower(0.0);

        rest(seconds);
    }

    public void driveLeft(double seconds) {
        frontleftMotor.setPower(power);
        frontrightMotor.setPower(power);
        backleftMotor.setPower(-power);
        backrightMotor.setPower(power);

        rest(seconds);
        stopDriving(0);
    }

    public void driveRight(double seconds) {
        frontleftMotor.setPower(-power);
        frontrightMotor.setPower(-power);
        backleftMotor.setPower(power);
        backrightMotor.setPower(-power);

        rest(seconds);
        stopDriving(0);
    }

    public void driveBackward(double seconds) {
        frontleftMotor.setPower(power);
        frontrightMotor.setPower(-power);
        backleftMotor.setPower(power);
        backrightMotor.setPower(power);

        rest(seconds);
        stopDriving(0);
    }

    public void rotateLeft(double seconds) {
        frontleftMotor.setPower(power);
        frontrightMotor.setPower(power);
        backleftMotor.setPower(power);
        backrightMotor.setPower(-power);

        rest(seconds);
        stopDriving(0);

    }

    public void rotateRight(double seconds) {
        frontleftMotor.setPower(-power);
        frontrightMotor.setPower(-power);
        backleftMotor.setPower(-power);
        backrightMotor.setPower(power);

        rest(seconds);
        stopDriving(0);
    }

    public void liftUp (double seconds) {
        liftMotor.setPower(power2);

        rest(seconds);
        stopLifting(0);
    }

    public void liftDown (double seconds) {
        liftMotor.setPower(-power2);

        rest(seconds);
        stopLifting(0);
    }

    public void closeGrabber (double seconds) {
        rightServo.setPosition(0.0);
        leftServo.setPosition(0.9);

        rest(seconds);
    }

    public void middleGrabber (double seconds) {
        rightServo.setPosition(0.35);
        leftServo.setPosition(0.55);

        rest(seconds);
    }

    public void openGrabber (double seconds) {
        rightServo.setPosition(0.85);
        leftServo.setPosition(0.1);

        rest(seconds);
    }


    public void rest(double seconds) {
        sleep((long) (seconds * 1000));
    }
}
