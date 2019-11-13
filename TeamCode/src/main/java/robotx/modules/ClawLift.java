package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.security.PublicKey;

import robotx.libraries.XModule;

public class ClawLift extends XModule {
    DcMotor liftMotor;
    int position = 0;
    boolean liftUp = false;

    public ClawLift(OpMode op){super(op);}

    public void init(){
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setTargetPosition(0);
    }

    public void autoLift(){
        if (liftUp){
            liftMotor.setTargetPosition(0);
            liftMotor.setPower(-1.0);
            liftUp = false;
        }
        else {
            liftMotor.setTargetPosition(position);
            liftMotor.setPower(1.0);
            liftUp = true;
        }
    }

    public void loop(){
        opMode.telemetry.addData("Level:", position/250);
        opMode.telemetry.update();

        if (xGamepad2().left_trigger > 0 || xGamepad2().right_trigger >0) {
            liftMotor.setPower((xGamepad2().right_trigger) - (xGamepad2().left_trigger));
        }
        if (xGamepad2().right_bumper.wasPressed()){
            position += 250;
        }
        else if (xGamepad2().left_bumper.wasPressed()){
            position -= 250;
        }
        if (xGamepad2().x.wasPressed()){
            autoLift();
        }
        if (liftMotor.getTargetPosition() == position && liftMotor.getCurrentPosition() >= position){
            liftMotor.setPower(0.0);
            position = 0;
        }
        else if (liftMotor.getTargetPosition() == 0 && liftMotor.getCurrentPosition() <= 25){
            liftMotor.setPower(0.0);
        }
    }
}