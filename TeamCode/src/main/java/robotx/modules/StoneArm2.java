package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

/**
 * Created by John David Sniegocki and Sam McDowell on 10.28.2019
 */
public class
StoneArm2 extends XModule {

    DcMotor stoneArm;
    double armPower;


    public StoneArm2(OpMode op) {
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
        double arm = stoneArm.getCurrentPosition() - 700;
        double armPower = -arm * .001;
        if(xGamepad2().a.wasPressed()) {
            if (stoneArm.getCurrentPosition() <= 50 && stoneArm.getCurrentPosition() >= -50) {
                stoneArm.setTargetPosition(1000);
                stoneArm.setPower(armPower);
            } else {
                stoneArm.setTargetPosition(0);
                stoneArm.setPower(0.8)
            }

        }
        if(stoneArm.getCurrentPosition() >= 475 && stoneArm.getCurrentPosition() <= 525) {
            stoneArm.setPower(-0.1);
        }

        }
    }