package robotx.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.controls.*;
import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Robot X Team 4969 on 11/22/16.
 */
@TeleOp(name = "TileRunnerBetaOp", group = "Competition")
@Disabled
public class TileRunnerBetaOp extends XOpMode {

	BasicDriveSystem tileRunnerDrive;
	ButtonPusher buttonPusher;
	FlipperLauncher flipperLauncher;
	SweeperSystem sweeperSystem;
	LiftSystem liftSystem;
	LiftOneMotor liftOneMotor;


	@Override
	public void initModules() {
		super.initModules();

		tileRunnerDrive = new TwoMotorDrive(this);
		tileRunnerDrive.controls = new BasicTankDriveControls();
		activeModules.add(tileRunnerDrive);

		buttonPusher = new ButtonPusher(this);
		activeModules.add(buttonPusher);

		flipperLauncher = new FlipperLauncher(this);
		activeModules.add(flipperLauncher);

		sweeperSystem = new SweeperSystem(this);
		activeModules.add(sweeperSystem);

		liftSystem = new LiftSystem(this);
		activeModules.add(liftSystem);

		liftOneMotor = new LiftOneMotor(this);
		activeModules.add(liftOneMotor);

	}

	@Override
	public void init() {
		super.init();
		// Add custom code here.
	}

}