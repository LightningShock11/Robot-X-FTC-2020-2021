package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class CoachDDrive extends XModule {
    public CoachDDrive(OpMode op){super(op);}

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor backLeft;

    double x;
    double y;
    double s;
    double r;

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop(){
        x = xGamepad1().left_stick_x;
        y = xGamepad1().left_stick_y;
        r = xGamepad1().right_stick_x;
        s = ((Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(r))))*(Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(r)))))/((x*x)+(y*y)+(r*r));

        frontLeft.setPower((y-x+r)*(s));
        backRight.setPower((y-x-r)*(s));

        frontRight.setPower((y+x-r)*(s));
        backLeft.setPower((y+x+r)*(s));
    }
}
