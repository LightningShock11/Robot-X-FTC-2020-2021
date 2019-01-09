package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class DumpingBucket extends XModule{

    Servo dumpingServo;

    public DumpingBucket(OpMode op){super(op);}

    public void init(){
        dumpingServo = opMode.hardwareMap.servo.get("dumpingMotor");
        dumpingServo.setPosition(0.0);
    }
    private void sleep(long milliseconds) {
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }
    public void autoDump(){
        //dumpingServo.setPosition();
        sleep(1500);
        dumpingServo.setPosition(0.0);
    }

    public void loop(){
        if (xGamepad2().a.isDown()){
            //dumpingServo.setPosition();
        }
        else{
            dumpingServo.setPosition(0.0);
        }
    }
}
