package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import robotx.libraries.XModule;

public class StoneLift extends XModule {
    public StoneLift(OpMode op){super(op);}

    DcMotor liftMotor;
    TouchSensor endStop;
    Servo capServo;
    boolean capped = false;
    double inPos;
    double cappedPos;

    public void init(){
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        endStop = opMode.hardwareMap.touchSensor.get("endStop");
        capServo = opMode.hardwareMap.servo.get("capServo");
    }

    public void toggleCap(){
        if (capped){
            capServo.setPosition(inPos);
            capped = false;
        }
        else {
            capServo.setPosition(cappedPos);
            capped = true;
        }
    }

    public void loop() {
        if (!endStop.isPressed() || xGamepad2().left_stick_y > 0) {
            liftMotor.setPower(xGamepad2().left_stick_y);
        } else {
            liftMotor.setPower(0.0);
        }
        if (xGamepad2().left_stick_y < 0 && liftMotor.getCurrentPosition() <= 50) {
            liftMotor.setPower(0.0);
        }
        if (xGamepad2().x.wasPressed()){
            toggleCap();
        }
    }
}
