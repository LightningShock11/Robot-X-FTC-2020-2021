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

    DcMotor stoneArm;


    public StoneArm(OpMode op) {
        super(op);
    }

    public void init() {
        //initialize motor
        stoneArm = opMode.hardwareMap.dcMotor.get("stoneArm");
        stoneArm.setDirection(DcMotorSimple.Direction.REVERSE);
        stoneArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        stoneArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        stoneArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        stoneArm.getCurrentPosition();
        stoneArm.setTargetPosition(0);
    }

    public void loop () {

        if(xGamepad2().a.wasPressed()) {
            if(stoneArm.getCurrentPosition() <= -10 || stoneArm.getCurrentPosition() >= 10) {
                stoneArm.setTargetPosition(0);
                stoneArm.setPower(-0.5);
                if(stoneArm.getCurrentPosition() <= 10 && stoneArm.getCurrentPosition() >= -10){
                    stoneArm.setPower(0);
                }
            } else if(stoneArm.getCurrentPosition() <= 10 && stoneArm.getCurrentPosition() >= -10) {
                stoneArm.setTargetPosition(180);
                stoneArm.setPower(0.5);
                if(stoneArm.getCurrentPosition() <= 190 && stoneArm.getCurrentPosition() >= 170) {
                    stoneArm.setPower(0);
                }

            }
        }


        }
    }
