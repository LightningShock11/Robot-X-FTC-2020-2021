package robotx.RAPS;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Kush Dalal on 11/15/19.
 */
@TeleOp(name = "RAPSOpMode", group = "Default")
public class RAPSOpMode extends OpMode {
	public DcMotor frontLeft;
	public DcMotor frontRight;
	public DcMotor backRight;
	public DcMotor backLeft;

	public double xO;
	public double yO;
	public double rO;
	public double sO;


	public DcMotor xEncoder;
	public DcMotor yEncoder;
	public BNO055IMU gyro;
	public Orientation lastAngles = new Orientation();
	public double globalAngle;

	public static double worldXpos = 0; //this needs to equal the odometry x pos
	public static double worldYpos = 0; //this needs to equal the odometry y pos
	public static double worldAngle = 0; //this needs to equal the gyro sensor angle


	public void init() {
		frontLeft = hardwareMap.dcMotor.get("frontLeft");
		frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
		frontRight = hardwareMap.dcMotor.get("frontRight");
		//frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
		backRight = hardwareMap.dcMotor.get("backRight");
		//backRight.setDirection(DcMotorSimple.Direction.REVERSE);
		backLeft = hardwareMap.dcMotor.get("backLeft");
		backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
		
		xEncoder = hardwareMap.dcMotor.get("stoneArm");
		yEncoder = hardwareMap.dcMotor.get("liftMotor");

		xEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		yEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


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
		gyro = hardwareMap.get(BNO055IMU.class, "gyroSensor");
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


	public void start() {

	}


	public void loop() {
		RAPSMovement.goToPos(2159,0,0.5,0,0.5);
		worldXpos = -xEncoder.getCurrentPosition();
		worldYpos = yEncoder.getCurrentPosition();
		worldAngle = getHeadingAngle();

		xO = RAPSMovement.xMotorPower;
		yO = RAPSMovement.yMotorPower;
		rO = RAPSMovement.rotationPower;
		sO = ((Math.max(Math.abs(xO), Math.max(Math.abs(yO), Math.abs(rO))))*(Math.max(Math.abs(xO), Math.max(Math.abs(yO), Math.abs(rO)))))/((xO*xO)+(yO*yO)+(rO*rO));


		frontLeft.setPower((yO-xO-rO)*(sO));
		frontRight.setPower((yO+xO+rO)*(sO));
		backLeft.setPower((yO+xO-rO)*(sO));
		backRight.setPower((yO-xO+rO)*(sO));

		/*frontRight.setPower(RAPSMovement.xMotorPower + RAPSMovement.yMotorPower + RAPSMovement.rotationPower);
		frontLeft.setPower(RAPSMovement.xMotorPower + RAPSMovement.yMotorPower - RAPSMovement.rotationPower);
		backRight.setPower(RAPSMovement.xMotorPower - RAPSMovement.yMotorPower + RAPSMovement.rotationPower);
		backLeft.setPower(RAPSMovement.xMotorPower - RAPSMovement.yMotorPower - RAPSMovement.rotationPower);*/

		telemetry.addData("X Power: ", RAPSMovement.movementXPower + "Y Power: " + RAPSMovement.movementYPower);
		telemetry.addData("Angle", worldAngle + " " + worldXpos + " " + worldYpos);

	}


}
