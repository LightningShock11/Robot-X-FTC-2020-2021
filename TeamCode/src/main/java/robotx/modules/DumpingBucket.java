package robotx.modules;

import android.widget.RemoteViews;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class DumpingBucket extends XModule{

    DcMotor dumpingMotor;
    boolean dumping;

    public DumpingBucket(OpMode op){super(op);}

    public void init(){
        dumpingMotor = opMode.hardwareMap.dcMotor.get("dumpingMotor");
        dumpingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpingMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dumpingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dumpingMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        dumpingMotor.getCurrentPosition();
        dumpingMotor.setTargetPosition(0);
    }

    public void autoDump(){
        //Toggle method that allows the motor to move back and forth between two positions
        if (dumping){
            dumpingMotor.setTargetPosition(0);
            dumpingMotor.setPower(0.5);
            dumping = false;
        }
        else{
            dumpingMotor.setTargetPosition(-150);
            dumpingMotor.setPower(-0.5);
            dumping = true;
        }
    }

    public void loop(){
        opMode.telemetry.addData("Current Motor Position:", dumpingMotor.getCurrentPosition());
        opMode.telemetry.addData("Target Motor Position:", dumpingMotor.getTargetPosition());

        if (dumpingMotor.getTargetPosition() == -150 && dumpingMotor.getCurrentPosition() <= dumpingMotor.getTargetPosition()){
            dumpingMotor.setPower(0.0);
        }
        else if(dumpingMotor.getTargetPosition() == 0 && dumpingMotor.getCurrentPosition() >= dumpingMotor.getTargetPosition()){
            dumpingMotor.setPower(0.0);
        }

        if (xGamepad2().a.wasPressed()){
            autoDump();
        }

        if (xGamepad2().dpad_right.isDown()){
            dumpingMotor.setPower(0.5);
        }
        else if(xGamepad2().dpad_left.isDown()){
            dumpingMotor.setPower(-0.5);
        }
        else {
            dumpingMotor.setPower(0.0);
        }
    }

    public void stop(){
        dumpingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
