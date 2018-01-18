package robotx.libraries;

/**
 * Created by Robot-X Team Member on 11/21/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Robot-X Team Member (Nicholas) on 11/21/2017.
 * Represents any drive system that can move at any angle, and rotate.
 * Can be used for Mechanum, omniwheels.
 * Handles different control schemes, too.
 */
public abstract class OmniDriveSystem extends XModule {

    // All powers range from -1.0 to 1.0
    private double xPower = 0.0; // Left-right axis, right is positive
    private double yPower = 0.0; // Up-down axis, up is positive
    private double rotationPower = 0.0; // Rotation power, clockwise is positive

    public boolean controlsEnabled = true;

    public OmniDriveSystem(OpMode op) {
        super(op);
    }

    // To be implemented by subclasses.
    // Takes leftPower and rightPower and assigns them to the motors.
    public abstract void updateMotors();

    // Public facing hardware methods

    public double getXPower() {
        return xPower;
    }
    public void setXPower(double xPower) {
        this.xPower = xPower;
        updateMotors();
    }
    public double getYPower() {
        return yPower;
    }
    public void setYPower(double yPower) {
        this.yPower = yPower;
        updateMotors();
    }
    public double getRotationPower() {
        return rotationPower;
    }
    public void setRotationPower(double rotationPower) {
        this.rotationPower = rotationPower;
        updateMotors();
    }

    public void setAllPower(double power) {
        setXPower(power);
        setYPower(power);
        setRotationPower(power);
    }

    public void brakeAllMotors() {
        setAllPower(0.0);
    }

    // TeleOp code

    public void start() {
        brakeAllMotors();
    }

    public void loop() {
        if (controlsEnabled) {
            double xPow = controlRamp(-xGamepad1().left_stick_x);
            double yPow = controlRamp(-xGamepad1().left_stick_y); // Negate the left stick value because negative is up.
            double rotPow = controlRamp(xGamepad1().right_stick_x);

            setXPower(xPow);
            setYPower(yPow);
            setRotationPower(rotPow);
        }
        opMode.telemetry.addData("X Power", xPower);
        opMode.telemetry.addData("Y Power", yPower);
        opMode.telemetry.addData("Rotation Power", rotationPower);
    }
    private double controlRamp(double input) {
        double output = input;
        double coeff = 1.0;
        if (xGamepad1().left_bumper.isDown() || xGamepad1().right_bumper.isDown()) {
            coeff = coeff / 2.0;
        }
        if (xGamepad1().right_bumper.isDown() && xGamepad1().left_bumper.isDown()) {
            coeff = coeff / 8.0;
        }

        return output*coeff;
    }

    public void stop() {
        brakeAllMotors();
    }

}
