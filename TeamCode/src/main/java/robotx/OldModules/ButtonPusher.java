package robotx.OldModules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.*;

/**
 * Created by Nicholas on 11/6/16.
 */
public class ButtonPusher extends XModule {

	public enum BeaconStatus {
		RED_BLUE,
		BLUE_RED;

		public static BeaconStatus BLUE_ON_LEFT() {
			return BLUE_RED;
		}
		public static BeaconStatus BLUE_ON_RIGHT() {
			return RED_BLUE;
		}
		public static BeaconStatus RED_ON_LEFT() {
			return RED_BLUE;
		}
		public static BeaconStatus RED_ON_RIGHT() {
			return BLUE_RED;
		}

		@Override
		public String toString() {
			if (this == BeaconStatus.RED_BLUE) {
				return "RED_BLUE";
			} else { // BLUE_RED
				return "BLUE_RED";
			}

		}
	}
	public enum ArmStatus {
		EXTENDED,
		RETRACTED
	}

	ColorSensor leftColorSensor;
	ColorSensor rightColorSensor;

	Servo leftServo;
	Servo rightServo;

	ArmStatus leftArmStatus;
	ArmStatus rightArmStatus;

	public ButtonPusher(OpMode op) {
		super(op);
	}

	public void init() {
		leftColorSensor = opMode.hardwareMap.colorSensor.get("BP_leftColorSensor");
		leftColorSensor.setI2cAddress(I2cAddr.create8bit(0x3c));
		leftColorSensor.enableLed(false);
		rightColorSensor = opMode.hardwareMap.colorSensor.get("BP_rightColorSensor");
		rightColorSensor.setI2cAddress(I2cAddr.create8bit(0x4c));
		rightColorSensor.enableLed(false);

		leftServo = opMode.hardwareMap.servo.get("BP_leftServo");
		leftServo.scaleRange(0.02, 0.98);
		rightServo = opMode.hardwareMap.servo.get("BP_rightServo");
		rightServo.scaleRange(0.02, 0.98);
		rightServo.setDirection(Servo.Direction.REVERSE);

		leftArmStatus = ArmStatus.RETRACTED;
		rightArmStatus = ArmStatus.RETRACTED;
	}

	// Public facing hardware methods

	public void pressLeftButton() { // TODO Tweak servo position values.
		leftServo.setPosition(0.55);
		leftArmStatus = ArmStatus.EXTENDED;
	}
	public void pressRightButton() {
		rightServo.setPosition(0.55);
		rightArmStatus = ArmStatus.EXTENDED;
	}

	public void retractLeftArm() {
		leftServo.setPosition(0.00);
		leftArmStatus = ArmStatus.RETRACTED;
	}
	public void retractRightArm() {
		rightServo.setPosition(0.00);
		rightArmStatus = ArmStatus.RETRACTED;
	}

	public void toggleLeftArm() {
		if (leftArmStatus == ArmStatus.RETRACTED) {
			pressLeftButton();
		} else {
			retractLeftArm();
		}
	}
	public void toggleRightArm() {
		if (rightArmStatus == ArmStatus.RETRACTED) {
			pressRightButton();
		} else {
			retractRightArm();
		}
	}

	public void retractBothArms() {
		retractLeftArm();
		retractRightArm();
	}

	public BeaconStatus getBeaconStatus() {
		BeaconStatus beaconStatus;
		if (leftColorSensor.blue() > rightColorSensor.blue() && leftColorSensor.red() < rightColorSensor.red()) {
			beaconStatus = BeaconStatus.BLUE_RED;
		} else if (leftColorSensor.blue() < rightColorSensor.blue() && leftColorSensor.red() > rightColorSensor.red()) {
			beaconStatus = BeaconStatus.RED_BLUE;

		} else { // Fall back to single color sensor detection.

			opMode.telemetry.addLine("Difficulty detecting beacon colors.");
			if (leftColorSensor.blue() > leftColorSensor.red()) {
				beaconStatus = BeaconStatus.BLUE_ON_LEFT();
			} else if (leftColorSensor.blue() < leftColorSensor.red()) {
				beaconStatus = BeaconStatus.RED_ON_LEFT();
			} else {

				if (rightColorSensor.blue() > rightColorSensor.red()) {
					beaconStatus = BeaconStatus.BLUE_ON_RIGHT();
				} else if (rightColorSensor.blue() < rightColorSensor.red()) {
					beaconStatus = BeaconStatus.RED_ON_RIGHT();
				} else {
					opMode.telemetry.addLine("Sensors are probably disconnected.  Guessing red on left.");
					beaconStatus = BeaconStatus.RED_ON_LEFT();
				}
			}
		}

		return beaconStatus;
	}

	public void pressRedButton() {
		BeaconStatus beaconStatus = getBeaconStatus();
		if (beaconStatus==BeaconStatus.RED_ON_LEFT()) {
			pressLeftButton();
		} else if (beaconStatus==BeaconStatus.RED_ON_RIGHT()) {
			pressRightButton();
		}
	}
	public void pressBlueButton() {
		BeaconStatus beaconStatus = getBeaconStatus();
		if (beaconStatus==BeaconStatus.BLUE_ON_LEFT()) {
			pressLeftButton();
		} else if (beaconStatus==BeaconStatus.BLUE_ON_RIGHT()) {
			pressRightButton();
		}
	}

	// TeleOp code

	// Called only for active XModules.
	public void start() {
		retractBothArms();
	}

	// Called only for active XModules.
	public void loop() {
		if (xGamepad2().dpad_left.wasPressed()) {
			toggleLeftArm();
		}
		if (xGamepad2().dpad_right.wasPressed()) {
			toggleRightArm();
		}

	}

	// Called only for active XModules.
	public void stop() {
		retractBothArms();
	}

}
