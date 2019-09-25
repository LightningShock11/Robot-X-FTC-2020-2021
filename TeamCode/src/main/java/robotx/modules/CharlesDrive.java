package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import robotx.libraries.XModule;

public class CharlesDrive extends XModule {
    public CharlesDrive(OpMode op){super(op);}

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor backLeft;

    double theta;

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backleft");
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop(){
        theta = Math.toDegrees(Math.atan(xGamepad1().left_stick_y/xGamepad1().left_stick_x));

        if (-180<theta && theta<-90){
            frontLeft.setPower(-1);
            backRight.setPower(-1);
            frontRight.setPower(-1);
            backLeft.setPower(-1);
        }
        else if (-90<theta && theta<0){
            frontLeft.setPower(((1/45)*theta)+1);
            backRight.setPower(((1/45)*theta)+1);
            frontRight.setPower(-1);
            backLeft.setPower(-1);
        }
        else if (0<theta && theta<90){
            frontLeft.setPower(1);
            backRight.setPower(1);
            frontRight.setPower(((1/45)*theta)-1);
            backLeft.setPower(((1/45)*theta)-1);
        }
        else if (90<theta && theta<180){
            frontLeft.setPower(((-1/45)*theta)+3);
            backRight.setPower(((-1/45)*theta)+3);
            frontRight.setPower(1);
            backLeft.setPower(1);
        }
        else{
            frontLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
        }
    }
}
