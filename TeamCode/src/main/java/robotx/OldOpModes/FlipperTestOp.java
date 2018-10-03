package robotx.OldOpModes;

import com.qualcomm.robotcore.eventloop.opmode.*;

import robotx.OldModules.FlipperLauncher;
import robotx.libraries.*;

/**
 * Created by Ben on 12/28/2016.
 */
@TeleOp(name = "FlipperTestOp", group = "Competition")
@Disabled
public class FlipperTestOp extends XOpMode {

	FlipperLauncher flipperLauncher;

	@Override
	public void initModules() {
		super.initModules();

		FlipperLauncher flipperLauncher = new FlipperLauncher(this);
		activeModules.add(flipperLauncher);
	}

	@Override
	public void init() {
		super.init();
		// Add custom code here.
	}

}
