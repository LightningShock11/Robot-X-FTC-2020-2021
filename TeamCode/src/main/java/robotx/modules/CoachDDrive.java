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

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop(){
        x = xGamepad1().left_stick_x;
        y = xGamepad1().left_stick_y;

        if (x>1 && y>1){
            frontLeft.setPower(Math.max(y,(y+x)));
            backRight.setPower(-Math.max(y,(y+x)));
            frontRight.setPower(Math.min(y,(y+x)));
            backLeft.setPower(-Math.min(y,(y+x)));
        }
        else if (x<1 && y>1){
            frontLeft.setPower(Math.min(y,(y-x)));
            backRight.setPower(-Math.min(y,(y-x)));
            frontRight.setPower(Math.min(y,(y+x)));
            backLeft.setPower(Math.min(y,(y+x)));
        }
        else if (x<1 && y<1){
            frontLeft.setPower(Math.max(y,(y+x)));
            backRight.setPower(-Math.max(y,(y+x)));
            frontRight.setPower(Math.max(y,(y-x)));
            backLeft.setPower(-Math.max(y,(y-x)));
        }
        else if (x>1 && y<1){
            frontLeft.setPower(Math.max(y,(y+x)));
            backRight.setPower(-Math.max(y,(y+x)));
            frontRight.setPower(-Math.max(y,(y-x)));
            backLeft.setPower(Math.max(y,(y-x)));
        }
    }
}
