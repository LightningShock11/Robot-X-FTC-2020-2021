package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
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
    public DigitalChannel magSwitch;
    public boolean magLimit = true;

    boolean capped = false;
    double inPos;
    double cappedPos;

    public void init(){
        liftMotor = opMode.hardwareMap.dcMotor.get("liftMotor");
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        encoder = opMode.hardwareMap.dcMotor.get("flywheelRight");
        //endStop = opMode.hardwareMap.touchSensor.get("endStop");
        //capServo = opMode.hardwareMap.servo.get("capServo");
        magSwitch = opMode.hardwareMap.get(DigitalChannel.class, "magSwitch");
        magSwitch.setMode(DigitalChannel.Mode.INPUT);
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
            magLimit = magSwitch.getState();

            opMode.telemetry.addData("Magnetic Switch Pressed?", magLimit);

            opMode.telemetry.addData("Motor Power: ", liftMotor.getPower() + xGamepad2().left_stick_y + " Encoder Value: " + encoder.getCurrentPosition());

            //check if the encoder position is greater than the starting position and that there is no power from
            //the joy sticks.
            if(encoder.getCurrentPosition() <= -150 && xGamepad2().left_stick_y == 0){
                liftMotor.setPower(motorPower); //if so, set a constant motor power
            }else{
                liftMotor.setPower(xGamepad2().left_stick_y); // if not, just set it to the joystick value as normal
            }

        }
        /*if (xGamepad2().x.wasPressed()){
            toggleCap();
        }*/
    }

