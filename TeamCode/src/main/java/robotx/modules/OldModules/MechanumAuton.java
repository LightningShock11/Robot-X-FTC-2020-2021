package robotx.modules.OldModules;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.*;

/**
 * Created by Robot-X Team Member on 11/27/2017.
 */

public class MechanumAuton extends OmniAutonomousSystem {
    ModernRoboticsI2cGyro gyroSensor;
    // Be sure to assign these before use.
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backRightMotor;
    public DcMotor backLeftMotor;

    public MechanumAuton(OpMode op) {
        super(op);
    }

    public void init() {
        gyroSensor = (ModernRoboticsI2cGyro)opMode.hardwareMap.gyroSensor.get("gyroSensor");
    }

    // Return the current heading angle of the robot.
    // This should not loop around at 360, and values should increase past 360.
    public int getHeadingAngle() {
        return gyroSensor.getIntegratedZValue();
    }

    // Return the current distance the left side has traveled, in encoder ticks.
    public int getXTicks() {
        return backRightMotor.getCurrentPosition();
    }
    // Return the current distance the right side has traveled, in encoder ticks.
    public int getYTicks() {
        return -backLeftMotor.getCurrentPosition();
    }

    // Input encoder ticks and return centimeters.
    public double ticksToCentimeters(int encoderTicks) {
        return encoderTicks * ((42.25/3600.0)*2.54)*(100.0/116.5);
    }

    public void calibrateGyro() {
        gyroSensor.calibrate();
    }

}
