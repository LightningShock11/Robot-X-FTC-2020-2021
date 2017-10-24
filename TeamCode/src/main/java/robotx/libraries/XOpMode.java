package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.*;
import java.util.ArrayList;

/**
 * Created by Nicholas on 11/3/16.
 */
public abstract class XOpMode extends OpMode {

	public ArrayList<XModule> activeModules = new ArrayList<XModule>();
	public ArrayList<XModule> inactiveModules = new ArrayList<XModule>(); // Modules that don't use the start() / loop() / stop() methods.

	public XGamepad xGamepad1;
	public XGamepad xGamepad2;

	// Subclasses set up modules here and add them to activeModules.
	public void initModules() {

	}

	@Override
	public void init() {
		xGamepad1 = new XGamepad(gamepad1);
		xGamepad2 = new XGamepad(gamepad2);

		initModules();
		for (XModule module : activeModules) {
			module.init();
		}
		for (XModule module : inactiveModules) {
			module.init();
		}
	}

	@Override
	public void init_loop() {
		xGamepad1.update(gamepad1);
		xGamepad2.update(gamepad2);

		for (XModule module : activeModules) {
			module.init_loop();
		}
		for (XModule module : inactiveModules) {
			module.init_loop();
		}
	}

	@Override
	public void start() {
		for (XModule module : activeModules) {
			module.start();
		}
	}

	@Override
	public void loop() {
		xGamepad1.update(gamepad1);
		xGamepad2.update(gamepad2);

		for (XModule module : activeModules) {
			module.loop();
		}
	}

	@Override
	public void stop() {
		for (XModule module : activeModules) {
			module.stop();
		}
	}

}
