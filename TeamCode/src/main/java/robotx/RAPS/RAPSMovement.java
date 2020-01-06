package robotx.RAPS;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static robotx.RAPS.MathFunctions.*;


public class RAPSMovement {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor backLeft;
    public OpMode opMode;

    public DcMotor stoneArm;
    public DcMotor liftMotor;
    public BNO055IMU gyro;
    public Orientation lastAngles = new Orientation();
    public double globalAngle;

    public static double worldXpos = 0; //this needs to equal the odometry x pos
    public static double worldYpos = 0; //this needs to equal the odometry y pos
    public static double worldAngle = 0; //this needs to equal the gyro sensor angle

    public static double xMotorPower;
    public static double yMotorPower;
    public static double rotationPower;

    public static double movementXPower = 0;
    public static double movementYPower = 0;



    public void init() {
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
        gyro = opMode.hardwareMap.get(BNO055IMU.class, "gyroSensor");
        gyro.initialize(parameters);
    }
    public int getHeadingAngle() {
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
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
        worldXpos = stoneArm.getCurrentPosition();
        worldYpos = liftMotor.getCurrentPosition();
        worldAngle = getHeadingAngle();

        frontRight.setPower(xMotorPower + yMotorPower + rotationPower);
        frontLeft.setPower(xMotorPower + yMotorPower - rotationPower);
        backRight.setPower(xMotorPower - yMotorPower + rotationPower);
        backLeft.setPower((xMotorPower - yMotorPower - rotationPower));
        opMode.telemetry.addData("Current Positions: ", worldAngle + " " + worldXpos + " " + worldYpos);
    }

    public static void goToPos(double x, double y, double moveSpeed, double preferredAngle, double turnSpeed){
        //////Placeholder Variables//////


        /////////////////////////////////

        double distanceToTarget = Math.hypot(x-RAPSOpMode.worldXpos,y-RAPSOpMode.worldYpos);

        double absoluteAngleToTarget = Math.atan2(y-RAPSOpMode.worldYpos, x-RAPSOpMode.worldXpos);
        double relativeAngleToTarget = angleWrap(absoluteAngleToTarget - RAPSOpMode.worldAngle);

        double relativeXToPoint = Math.cos(relativeAngleToTarget)*distanceToTarget;
        double relativeYtoPoint = Math.sin(relativeAngleToTarget)*distanceToTarget;

        movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));
        movementYPower = relativeYtoPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));

        xMotorPower = movementXPower * moveSpeed;
        yMotorPower = movementYPower * moveSpeed;

        double relativeTurnAngle = relativeAngleToTarget - Math.toRadians(180) + preferredAngle;
        rotationPower = Range.clip(relativeTurnAngle/Math.toRadians(30),-1, 1) * turnSpeed;

        if(distanceToTarget < 30){
            rotationPower = 0;
            xMotorPower = 0;
            yMotorPower = 0;
        }

    }
}
