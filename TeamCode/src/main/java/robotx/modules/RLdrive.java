package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class RLdrive extends XModule {
    DcMotor leftMotor;
    DcMotor rightMotor;

    float xValue, power, brake;

    public RLdrive(OpMode op){super(op);}

    public void init(){
        leftMotor = opMode.hardwareMap.dcMotor.get("leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor = opMode.hardwareMap.dcMotor.get("rightMotor");
    }
    public void loop(){
        opMode.telemetry.addData("Power", power);
        opMode.telemetry.addData("xValue", xValue);


        xValue = xGamepad1().left_stick_x;
        power = xGamepad1().right_trigger;
        brake = -xGamepad1().left_trigger;


        leftMotor.setPower(power + brake + xValue);
        rightMotor.setPower(power + brake - xValue);

        //rightMotor.setPower(-xValue);
        //leftMotor.setPower(xValue);

    }
}
