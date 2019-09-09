package robotx.modules;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import robotx.libraries.XModule;

public class DriverCentric extends XModule {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor backLeft;
    BNO055IMU gyroSensor;
    Orientation lastAngles = new Orientation();
    double globalAngle;

    public DriverCentric(OpMode op){super(op);}

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        gyroSensor = opMode.hardwareMap.get(BNO055IMU.class, "gyroSensor");
        gyroSensor.initialize(parameters);

        opMode.telemetry.addData("heading: ", getHeadingAngle());
        opMode.telemetry.update();
    }
    public int getHeadingAngle() {

        Orientation angles = gyroSensor.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        int finalAngle = (int) globalAngle;

        lastAngles = angles;

        return finalAngle;

    }

    public void loop(){
        getHeadingAngle();
        opMode.telemetry.addData("heading: ", getHeadingAngle());
        opMode.telemetry.update();

        if (xGamepad1().left_stick_x > 0 && xGamepad1().left_stick_y < 0.3 && xGamepad1().left_stick_y > -0.3){
            frontLeft.setPower(xGamepad1().left_stick_x);
            frontRight.setPower(-xGamepad1().left_stick_x);
            backRight.setPower(xGamepad1().left_stick_x);
            backLeft.setPower(-xGamepad1().left_stick_x);
        }
        else if (xGamepad1().left_stick_x < 0 && xGamepad1().left_stick_y > -0.3 && xGamepad1().left_stick_y < 0.3){
            frontLeft.setPower(xGamepad1().left_stick_x);
            frontRight.setPower(-xGamepad1().left_stick_x);
            backRight.setPower(xGamepad1().left_stick_x);
            backLeft.setPower(-xGamepad1().left_stick_x);
        }
        else if (xGamepad1().left_stick_x > -0.3 && xGamepad1().left_stick_x < 0.3 && xGamepad1().left_stick_y > 0){
            frontRight.setPower(xGamepad1().left_stick_y);
            frontLeft.setPower(xGamepad1().left_stick_y);
            backLeft.setPower(xGamepad1().left_stick_y);
            backRight.setPower(xGamepad1().left_stick_y);
        }
        else if (xGamepad1().left_stick_y < 0 && xGamepad1().left_stick_x > -0.3 && xGamepad1().left_stick_x < 0.3){
            frontLeft.setPower(xGamepad1().left_stick_y);
            frontRight.setPower(xGamepad1().left_stick_y);
            backRight.setPower(xGamepad1().left_stick_y);
            backLeft.setPower(xGamepad1().left_stick_y);
        }
        else if (xGamepad1().left_stick_x > 0.3 && xGamepad1().left_stick_y > 0.3){
            frontRight.setPower(-xGamepad1().left_stick_y);
            backLeft.setPower(-xGamepad1().left_stick_y);
        }
        else if (xGamepad1().left_stick_y < -0.3 && xGamepad1().left_stick_x > 0.3){
            frontLeft.setPower(-xGamepad1().left_stick_y);
            backRight.setPower(-xGamepad1().left_stick_y);
        }
        else if (xGamepad1().left_stick_y < -0.3 && xGamepad1().left_stick_x < -0.3){
            frontRight.setPower(-xGamepad1().left_stick_y);
            backLeft.setPower(-xGamepad1().left_stick_y);
        }
        else if (xGamepad1().left_stick_x < -0.3 && xGamepad1().left_stick_y > 0.3){
            frontLeft.setPower(-xGamepad1().left_stick_y);
            backRight.setPower(-xGamepad1().left_stick_y);
        }
        else if (xGamepad1().right_stick_x > 0){
            frontLeft.setPower(xGamepad1().right_stick_x);
            backLeft.setPower(xGamepad1().right_stick_x);
            frontRight.setPower(-xGamepad1().right_stick_x);
            backRight.setPower(-xGamepad1().right_stick_x);
        }
        else if (xGamepad1().right_stick_x < 0){
            frontRight.setPower(-xGamepad1().right_stick_x);
            backRight.setPower(-xGamepad1().right_stick_x);
            frontLeft.setPower(xGamepad1().right_stick_x);
            backLeft.setPower(xGamepad1().right_stick_x);
        }
        else {
            frontRight.setPower(0);
            frontLeft.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
        }
    }
}
