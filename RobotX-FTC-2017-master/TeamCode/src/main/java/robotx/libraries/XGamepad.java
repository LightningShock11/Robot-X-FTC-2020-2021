package robotx.libraries;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Nicholas on 11/21/16.
 */
public class XGamepad {

	public float left_stick_x;
	public float left_stick_y;
	public float right_stick_x;
	public float right_stick_y;
	public float left_trigger;
	public float right_trigger;

	public Button dpad_up;
	public Button dpad_down;
	public Button dpad_left;
	public Button dpad_right;
	public Button a;
	public Button b;
	public Button x;
	public Button y;
	public Button guide;
	public Button start;
	public Button back;
	public Button left_bumper;
	public Button right_bumper;
	public Button left_stick_button;
	public Button right_stick_button;

	public XGamepad(Gamepad gamepad) {
		dpad_up = new Button();
		dpad_down = new Button();
		dpad_left = new Button();
		dpad_right = new Button();
		a = new Button();
		b = new Button();
		x = new Button();
		y = new Button();
		guide = new Button();
		start = new Button();
		back = new Button();
		left_bumper = new Button();
		right_bumper = new Button();
		left_stick_button = new Button();
		right_stick_button = new Button();

		update(gamepad);
	}

	public void update(Gamepad gamepad) {
		left_stick_x = gamepad.left_stick_x;
		left_stick_y = gamepad.left_stick_y;
		right_stick_x = gamepad.right_stick_x;
		right_stick_y = gamepad.right_stick_y;
		left_trigger = gamepad.left_trigger;
		right_trigger = gamepad.right_trigger;

		dpad_up.update(gamepad.dpad_up);
		dpad_down.update(gamepad.dpad_down);
		dpad_left.update(gamepad.dpad_left);
		dpad_right.update(gamepad.dpad_right);
		a.update(gamepad.a);
		b.update(gamepad.b);
		x.update(gamepad.x);
		y.update(gamepad.y);
		guide.update(gamepad.guide);
		start.update(gamepad.start);
		back.update(gamepad.back);
		left_bumper.update(gamepad.left_bumper);
		right_bumper.update(gamepad.right_bumper);
		left_stick_button.update(gamepad.left_stick_button);
		right_stick_button.update(gamepad.right_stick_button);
	}

}
