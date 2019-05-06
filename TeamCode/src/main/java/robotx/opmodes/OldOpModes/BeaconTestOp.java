package robotx.opmodes.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import robotx.modules.OldModules.ButtonPusher;
import robotx.libraries.*;

/**
 * Created by Robot-X Team 4969 on 11/15/2016.
 */
@TeleOp(name = "BeaconTestOp", group = "Debug")
@Disabled
public class BeaconTestOp extends XOpMode {

	ButtonPusher buttonPusher;
	OpticalDistanceSensor distanceSensor;

	@Override
	public void initModules() {
		super.initModules();

		buttonPusher = new ButtonPusher(this);
		inactiveModules.add(buttonPusher);

		distanceSensor = hardwareMap.opticalDistanceSensor.get("BP_distanceSensor");
	}

	@Override
	public void init() {
		super.init();
		// Add custom code here.
	}

	@Override
	public void loop() {
		super.loop();

		ButtonPusher.BeaconStatus status = buttonPusher.getBeaconStatus();
		telemetry.addData("Current Status", status.toString());

		telemetry.addData("lightDetected", distanceSensor.getLightDetected());
		telemetry.addData("rawLightDetected", distanceSensor.getRawLightDetected());

		if (gamepad1.b) { //RED
			buttonPusher.pressRedButton();
		}
		if (gamepad1.x) { //BLUE
			buttonPusher.pressBlueButton();
		}
		if (gamepad1.a) {
			buttonPusher.retractBothArms();
		}

		if (gamepad1.dpad_left) {
			buttonPusher.pressLeftButton();
		}
		if (gamepad1.dpad_right) {
			buttonPusher.pressRightButton();
		}
	}

}
