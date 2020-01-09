package robotx.RAPS.OpenCV.Stuff;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

import robotx.RAPS.RAPSMovement;
import robotx.RAPS.RAPSOpMode;
import robotx.modules.FlywheelIntake;
import robotx.modules.FoundationPins;
import robotx.modules.OrientationDrive;
import robotx.modules.StoneArm;
import robotx.modules.StoneClaw;
import robotx.modules.StoneLift;

@Autonomous
public class OdometryOpenCVTest extends LinearOpMode {


    /////////////////////////////////////
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor backLeft;

    public double xO;
    public double yO;
    public double rO;
    public double sO;

    public double targetX;
    public double targetY;
    public double movePower;


    public DcMotor xEncoder;
    public DcMotor yEncoder;
    public BNO055IMU gyro;
    public Orientation lastAngles = new Orientation();
    public double globalAngle;

    public static double worldXpos = 0; //this needs to equal the odometry x pos
    public static double worldYpos = 0; //this needs to equal the odometry y pos
    public static double worldAngle = 0; //this needs to equal the gyro sensor angle
    /////////////////////////////////////








    //0 means skystone, 1+ means yellow stone
    //-1 for debug, but we can keep it like this because if it works, it should change to either 0 or 255
    private static int valMid = -1;
    private static int valLeft = -1;
    private static int valRight = -1;

    private static float rectHeight = .6f / 8f;
    private static float rectWidth = 1.5f / 8f;

    private static float offsetX = 0f
            / 8f;//changing this moves the three rects and the three circles left or right, range : (-2, 2) not inclusive
    private static float offsetY = 3f
            / 8f;//changing this moves the three rects and circles up or down, range: (-4, 4) not inclusive

    private static float[] midPos = {4f / 8f + offsetX, 4f / 8f + offsetY};//0 = col, 1 = row
    private static float[] leftPos = {2f / 8f + offsetX, 4f / 8f + offsetY};
    private static float[] rightPos = {6f / 8f + offsetX, 4f / 8f + offsetY};
    //moves all rectangles right or left by amount. units are in ratio to monitor

    private final int rows = 1280;
    private final int cols = 720;

    public boolean isLeft;
    public boolean isCenter;
    public boolean isRight;

    public double multiplier = 0;

    OpenCvInternalCamera phoneCam;
    FlywheelIntake flywheelIntake;
    OrientationDrive movement;
    StoneArm stoneArm;
    StoneClaw stoneClaw;
    StoneLift stoneLift;
    FoundationPins pins;

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


    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        //phoneCam = new OpenCvWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        phoneCam.openCameraDevice();//open camera
        phoneCam.setPipeline(new StageSwitchingPipeline());//different stages
        phoneCam.startStreaming(rows, cols, OpenCvCameraRotation.SIDEWAYS_RIGHT);//display on RC
        //width, height
        //width = height in this case, because camera is in portrait mode.




        ///////////////////////////////////////
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight = hardwareMap.dcMotor.get("frontRight");
        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight = hardwareMap.dcMotor.get("backRight");
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        xEncoder = hardwareMap.dcMotor.get("liftMotor");
        yEncoder = hardwareMap.dcMotor.get("stoneArm");

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
        //////////////////////////////////////////



        if(getBatteryVoltage() >= 14.00){ //check if voltage is larger than 14.00
            multiplier = 0.09; //set multiplier to 0.09
            telemetry.addData("Multiplier", multiplier);
        }else if(getBatteryVoltage() < 14.00 && getBatteryVoltage() >= 13.80 ){
            multiplier = 0.07;
            telemetry.addData("Multiplier", multiplier);
        }else if(getBatteryVoltage() < 13.80 && getBatteryVoltage() >= 13.65){
            multiplier = 0.04;
            telemetry.addData("Multiplier", multiplier);
        }else if(getBatteryVoltage() < 13.65 && getBatteryVoltage() >= 13.40){
            multiplier = 0.023;
            telemetry.addData("Multiplier", multiplier);
        }else{
            multiplier = 0;
            telemetry.addData("Multiplier", multiplier);
        }


        movement = new OrientationDrive(this);
        movement.init();

        flywheelIntake = new FlywheelIntake(this);
        flywheelIntake.init();

        stoneClaw = new StoneClaw(this);
        stoneClaw.init();

        stoneArm = new StoneArm(this);
        stoneArm.init();

        pins = new FoundationPins(this);
        pins.init();

        stoneLift = new StoneLift(this);
        stoneLift.init();




        movement.start();
        stoneClaw.start();
        flywheelIntake.start();
        stoneArm.start();
        stoneClaw.start();
        pins.start();
        telemetry.addData("Starting Side: ", "Loading/Skystone");
        telemetry.addData("Position: ","Facing back wall, Color Sensor lines up with middle of tile");
        telemetry.update();

