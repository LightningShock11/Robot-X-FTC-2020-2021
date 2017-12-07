package robotx.modules;

import robotx.libraries.XModule;

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
        BLUE_RED;

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
            } else { // BLUE_RED
                return "BLUE_RED";
            }

        }
    }

    boolean leftBallIsBlue;
    boolean leftBallIsRed;

    boolean armIsUp;

    ColorSensor armColor;
    Servo armServo;

    public JewelColor(OpMode Op) {super (Op);}

    public void init(){
        armColor = opMode.hardwareMap.colorSensor.get("armColor");
        armColor.setI2cAddress(I2cAddr.create7bit(0x39)); // TODO Figure out if this address it correct.
        armColor.enableLed(true);
        opMode.telemetry.addLine("Color sensor is online");
        armServo = opMode.hardwareMap.servo.get("armServo");

        raiseArm();
        armIsUp = true;

    }
    public GemStatus colorEval(){
        if (armColor.blue() > armColor.red()){
            opMode.telemetry.addLine("Left ball is blue");
            return GemStatus.BLUE_ON_LEFT();
        }
        else if (armColor.blue() < armColor.red()){
            opMode.telemetry.addLine("Left ball is red");

            return GemStatus.RED_ON_LEFT();
        }
        // We should return an error value here.
        return GemStatus.RED_ON_LEFT();
    }
    public void toggleArm(){
        if (armIsUp) {
            lowerArm();
        } else {
            raiseArm();
        }
    }
    public void lowerArm() {
        armServo.setPosition(0.3);
        armIsUp = false;
    }
    public void raiseArm() {
        armServo.setPosition(1.0);
        armIsUp = true;
    }


    public void start(){
        raiseArm();
    }

    public void loop(){
        if (xGamepad1().a.wasPressed()){
            colorEval();
            toggleArm();
        }
    }
    public void stop(){
        armColor.resetDeviceConfigurationForOpMode();
        lowerArm();
    }
}
