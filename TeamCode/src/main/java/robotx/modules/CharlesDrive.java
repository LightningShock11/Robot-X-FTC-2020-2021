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
    double r;
    double x;
    double y;

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop(){
        //-----------Variables----------\\
        x = xGamepad1().left_stick_x;
        y = xGamepad1().left_stick_y;
        //------------------------------\\

        ////////////////////Cartesian to Polar Conversions/////////////////////////////

        if(x > 0 && y > 0){ //Quadrant 1
            theta = Math.toDegrees(Math.atan(y/x)); //theta = the inverse tangent of y/x
            r = Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0)); //r = the square root of x^2 + y^2 (Pythagorean Theorem)
        }
        /*else if(x < 0 && y > 0){ //Quadrant 2
            theta = (180 + Math.toDegrees(Math.atan(y/x)));
            r = Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0));
        }
        else if(x < 0 && y < 0){ //Quadrant 3
            theta = (180 + Math.toDegrees(Math.atan(y/x)));
        r = Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0));
        }
        else if(x > 0 && y < 0){ //Quadrant 4
            theta = (360 - Math.toDegrees(Math.atan(y/x)));
            r = Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0));
        }
*/
        ////////////////////Applying conversions to motors////////////////////////////

        //  1/45 = 0.022222

        if (x > 0 && y > 0){ //Quadrant 1 code
            frontLeft.setPower(1*r);
            backRight.setPower(1*r);
            frontRight.setPower((((.02222)*theta)-1)*r);
            backLeft.setPower((((.02222)*theta)-1)*r);
        }
       /* else if (x < 0 && y > 0){ //quadrant 2 code
            frontLeft.setPower((((-.02222)*theta)+3)*r);
            backRight.setPower((((-.02222)*theta)+3)*r);
            frontRight.setPower(1*r);
            backLeft.setPower(1*r);
        }
        else if (x < 0 && y < 0){ //quadrant 3 code
            frontLeft.setPower(-1*r);
            backRight.setPower(-1*r);
            frontRight.setPower((((-.02222)*theta)-3)*r);
            backLeft.setPower((((-.02222)*theta)-3)*r);
        }
        else if (x > 0 && y < 0){ //quadrant 4 code
            frontLeft.setPower((((.02222)*theta)+1)*r);
            backRight.setPower((((.02222)*theta)+1)*r);
            frontRight.setPower(-1*r);
            backLeft.setPower(-1*r);
        }
*/
        else{
            frontLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
        }
    }
}
