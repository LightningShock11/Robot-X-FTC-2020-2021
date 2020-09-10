package robotx.modules.OldModules;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled
public class WhiteTeam extends OpMode {
    public OpMode opMode;
    DcMotor fl;
    DcMotor elbows;
    DcMotor bl;
    DcMotor br;
    Servo fingers;
    long startTime = 0;

    public void init() {
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        elbows = hardwareMap.dcMotor.get("elbows");
        fingers = hardwareMap.servo.get("fingers");
    }

    public void loop() {
        // The left joystick will move the robot forward/back, the right will move it left/right
        bl.setPower(gamepad1.left_stick_y - gamepad1.right_stick_x);
        br.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x);
        opMode.telemetry.addData("String", elbows.getCurrentPosition());
        if (gamepad2.dpad_down)
        {
            elbows.setPower(-1);
            startTime = System.currentTimeMillis();
        }
        if (gamepad2.dpad_up)
        {
            elbows.setPower(1);
            startTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - startTime > 100)
        {
            elbows.setPower(0);
        }
        fingers.setPosition(1 - gamepad2.left_trigger);
    }
}