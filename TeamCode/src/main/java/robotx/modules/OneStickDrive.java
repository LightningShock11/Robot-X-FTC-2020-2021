package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import robotx.libraries.XModule;

public class OneStickDrive extends XModule {
    DcMotor leftMotor;
    DcMotor rightMotor;

    float xValue, yValue;

    public OneStickDrive(OpMode op){super(op);}

    public void init(){
        leftMotor = opMode.hardwareMap.dcMotor.get("leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor = opMode.hardwareMap.dcMotor.get("rightMotor");
    }
    public void loop(){
        opMode.telemetry.addData("yValue", yValue);
        opMode.telemetry.addData("xValue", xValue);

        yValue = xGamepad1().right_stick_y;
        xValue = xGamepad1().left_stick_x;

        leftMotor.setPower(yValue - xValue);
        rightMotor.setPower(yValue + xValue);
    }
}
