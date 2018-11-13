package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class OneStickDrive extends XModule {
    DcMotor leftMotor;
    DcMotor rightMotor;

    public OneStickDrive(OpMode op){super(op);}

    public void init(){
        leftMotor = opMode.hardwareMap.dcMotor.get("leftMotor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor = opMode.hardwareMap.dcMotor.get("rightMotor");
    }

    public void loop(){
        leftMotor.setPower(xGamepad1().left_stick_y * 5);
        rightMotor.setPower(xGamepad1().left_stick_y * 5);

        leftMotor.setPower(-xGamepad1().right_stick_x * 5);
        rightMotor.setPower(xGamepad1().right_stick_x * 5);
    }

}
