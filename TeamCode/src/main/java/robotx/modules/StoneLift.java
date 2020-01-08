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
    DcMotor encoder;
    TouchSensor endStop;
    //Servo capServo;
    public double motorPower = -0.15;

    boolean capped = false;
    double inPos;
    double cappedPos;

    public void init(){
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        encoder = opMode.hardwareMap.dcMotor.get("flywheelRight");
        //endStop = opMode.hardwareMap.touchSensor.get("endStop");
        //capServo = opMode.hardwareMap.servo.get("capServo");
    }

    /*public void toggleCap(){
        if (capped){
            capServo.setPosition(inPos);
            capped = false;
        }
        else {
            capServo.setPosition(cappedPos);
            capped = true;
        }
    }*/

        public void loop() {

            opMode.telemetry.addData("Motor Power: ", liftMotor.getPower() + xGamepad2().left_stick_y + " Encoder Value: " + encoder.getCurrentPosition());
            if(encoder.getCurrentPosition() <= -150 && xGamepad2().left_stick_y == 0){
                liftMotor.setPower(motorPower);
            }else{
                liftMotor.setPower(xGamepad2().left_stick_y);
            }
        }
        /*if (xGamepad2().x.wasPressed()){
            toggleCap();
        }*/
    }

