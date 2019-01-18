package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class DumpingBucket extends XModule{

    Servo dumpingServo;
    double dumpPosition = 0.9;
    boolean dumping = true;

    public DumpingBucket(OpMode op){super(op);}

    public void init(){
        dumpingServo = opMode.hardwareMap.servo.get("dumpingServo");
        dumpingServo.setPosition(0.35);
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
        dumpingServo.setPosition(0.45);
    }
    public void toggleBucket(){
        if (dumping){
            dumpingServo.setPosition(0.6);
            dumping = false;
        }
        else{
            dumpingServo.setPosition(dumpPosition);
            dumping = true;
        }
    }

    public void loop(){
        if (xGamepad1().a.wasPressed()){
            toggleBucket();
        }
        if (xGamepad1().y.wasPressed()){
            dumpingServo.setPosition(0.35);
        }
    }
}
