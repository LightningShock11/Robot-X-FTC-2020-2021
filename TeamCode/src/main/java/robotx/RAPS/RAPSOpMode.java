package robotx.RAPS;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Kush Dalal on 11/15/19.
 */
@TeleOp(name = "RAPSOpMode", group = "Default")
public class RAPSOpMode extends OpMode {

	@Override
	public void init() {

	}

	@Override
	public void init_loop() {

	}

	@Override
	public void start() {

	}

	@Override
	public void loop() {
		RAPSMovement.goToPos(50, 50, 0.3);

	}

	@Override
	public void stop() {

	}

}
