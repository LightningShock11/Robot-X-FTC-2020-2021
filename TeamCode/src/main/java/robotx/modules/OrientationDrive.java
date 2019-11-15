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

public class OrientationDrive extends XModule {
    public OrientationDrive(OpMode op){super(op);}

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor backLeft;
    public BNO055IMU gyroSensor;
    public Orientation lastAngles = new Orientation();
    public double globalAngle;
    public double robotAngle;
    public double joystickAngle;

    public double x;
    public double y;
    public double s;
    public double r;

    public double xPrime;
    public double yPrime;

    public boolean orientationMode = true;
    public boolean slowMode = false;
    public double multiplier = 1;

    public void init(){
        frontLeft = opMode.hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = opMode.hardwareMap.dcMotor.get("frontRight");
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = opMode.hardwareMap.dcMotor.get("backRight");
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = opMode.hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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
    public void switchMode(){
        if (orientationMode){
            orientationMode = false;
        }
        else{
            orientationMode = true;
        }
    }
    public void toggleSlow(){
        if (slowMode){
            slowMode = false;
        }
        else {
            slowMode = true;
        }
    }
    public void loop(){
        getHeadingAngle();

        if(orientationMode){
            robotAngle = Math.toRadians(globalAngle);
        }
        else{
            robotAngle = 0;
        }
        if (xGamepad1().y.wasPressed()) {
            switchMode();
        }
        opMode.telemetry.addData("Orientation mode:", orientationMode);


        x = xGamepad1().left_stick_x;
        y = xGamepad1().left_stick_y;
        r = xGamepad1().right_stick_x;
        s = ((Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(r))))*(Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(r)))))/((x*x)+(y*y)+(r*r));

        if (x>0){
            joystickAngle = Math.atan(y/x);
        }
        else if (x<0){
            joystickAngle = Math.atan(y/x) + Math.toRadians(180);
        }
        else if (x == 0 && y>0){
            joystickAngle = Math.toRadians(90);
        }
        else if (x == 0 && y<0){
            joystickAngle = Math.toRadians(270);
        }

        xPrime = (Math.sqrt((x*x) + (y*y))) * (Math.cos(robotAngle + joystickAngle));
        yPrime = (Math.sqrt((x*x + y*y))) * (Math.sin(robotAngle + joystickAngle));

        if (xGamepad1().x.wasPressed()){
            toggleSlow();
        }
        if (slowMode){
            frontLeft.setPower((yPrime-xPrime-r)*(s) * .5);
            backRight.setPower((yPrime-xPrime+r)*(s) * .5);

            frontRight.setPower((yPrime+xPrime+r)*(s) * .5);
            backLeft.setPower((yPrime+xPrime-r)*(s) * .5);
        }
        else {
            frontLeft.setPower((yPrime-xPrime-r)*(s));
            backRight.setPower((yPrime-xPrime+r)*(s));

            frontRight.setPower((yPrime+xPrime+r)*(s));
            backLeft.setPower((yPrime+xPrime-r)*(s));
        }
    }
}