        movement.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        movement.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        movement.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        movement.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Values", valLeft + "   " + valMid + "   " + valRight);
            telemetry.addData("Height", rows);
            telemetry.addData("Width", cols);

            telemetry.addData("Frame Count", phoneCam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", phoneCam.getFps()));
            telemetry.addData("Total frame time ms", phoneCam.getTotalFrameTimeMs());
            telemetry.addData("Pipeline time ms", phoneCam.getPipelineTimeMs());


            telemetry.update();
            sleep(100);


////////////////////////////////////////////////////////////////////////////////////////////

            worldXpos = -xEncoder.getCurrentPosition();
            worldYpos = yEncoder.getCurrentPosition();
            worldAngle = Math.toRadians(getHeadingAngle());

            xO = RAPSMovement.xMotorPower;
            yO = RAPSMovement.yMotorPower;
            rO = RAPSMovement.rotationPower;
            sO = ((Math.max(Math.abs(xO), Math.max(Math.abs(yO), Math.abs(rO))))*(Math.max(Math.abs(xO),
                    Math.max(Math.abs(yO), Math.abs(rO)))))/((xO*xO)+(yO*yO)+(rO*rO));


            frontLeft.setPower((yO-xO-rO)*(sO));
            frontRight.setPower((yO+xO+rO)*(sO));
            backLeft.setPower((yO+xO-rO)*(sO));
            backRight.setPower((yO-xO+rO)*(sO));

		/*frontRight.setPower(RAPSMovement.xMotorPower + RAPSMovement.yMotorPower + RAPSMovement.rotationPower);
		frontLeft.setPower(RAPSMovement.xMotorPower + RAPSMovement.yMotorPower - RAPSMovement.rotationPower);
		backRight.setPower(RAPSMovement.xMotorPower - RAPSMovement.yMotorPower + RAPSMovement.rotationPower);
		backLeft.setPower(RAPSMovement.xMotorPower - RAPSMovement.yMotorPower - RAPSMovement.rotationPower);*/

            telemetry.addData(" X Power: ", RAPSMovement.movementXPower + " Y Power: " +
                    RAPSMovement.movementYPower + " Rotation Power: " + RAPSMovement.rotationPower);
            telemetry.addLine(" ");
            telemetry.addData("Angle: ", worldAngle + " X-Pos: " + worldXpos + " Y-Pos: " + worldYpos);
            telemetry.update();

////////////////////////////////////////////////////////////////////////////////////////////






            /////////////////////Movement///////////////////////
            sleep(750);
            strafeLeft(0.4,200);
            flywheelIntake.flywheelRight.setPower(1.0);
            flywheelIntake.flywheelLeft.setPower(1.0);
            sleep(1200);
            flywheelIntake.flywheelRight.setPower(0.0);
            flywheelIntake.flywheelLeft.setPower(0.0);
            sleep(1000);
            pins.deployPins();
            stoneArm.clawServo.setPosition(0.6);


            if(valLeft == 0 && valMid >= 1 && valRight >= 1){
                telemetry.addData("Skystone Position: ", "Left");
                telemetry.update();

                /**Go to skystone 1**/
                goToPos(1000,5000,0.75);
                telemetry.addData(" X-Pos: ", targetX + " Y-Pos: " + targetY);
                telemetry.addLine(" ");
                telemetry.addData(" X Power: ", RAPSMovement.movementXPower + " Y Power: " +
                        RAPSMovement.movementYPower + " Rotation Power: " + RAPSMovement.rotationPower);
                telemetry.addLine(" ");
                telemetry.addData("Angle: ", worldAngle + " X-Pos: " + worldXpos + " Y-Pos: " + worldYpos);
                telemetry.update();
                sleep(5000);
                telemetry.addData(" X-Pos: ", targetX + " Y-Pos: " + targetY);
                telemetry.addLine(" ");
                telemetry.addData(" X Power: ", RAPSMovement.movementXPower + " Y Power: " +
                        RAPSMovement.movementYPower + " Rotation Power: " + RAPSMovement.rotationPower);
                telemetry.addLine(" ");
                telemetry.addData("Angle: ", worldAngle + " X-Pos: " + worldXpos + " Y-Pos: " + worldYpos);
                telemetry.update();
                telemetry.update();
                goToPos(1000,1000,0.75);
                sleep(5000);




                /**Collect Skystone 1**/




            }else if(valLeft >= 1 && valMid >= 1 && valRight == 0){
                telemetry.addData("Skystone Position: ", "Right");
                telemetry.update();


                /**Collect Skystone 1**/




            }else if(valLeft >= 1 && valMid == 0 && valRight >= 1){
                telemetry.addData("Skystone Position: ", "center");
                telemetry.update();


                /**Collect first skystone**/



            }
            /**Reposition Foundation**/ //ONLY CHANGE THINGS BELOW THIS LINE

           /** sleep(500);
            if(multiplier == 0) {
                goBackward(0.5, 1750);
            }else if(multiplier == 0.023){
                goBackward(0.5, 1850);
            }else if(multiplier == 0.04){
                goBackward(0.5, 1870);
            }else if(multiplier == 0.07){
                goBackward(0.5, 1900);
            }else if(multiplier == 0.09){
                goBackward(0.5, 1900);
            }
            flywheelIntake.toggleFly();
            sleep(100);
            turnRight(90);
            sleep(100);
            goBackward(0.4,750);
            goBackward(0.2,400);
            stoneArm.stoneArm.setPower(-0.4);
            sleep(400);
            stoneClaw.clawServo.setPosition(0);
            pins.deployPins();
            sleep(600);
            goForward(0.5,1150);
            turnLeft(200);
            flywheelIntake.toggleFly();


            /**Place stone on foundation*

            stoneArm.stoneArm.setPower(0.55);
            sleep(1200);
            stoneClaw.clawServo.setPosition(0.6);
            sleep(1000);
            pins.deployPins();
            sleep(100);
            turnLeft(1);
            stoneArm.stoneArm.setPower(-0.35);
            flywheelIntake.toggleFlyReverse();

            /**Go to second skystone**

            sleep(500);
            goBackward(0.6,500);
            goForward(1.0,250);
            sleep(300);
            strafeRight(0.5, 1000);
            sleep(200);
            strafeLeft(1.0,650);
            sleep(200);
            flywheelIntake.toggleFly();
            flywheelIntake.toggleFly();
            sleep(200);
            goForward(1.0,500);
            sleep(200);
            stoneArm.stoneArm.setPower(0);
            stopDriving();
            sleep(10000);

            /**Collect Skystone 2

            strafeLeft(0.5, 900);
            goForward(0.5,700);
            sleep(200);
            strafeRight(1.0,400);
            stoneArm.stoneArm.setPower(-0.4);
            sleep(200);
            stoneClaw.clawServo.setPosition(0);
            sleep(200);

            /**Place Skystone 2 and park*

            goBackward(1.0,1200);
            stoneArm.stoneArm.setPower(0.35);
            sleep(700);
            stoneClaw.clawServo.setPosition(0.6);
            sleep(500);
            stoneArm.stoneArm.setPower(-0.35);
            goForward(1.0,600);
            sleep(300);
            sleep(10000);*/


        }
    }


    //detection pipeline
    static class StageSwitchingPipeline extends OpenCvPipeline {

        Mat yCbCrChan2Mat = new Mat();
        Mat thresholdMat = new Mat();
        Mat all = new Mat();
        List<MatOfPoint> contoursList = new ArrayList<>();

        enum Stage {//color difference. greyscale
            detection,//includes outlines
            THRESHOLD,//b&w
            RAW_IMAGE,//displays raw view
        }

        private Stage stageToRenderToViewport = Stage.detection;
        private Stage[] stages = Stage.values();

        @Override
        public void onViewportTapped() {
            /*
             * Note that this method is invoked from the UI thread
             * so whatever we do here, we must do quickly.
             */

            int currentStageNum = stageToRenderToViewport.ordinal();

            int nextStageNum = currentStageNum + 1;

            if (nextStageNum >= stages.length) {
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override
        public Mat processFrame(Mat input) {
            contoursList.clear();
            /*
             * This pipeline finds the contours of yellow blobs such as the Gold Mineral
             * from the Rover Ruckus game.
             */

            //color diff cb.
            //lower cb = more blue = skystone = white
            //higher cb = less blue = yellow stone = grey
            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);//converts rgb to ycrcb
            Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);//takes cb difference and stores

            //b&w
            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 102, 255, Imgproc.THRESH_BINARY_INV);

            //outline/contour
            Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST,
                    Imgproc.CHAIN_APPROX_SIMPLE);
            yCbCrChan2Mat.copyTo(all);//copies mat object
            //Imgproc.drawContours(all, contoursList, -1, new Scalar(255, 0, 0), 3, 8);//draws blue contours

            //get values from frame
            double[] pixMid = thresholdMat.get((int) (input.rows() * midPos[1]),
                    (int) (input.cols() * midPos[0]));//gets value at circle
            valMid = (int) pixMid[0];

            double[] pixLeft = thresholdMat.get((int) (input.rows() * leftPos[1]),
                    (int) (input.cols() * leftPos[0]));//gets value at circle
            valLeft = (int) pixLeft[0];

            double[] pixRight = thresholdMat.get((int) (input.rows() * rightPos[1]),
                    (int) (input.cols() * rightPos[0]));//gets value at circle
            valRight = (int) pixRight[0];

            //create three points
            Point pointMid = new Point((int) (input.cols() * midPos[0]),
                    (int) (input.rows() * midPos[1]));
            Point pointLeft = new Point((int) (input.cols() * leftPos[0]),
                    (int) (input.rows() * leftPos[1]));
            Point pointRight = new Point((int) (input.cols() * rightPos[0]),
                    (int) (input.rows() * rightPos[1]));

            //draw circles on those points
            Imgproc.circle(all, pointMid, 5, new Scalar(255, 0, 0), 1);//draws circle
            Imgproc.circle(all, pointLeft, 5, new Scalar(255, 0, 0), 1);//draws circle
            Imgproc.circle(all, pointRight, 5, new Scalar(255, 0, 0), 1);//draws circle

            //draw 3 rectangles
            Imgproc.rectangle(//1-3
                    all,
                    new Point(
                            input.cols() * (leftPos[0] - rectWidth / 2),
                            input.rows() * (leftPos[1] - rectHeight / 2)),
                    new Point(
                            input.cols() * (leftPos[0] + rectWidth / 2),
                            input.rows() * (leftPos[1] + rectHeight / 2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//3-5
                    all,
                    new Point(
                            input.cols() * (midPos[0] - rectWidth / 2),
                            input.rows() * (midPos[1] - rectHeight / 2)),
                    new Point(
                            input.cols() * (midPos[0] + rectWidth / 2),
                            input.rows() * (midPos[1] + rectHeight / 2)),
                    new Scalar(0, 255, 0), 3);
            Imgproc.rectangle(//5-7
                    all,
                    new Point(
                            input.cols() * (rightPos[0] - rectWidth / 2),
                            input.rows() * (rightPos[1] - rectHeight / 2)),
                    new Point(
                            input.cols() * (rightPos[0] + rectWidth / 2),
                            input.rows() * (rightPos[1] + rectHeight / 2)),
                    new Scalar(0, 255, 0), 3);

            switch (stageToRenderToViewport) {
                case THRESHOLD: {
                    return thresholdMat;
                }

                case detection: {
                    return all;
                }

                case RAW_IMAGE: {
                    return input;
                }

                default: {
                    return input;
                }
            }
        }

    }


    /////////////////////Controls///////////////////////

    public void goForward(double power, int time){

        power = power - multiplier;

        movement.frontLeft.setPower(-power);
        movement.frontRight.setPower(-power);
        movement.backLeft.setPower(-power);
        movement.backRight.setPower(-power);
        sleep(time);
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }
    public void goBackward(double power, int time){

        power = power - multiplier;

        movement.frontLeft.setPower(power);
        movement.frontRight.setPower(power);
        movement.backLeft.setPower(power);
        movement.backRight.setPower(power);
        sleep(time);
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }
    public void strafeRight(double power, int time){

        power = power - (multiplier/2);

        movement.frontLeft.setPower(-power);
        movement.frontRight.setPower(power);
        movement.backLeft.setPower(power);
        movement.backRight.setPower(-power);
        sleep(time);
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }
    public void strafeLeft(double power, int time){

        power = power - multiplier;

        movement.frontLeft.setPower(power);
        movement.frontRight.setPower(-power);
        movement.backLeft.setPower(-power);
        movement.backRight.setPower(power);
        sleep(time);
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }
    public void turnRight(int angle){
        telemetry.update();
        movement.frontLeft.setPower(-0.8 + multiplier);
        movement.backLeft.setPower(-0.8 + multiplier);
        movement.frontRight.setPower(0.8 - multiplier);
        movement.backRight.setPower(0.8 - multiplier);
        sleep((long)(angle*13.3)/(long)Math.PI);
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }
    public void turnLeft(int angle){
        telemetry.update();
        movement.frontLeft.setPower(0.8 - multiplier);
        movement.backLeft.setPower(0.8 - multiplier);
        movement.frontRight.setPower(-0.8 + multiplier);
        movement.backRight.setPower(-0.8 + multiplier);
        sleep((long)(angle*13.3)/(long)Math.PI);
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }

    public void stopDriving (){
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }

    double getBatteryVoltage() {
        double result = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
        }
        return result;
    }

    public void goToPos(double xPos, double yPos, double power){
        targetX = xPos;
        targetY = yPos;
        movePower = power;
        RAPSMovement.goToPos(targetX,targetY,movePower);
    }



}
