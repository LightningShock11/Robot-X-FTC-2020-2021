package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class DumpingBucket extends XModule{

    Servo dumpingServo;
    double dumpPosition = 0.2;

    public DumpingBucket(OpMode op){super(op);}

    public void init(){
        dumpingServo = opMode.hardwareMap.servo.get("dumpingServo");
        dumpingServo.setPosition(0.4);
    }
    //Sleep is used in autonomous
    private void sleep(long milliseconds) {
        if (opMode instanceof LinearOpMode) {
            ((LinearOpMode) opMode).sleep(milliseconds);
        }
    }
    //Used in autonomous
    public void autoDump(){
        dumpingServo.setPosition(dumpPosition);
        sleep(1500);
        dumpingServo.setPosition(0.4);
    }

    public void loop(){
        if (xGamepad2().a.isDown()){
            dumpingServo.setPosition(dumpPosition);
        }
        else{
            dumpingServo.setPosition(0.4);
        }
    }
}
