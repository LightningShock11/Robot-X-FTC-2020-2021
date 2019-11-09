package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

/**
 * Created by John David Sniegocki and Sam McDowell on 10.28.2019
 */
public class StoneArm2 extends XModule {

    DcMotor stoneArm;
    double armPower;
    boolean armOut = false;


    public StoneArm2(OpMode op) {
        super(op);
    }

    public void init() {
        //initialize motor
        stoneArm = opMode.hardwareMap.dcMotor.get("stoneArm");
        stoneArm.setDirection(DcMotorSimple.Direction.REVERSE);
        stoneArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void moveArm(){
        if (armOut) {
            stoneArm.setTargetPosition(0);
            armOut = false;
        }
        else{
            stoneArm.setTargetPosition(1000);
            armOut = true;
        }
    }

    public void loop () {
        opMode.telemetry.addData("Current position:", stoneArm.getCurrentPosition());
        opMode.telemetry.addData("Arm power", armPower);
        double arm = stoneArm.getCurrentPosition();

        if (stoneArm.getTargetPosition() == 0){
            armPower = -(arm-600) * .001;
        }
        else if (stoneArm.getTargetPosition() == 1000){
            armPower = -(arm-600) * .001;
        }

        stoneArm.setPower(armPower);

        if(xGamepad2().a.wasPressed()) {
            moveArm();
        }

        if (stoneArm.getTargetPosition() == 1000 && stoneArm.getCurrentPosition() >= 800){
            stoneArm.setPower(0.0);
        }
        if (stoneArm.getTargetPosition() == 0 && stoneArm.getCurrentPosition() <= 200){
            stoneArm.setPower(0.0);
        }
    }
}