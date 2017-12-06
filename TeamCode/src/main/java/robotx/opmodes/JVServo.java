package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.*;

/**
 * Created by Nicholas on 11/6/16.
 * Use to test the servo at any position.
 * Hold down the back bumpers to change the unit,
 * and press the up and down buttons on the D-Pad to increment/decrement.
 */
@TeleOp(name = "JVServo", group = "Debug")
public class JVServo extends OpMode {

    Servo rightServo;
    Servo leftServo;

    double servoPosition;

    PressHandler gamepad2_dpad_up;
    PressHandler gamepad2_dpad_down;
    PressHandler gamepad2_a;

    boolean scaleEnabled = false;

    @Override
    public void init() {
        rightServo = hardwareMap.servo.get("rightServo");

        servoPosition = 0.0;

        gamepad2_dpad_up = new PressHandler();
        gamepad2_dpad_down = new PressHandler();
        gamepad2_a = new PressHandler();
    }

    @Override
    public void start() {
        rightServo.setPosition(servoPosition);
        leftServo.setPosition(servoPosition);
    }

    @Override
    public void loop() {
        gamepad2_dpad_up.update(gamepad1.dpad_up);
        gamepad2_dpad_down.update(gamepad1.dpad_down);
        gamepad2_a.update(gamepad1.a);

        if (gamepad2_a.onPress()) {
            if (scaleEnabled) {
                rightServo.scaleRange(0.0, 1.0);
                leftServo.scaleRange(0.0, 1.0);
                scaleEnabled = false;

            } else {
                rightServo.scaleRange(0.00, 0.85);
                leftServo.scaleRange(0.1, 0.9);
                scaleEnabled = true;
            }
        }

        double unit = 0.1;
        if (gamepad2.left_bumper) {
            unit = 0.01;
        }
        if (gamepad2.right_bumper) {
            unit = 0.001;
        }
        if (gamepad2.left_bumper && gamepad1.right_bumper) {
            unit = 0.0001;
        }
        telemetry.addData("Unit", unit);

        if (gamepad2_dpad_up.onPress()) {
            servoPosition += unit;
        }
        if (gamepad2_dpad_down.onPress()) {
            servoPosition -= unit;
        }

        if (servoPosition > 1.0) {
            servoPosition = 1.0;
        }
        if (servoPosition < 0.0) {
            servoPosition = 0.0;
        }

        rightServo.setPosition(servoPosition);
        leftServo.setPosition(servoPosition);

        telemetry.addData("Scale Enabled?", scaleEnabled);
        telemetry.addData("Servo Position", servoPosition);
    }

    @Override
    public void stop() {

    }

}