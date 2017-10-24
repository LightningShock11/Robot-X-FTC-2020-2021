package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Nicholas on 11/20/16.
 */
@Autonomous(name = "OneBeaconBlueOp", group = "Autonomous")
@Disabled
public class OneBeaconBlueOp extends LinearOpMode {

	ElapsedTime timer = new ElapsedTime();

	DriveSystem tileRunnerDrive;
	ButtonPusher buttonPusher;

	ColorSensor floorSensor;
	XOpticalDistanceSensor opticalDistanceSensor;

	public void runOpMode() {
		// Do initialization.
		telemetry.addData("Stage", "Init");
		this.updateTelemetry(telemetry);
		tileRunnerDrive = new FourWheelDrive(this);
		tileRunnerDrive.init();

		buttonPusher = new ButtonPusher(this);
		buttonPusher.init();

		floorSensor = hardwareMap.colorSensor.get("floorColorSensor");
		floorSensor.setI2cAddress(I2cAddr.create8bit(0x5c));
		floorSensor.enableLed(true);

		opticalDistanceSensor = new XOpticalDistanceSensor(this, "BP_distanceSensor");

		waitForStart(); // Wait for start to be pressed.
		telemetry.addData("Stage", "Start");
		this.updateTelemetry(telemetry);

		tileRunnerDrive.start();
		buttonPusher.start();

		// 1. Drive away from wall.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "1. Drive away from wall");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setBothPower(0.4);
			timer.reset();
			while (timer.seconds() <= 0.6) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 2. Turn right towards beacon.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "2. Turn right towards beacon.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setLeftPower(0.4);
			timer.reset();
			while (timer.seconds() <= 0.7) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 3. Drive quickly towards beacon.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "3. Drive quickly towards beacon.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setBothPower(1.0);
			timer.reset();
			while (timer.seconds() <= 1.0) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 4. Drive slowly, searching for white line.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "4. Drive slowly, searching for white line.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setBothPower(0.15);
			while (floorSensor.green() <= 7) {
				telemetry.addData("Stage", "4. Drive slowly, searching for white line.");
				telemetry.addData("Floor Sensor Green", floorSensor.green());
				this.updateTelemetry(telemetry);
			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 5. Make a slight turn.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "5. Make a slight turn.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setLeftPower(0.2);
			timer.reset();
			while (timer.seconds() <= 0.4) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 6. Follow line until beacon is close.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "6. Follow line until beacon is close.");
			this.updateTelemetry(telemetry);
			while (opticalDistanceSensor.distanceInCentimeters() >= 0.6) {
				telemetry.addData("Stage", "5. Follow line until beacon is close.");
				telemetry.addData("Floor Sensor Green", floorSensor.green());
				telemetry.addData("Distance Sensor (cm)", opticalDistanceSensor.distanceInCentimeters());
				this.updateTelemetry(telemetry);
				if (floorSensor.green() < 7) { // 0 <= x < 7
					tileRunnerDrive.setRightPower(0.0);
					tileRunnerDrive.setLeftPower(0.2);
				} else if (floorSensor.green() < 9) { // 7 <= x < 9
					tileRunnerDrive.setBothPower(0.2);
				} else { // 9 <= x <= 255
					tileRunnerDrive.setRightPower(0.2);
					tileRunnerDrive.setLeftPower(0.0);
				}
			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 7. Make a slight turn.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "7. Make a slight turn.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setLeftPower(0.2);
			timer.reset();
			while (timer.seconds() <= 0.4) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 8. Press blue beacon button.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "8. Press blue beacon button.");
			this.updateTelemetry(telemetry);
			buttonPusher.pressBlueButton();
		}

		// 9. Wait.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "9. Wait.");
			this.updateTelemetry(telemetry);
			timer.reset();
			while (timer.seconds() <= 0.75) {

			}
		}

		// 10. Retract beacon arms.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "10. Retract beacon arms");
			this.updateTelemetry(telemetry);
			buttonPusher.retractBothArms();
		}

		// 11. Back up towards cap ball.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "11. Back up towards cap ball.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setBothPower(-1.0);
			timer.reset();
			while (timer.seconds() <= 1.2) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 12. Turn backwards towards cap ball.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "12. Turn backwards towards cap ball.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setLeftPower(-0.4);
			tileRunnerDrive.setRightPower(-0.1);
			timer.reset();
			while (timer.seconds() <= 1.0) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 13. Drive away from ball.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "13. Drive away from ball.");
			this.updateTelemetry(telemetry);
			tileRunnerDrive.setBothPower(1.0);
			timer.reset();
			while (timer.seconds() <= 0.7) {

			}
			tileRunnerDrive.setBothPower(0.0);
		}

		// 14. Stop and wait.
		if (opModeIsActive()) {
			telemetry.addData("Stage", "14. Stop and wait.");
			this.updateTelemetry(telemetry);
		}
	}

}
