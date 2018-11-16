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
        opMode.telemetry.addData("yvalue", yValue);
        opMode.telemetry.addData("xValue", xValue);

        yValue = xGamepad1().left_stick_y;
        xValue = xGamepad1().right_stick_x;

        leftMotor.setPower(Range.clip(yValue, -1.0, 1.0));
        rightMotor.setPower(Range.clip(yValue, -1.0, 1.0));

        leftMotor.setPower(Range.clip(-xValue, -1.0, 1.0));
        rightMotor.setPower(Range.clip(xValue, -1.0, 1.0));
    }
}
