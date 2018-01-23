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
@TeleOp(name = "ServoTesterOp", group = "Tests")
public class ServoTesterOp extends OpMode {

	Servo testServo;

	double servoPosition;

	PressHandler gamepad1_dpad_up;
	PressHandler gamepad1_dpad_down;
	PressHandler gamepad1_a;

	boolean scaleEnabled = false;

	@Override
	public void init() {
		testServo = hardwareMap.servo.get("testServo");

		servoPosition = 0.0;

		gamepad1_dpad_up = new PressHandler();
		gamepad1_dpad_down = new PressHandler();
		gamepad1_a = new PressHandler();
	}

	@Override
	public void start() {
		testServo.setPosition(servoPosition);
	}

	@Override
	public void loop() {
		gamepad1_dpad_up.update(gamepad1.dpad_up);
		gamepad1_dpad_down.update(gamepad1.dpad_down);
		gamepad1_a.update(gamepad1.a);

		if (gamepad1_a.onPress()) {
			if (scaleEnabled) {
				testServo.scaleRange(0.0, 1.0);
				scaleEnabled = false;
			} else {
				testServo.scaleRange(0.02, 0.98);
				scaleEnabled = true;
			}
		}

		double unit = 0.1;
		if (gamepad1.left_bumper) {
			unit = 0.01;
		}
		if (gamepad1.right_bumper) {
			unit = 0.001;
		}
		if (gamepad1.left_bumper && gamepad1.right_bumper) {
			unit = 0.0001;
		}
		telemetry.addData("Unit", unit);

		if (gamepad1_dpad_up.onPress()) {
			servoPosition += unit;
		}
		if (gamepad1_dpad_down.onPress()) {
			servoPosition -= unit;
		}

		if (servoPosition > 1.0) {
			servoPosition = 1.0;
		}
		if (servoPosition < 0.0) {
			servoPosition = 0.0;
		}

		testServo.setPosition(servoPosition);

		telemetry.addData("Scale Enabled?", scaleEnabled);
		telemetry.addData("Servo Position", servoPosition);
	}

	@Override
	public void stop() {

	}

}
