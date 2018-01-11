package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "JVRed2_Autonomous", group = "Default")

public class JVRed2_Autonomous extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor frontleftMotor;
    DcMotor frontrightMotor;
    DcMotor backleftMotor;
    DcMotor backrightMotor;
    Servo leftServo;
    Servo rightServo;
    DcMotor liftMotor;

    double power = 0.5;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontleftMotor = hardwareMap.dcMotor.get("Front_Left_Motor");
        frontrightMotor = hardwareMap.dcMotor.get("Front_Right_Motor");
        backleftMotor = hardwareMap.dcMotor.get("Back_Left_Motor");
        backrightMotor = hardwareMap.dcMotor.get("Back_Right_Motor");

        waitForStart();
        runtime.reset();

        driveRight(1.3);
        rotateRight(1.2);
        driveForward(0.4);

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

    public void stopDriving(double seconds) {

        frontleftMotor.setPower(0.0);
        frontrightMotor.setPower(0.0);
        backleftMotor.setPower(0.0);
        backrightMotor.setPower(0.0);

        rest(seconds);
    }

    public void driveForward(double seconds) {

        frontleftMotor.setPower(-power);
        frontrightMotor.setPower(power);
        backleftMotor.setPower(-power);
        backrightMotor.setPower(-power);

        rest(seconds);
        stopDriving(0);
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


    public void rest(double seconds) {
        sleep((long) (seconds * 1000));
    }
}

