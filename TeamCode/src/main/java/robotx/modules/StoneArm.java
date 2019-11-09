package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

/**
 * Created by John David Sniegocki and Sam McDowell on 10.28.2019
 */
public class
StoneArm extends XModule {

    public DcMotor stoneArm;
    double armPower;


    public StoneArm(OpMode op) {
        super(op);
    }

    public void init() {
        //initialize motor
        stoneArm = opMode.hardwareMap.dcMotor.get("stoneArm");
        stoneArm.setDirection(DcMotorSimple.Direction.REVERSE);
        stoneArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop () {
        opMode.telemetry.addData("Current position:", stoneArm.getCurrentPosition());
        opMode.telemetry.addData("Arm power", armPower);

        stoneArm.setPower((xGamepad2().right_trigger - xGamepad2().left_trigger)/2);
        }
    }