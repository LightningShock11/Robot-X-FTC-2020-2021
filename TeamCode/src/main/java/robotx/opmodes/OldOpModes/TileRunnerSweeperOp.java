package robotx.opmodes.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.modules.OldModules.ButtonPusher;
import robotx.modules.OldModules.LineFollower;
import robotx.modules.OldModules.TwoWheelLauncher;
import robotx.controls.BasicTankDriveControls;
import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Nicholas on 11/12/16.
 */
@TeleOp(name = "TileRunnerSweeperOp", group = "Competition")
@Disabled
public class TileRunnerSweeperOp extends XOpMode {

	BasicDriveSystem tileRunnerDrive;
	ButtonPusher buttonPusher;
	TwoWheelLauncher twoWheelLauncher;
	TeleOpTimerDisplay timerDisplay;
	LineFollower lineFollower;
    XOpticalDistanceSensor opticalDistanceSensor;

	@Override
	public void initModules() {
		super.initModules();


		tileRunnerDrive = new TwoMotorDrive(this);
		tileRunnerDrive.controls = new BasicTankDriveControls();
		activeModules.add(tileRunnerDrive);

		buttonPusher = new ButtonPusher(this);
		activeModules.add(buttonPusher);

		twoWheelLauncher = new TwoWheelLauncher(this);
		activeModules.add(twoWheelLauncher);

		timerDisplay = new TeleOpTimerDisplay(this);
		timerDisplay.displayGamePeriod = true;
		activeModules.add(new TeleOpTimerDisplay(this));

		lineFollower = new LineFollower(this, tileRunnerDrive);
		activeModules.add(lineFollower);

        opticalDistanceSensor = new XOpticalDistanceSensor(this, "BP_distanceSensor");
        activeModules.add(opticalDistanceSensor);
	}

	@Override
	public void init() {
		super.init();
		// Add custom code here.
	}

}