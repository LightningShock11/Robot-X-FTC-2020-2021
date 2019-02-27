package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class RLdrive extends XModule {
    DcMotor leftMotor;
    DcMotor rightMotor;
    boolean isSlowMode;

    float xValue, power, brake;

    public RLdrive(OpMode op){super(op);}

    public void toggleSlow(){
       if(isSlowMode){
           isSlowMode = false;
       }else{
           isSlowMode = true;
       }
    }

    public void init(){
        leftMotor = opMode.hardwareMap.dcMotor.get("leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor = opMode.hardwareMap.dcMotor.get("rightMotor");
    }
    public void loop(){
        opMode.telemetry.addData("Power", power);
        opMode.telemetry.addData("xValue", xValue);

        if(xGamepad1().x.wasPressed()){
            toggleSlow();
        }

        if (!isSlowMode) {
            xValue = xGamepad1().left_stick_x;
            power = -xGamepad1().right_trigger;
            brake = xGamepad1().left_trigger;
        }
        else{
            xValue = xGamepad1().left_stick_x/2;
            power = -xGamepad1().right_trigger/2;
            brake = xGamepad1().left_trigger/2;
        }



        leftMotor.setPower(power + brake - xValue);
        rightMotor.setPower(power + brake + xValue);

        //rightMotor.setPower(-xValue);
        //leftMotor.setPower(xValue);

    }
}
