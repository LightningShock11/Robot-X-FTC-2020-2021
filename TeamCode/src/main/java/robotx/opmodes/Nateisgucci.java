package robotx.opmodes;

/**
 * Created by William Braun on 1/31/2018.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Nateisgucci {

        private ElapsedTime runtime = new ElapsedTime();

        DcMotor frontleftMotor;
        DcMotor frontrightMotor;
        DcMotor backleftMotor;
        DcMotor backrightMotor;
        Servo leftServo;
        Servo rightServo;
        DcMotor liftMotor;

    double power = 0.5;
    double power2 = 1.0;

    waitForStart();
    runtime.reset();

    public void runOpMode() {
        driveForward(seconds 0.5)
        stopDriving(seconds 0.2)
       driveForward(seconds 1);
       driveRight(seconds .5)







}

