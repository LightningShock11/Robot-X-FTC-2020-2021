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
public class StoneArm extends XModule {

    DcMotor yMotor;
    DcMotor xMotor;
    boolean up;
    boolean out;
    int xMax = 1345;
    int yMax = 3100;

    public StoneArm(OpMode op) {
        super(op);
    }

    public void init() {
        //initialize motor
        yMotor = opMode.hardwareMap.dcMotor.get("yMotor");
        yMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        yMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        yMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        yMotor.getCurrentPosition();
        yMotor.setTargetPosition(0);
    }
}