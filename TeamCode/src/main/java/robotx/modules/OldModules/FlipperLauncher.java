package robotx.modules.OldModules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.*;

/**
 * Created by Kush but made entirely by Ben with some help by kush and nic on 12/7/2016.
 * A flipper that will rotate once with the press of a button.
 */
public class FlipperLauncher extends XModule {

    DcMotor flipperMotor;
    Servo flipperServo;

    public FlipperLauncher(OpMode op) {
        super(op);

    }

    public void init() {
        flipperMotor = opMode.hardwareMap.dcMotor.get("flipperMotor");
        flipperMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        flipperServo = opMode.hardwareMap.servo.get("flipperServo");
    }

    // Public facing hardware methods

    // Blocking method used in autonomous.

    // Non-blocking methods used in TeleOp.
    public void flipOnce() {
        flipperMotor.setTargetPosition(flipperMotor.getCurrentPosition()-1680);
        flipperMotor.setPower(-1.0); // setPower sets the max power for use in run to position.
    }
    // For use to tweak the location of the flipper.
    public void adjustPosition(int ticks) {
        // If the adjustment is 0, do nothing.
        if (ticks != 0) {
            flipperMotor.setTargetPosition(flipperMotor.getCurrentPosition() - ticks);
            flipperMotor.setPower(-1.0);
        }
    }

    public void openDoor () {
        flipperServo.setPosition(0.65);
    }
    public void closeDoor () {
        flipperServo.setPosition(0.2);
    }

    // TeleOp code

    // Called only for active XModules.
    public void start() {
        closeDoor();
    }

    // Called only for active XModules.
    public void loop() {
        if (xGamepad2().a.wasPressed()) {
            flipOnce();
        }

        opMode.telemetry.addData("Flipper", "Current: " + flipperMotor.getCurrentPosition() + " Target: " + flipperMotor.getTargetPosition());

        int tickAdjustment = (int)((xGamepad2().right_trigger-xGamepad2().left_trigger)*300.0);
        adjustPosition(tickAdjustment);
        opMode.telemetry.addData("Flipper Adjustment", tickAdjustment);

        if (xGamepad2().b.wasPressed()) {
            openDoor();
        } else if (xGamepad2().b.wasReleased()){
            closeDoor();
        }
    }

    // Called only for active XModules.
    public void stop() {
        flipperMotor.setPower(0.0);
        closeDoor();
    }

}

