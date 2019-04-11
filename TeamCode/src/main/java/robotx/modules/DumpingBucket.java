package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.libraries.XModule;

public class DumpingBucket extends XModule{

    Servo dumpingServo;
    double dumpPosition = 0.93;
    double startPosition = 0.4;
    boolean dumping = true;

    public DumpingBucket(OpMode op){super(op);}

    public void init(){
        dumpingServo = opMode.hardwareMap.servo.get("dumpingServo");
        dumpingServo.setPosition(startPosition);
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
        dumpingServo.setPosition(startPosition);
    }
    public void toggleBucket(){
        if (dumping){
            dumpingServo.setPosition(startPosition);
            dumping = false;
        }
        else{
            dumpingServo.setPosition(dumpPosition);
            dumping = true;
        }
    }

    public void loop(){
        opMode.telemetry.addData("Dump position:", dumpingServo.getPosition());

        if (xGamepad1().a.wasPressed()){
            toggleBucket();
        }

    }
}
