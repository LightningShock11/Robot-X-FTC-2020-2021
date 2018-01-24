package robotx.modules;

import robotx.libraries.OmniAutonomousMovement;
import robotx.libraries.XModule;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ben Sabo on 10/20/2017.
 */

public class JewelColor extends XModule {

    public enum GemStatus {
        RED_BLUE,
        BLUE_RED,
        ERROR;

        public static GemStatus BLUE_ON_LEFT() {
            return BLUE_RED;
        }
        public static GemStatus BLUE_ON_RIGHT() {
            return RED_BLUE;
        }
        public static GemStatus RED_ON_LEFT() {
            return RED_BLUE;
        }
        public static GemStatus RED_ON_RIGHT() {
            return BLUE_RED;
        }

        @Override
        public String toString() {
            if (this == GemStatus.RED_BLUE) {
                return "RED_BLUE";
            } else if (this == GemStatus.BLUE_RED) {
                return "BLUE_RED";
            } else { // ERROR
                return "ERROR";
            }

        }
    }

    public OmniAutonomousMovement autonomousMovement;

    boolean armIsUp;

    ColorSensor armColor;
    Servo armServo;

    public JewelColor(OpMode Op) {super (Op);}

    public void init(){
        armColor = opMode.hardwareMap.colorSensor.get("armColor");
        armColor.setI2cAddress(I2cAddr.create7bit(0x39)); // All REV color sensors use this address
        armColor.enableLed(true);
        opMode.telemetry.addLine("Color sensor is online");
        armServo = opMode.hardwareMap.servo.get("armServo");
        raiseArm();
    }

    private void sleep(long milliseconds) {
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }

    public GemStatus colorEval(){
        opMode.telemetry.addData("Red", armColor.red());
        opMode.telemetry.addData("Blue", armColor.blue());
        if (armColor.blue() > armColor.red()) {
            return GemStatus.BLUE_ON_RIGHT();
        } else if (armColor.blue() < armColor.red()) {
            return GemStatus.RED_ON_RIGHT();
        }

        return GemStatus.ERROR;
    }

    public void knockOffRightGem() {
        lowerArm();
        sleep(500);
        autonomousMovement.pointTurnLeft(15);
        sleep(500);
        raiseArm();
        sleep(500);
        autonomousMovement.pointTurnRight(15);
    }
    public void knockOffLeftGem() {
        lowerArm();
        sleep(500);
        autonomousMovement.pointTurnRight(15);
        sleep(500);
        raiseArm();
        sleep(500);
        autonomousMovement.pointTurnLeft(15);
    }

    public void knockOffRedGem() {
        GemStatus gemStatus = colorEval();
        if (gemStatus == GemStatus.RED_ON_LEFT()) {
            knockOffLeftGem();
        } else if (gemStatus == GemStatus.RED_ON_RIGHT()) {
            knockOffRightGem();
        }
    }
    public void knockOffBlueGem() {
        GemStatus gemStatus = colorEval();
        if (gemStatus == GemStatus.BLUE_ON_LEFT()) {
            knockOffLeftGem();
        } else if (gemStatus == GemStatus.BLUE_ON_RIGHT()) {
            knockOffRightGem();
        }
    }

    public void toggleArm(){
        if (armIsUp) {
            lowerArm();
        } else {
            raiseArm();
        }
    }
    public void lowerArm() {
        armServo.setPosition(1.0);
        armIsUp = false;
    }
    public void raiseArm() {
        armServo.setPosition(0.5);
        armIsUp = true;
    }
    public void raiseArmAuton(){
        armServo.setPosition(0.0);
    }


    public void start(){
        raiseArm();
    }

    public void loop(){
        opMode.telemetry.addData("Color", colorEval().toString());
        raiseArm();
        if (xGamepad2().dpad_right.isDown() && xGamepad1().dpad_right.isDown()){
            lowerArm();
        }
    }
    public void stop(){
        raiseArm();
    }
}
