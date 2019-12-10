package robotx.RAPS.OpenCV.Stuff;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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

import robotx.modules.FlywheelIntake;
import robotx.modules.FoundationPins;
import robotx.modules.OrientationDrive;
import robotx.modules.StoneArm;
import robotx.modules.StoneClaw;
import robotx.modules.StoneDetectionColor;
import robotx.modules.StoneLift;

@Autonomous
public class LoadingRedOpenCV extends LinearOpMode {

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

    OpenCvInternalCamera phoneCam;
    FlywheelIntake flywheelIntake;
    OrientationDrive movement;
    StoneArm stoneArm;
    StoneClaw stoneClaw;
    StoneLift stoneLift;
    FoundationPins pins;
    StoneDetectionColor detection;

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

        detection = new StoneDetectionColor(this);
        detection.init();


        movement.start();
        stoneClaw.start();
        flywheelIntake.start();
        stoneArm.start();
        stoneClaw.start();
        pins.start();
        detection.start();
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

            /////////////////////Movement///////////////////////
            sleep(750);
            strafeLeft(0.4,200);
            flywheelIntake.flywheelRight.setPower(1.0);
            flywheelIntake.flywheelLeft.setPower(1.0);
            sleep(800);
            flywheelIntake.flywheelRight.setPower(0.0);
            flywheelIntake.flywheelLeft.setPower(0.0);
            pins.deployPins();
            stoneClaw.clawServo.setPosition(0.7);

            if(valLeft == 0 && valMid >= 1 && valRight >= 1){
                telemetry.addData("Skystone Position: ", "Right");
                telemetry.update();
                turnLeft(170);
                sleep(1000);
                stoneArm.stoneArm.setPower(0);
                flywheelIntake.toggleFly();
                goBackward(0.7, 400);
                sleep(1000);
                strafeRight(0.5,1800);
                sleep(400);
                goForward(0.5,650);
                sleep(1000);
                stoneArm.stoneArm.setPower(-0.4);
                sleep(1500);
                stoneClaw.clawServo.setPosition(0.7);
                strafeLeft(1.0,300);
                sleep(200);
                turnLeft(88);
                sleep(1000);
                strafeLeft(1.0,1400);
                sleep(500);
                goBackward(0.3,500);
                sleep(100);
                pins.deployPins();
                sleep(800);
                goForward(1.0,350);
                turnRight(190);
                sleep(300);
                stoneArm.deploy();
                sleep(2000);
                stoneClaw.clawServo.setPosition(0);
                sleep(1500);
                stoneArm.deploy();
                sleep(1000);
                pins.deployPins();
                stoneArm.deploy();
                sleep(500);
                strafeRight(1.0,300);
                sleep(200);
                goForward(1.0,500);
            }else if(valLeft >= 1 && valMid >= 1 && valRight == 0){
                telemetry.addData("Skystone Position: ", "Right");
                telemetry.update();
                goBackward(0.5,250);
                sleep(200);
                strafeLeft(0.5,1800);
                stoneArm.stoneArm.setPower(0);

            if(valLeft >= 1 && valMid >= 1 && valRight == 0){
                telemetry.addData("Skystone Position: ", "Right");
                telemetry.update();
                goForward(1.0, 100);
                turnLeft(88);
                flywheelIntake.toggleFly();
                goBackward(0.3,1400);
                sleep(100);
                goForward(1.0,450);
                sleep(100);
                turnLeft(92);
                sleep(100);
                goBackward(1.0,950);
                turnLeft(90);
                sleep(1000);
                goForward(0.4,400);
                sleep(1000);
                pins.deployPins();
                sleep(1000);
                goBackward(0.5,950);
                turnLeft(200);
                stoneArm.stoneArm.setPower(-0.5);
                sleep(1000);
                stoneArm.deploy();
                sleep(2000);
                stoneClaw.clawServo.setPosition(0.7);
                sleep(2000);
                stoneArm.deploy();
                sleep(2000);
                pins.deployPins();
                goBackward(1.0,200);
                sleep(300);
                strafeLeft(1.0,450);
                sleep(300);
                goBackward(0.6,900);
            }else if(valLeft == 0 && valMid >= 1 && valRight >= 1){
                telemetry.addData("Skystone Position: ", "Left");
                telemetry.update();
                goForward(1.0,100);
                sleep(100);
                strafeLeft(1.0, 770);
                sleep(100);
                stoneArm.stoneArm.setPower(.05);
                sleep(200);
                flywheelIntake.toggleFly();
                sleep(2000);
                goForward(0.5,700);
                sleep(200);
                strafeRight(1.0,500);
                stoneArm.stoneArm.setPower(-0.7);
                sleep(2000);
                stoneClaw.clawServo.setPosition(0.7);
                sleep(1500);
                goForward(1.0,1000);
                sleep(200);
                turnRight(88);
                sleep(200);
                goBackward(0.4,600);
                sleep(200);
                pins.deployPins();
                stoneArm.deploy();
                sleep(1500);
                stoneClaw.clawServo.setPosition(0);
                sleep(500);
                stoneArm.deploy();
                sleep(500);
                goForward(0.5,1000);
                sleep(500);
                turnRight(190);
                pins.deployPins();
                sleep(1000);
                strafeRight(1.0,300);
                sleep(200);
                goForward(1.0,600);
            }else if(valLeft >= 1 && valMid == 0 && valRight >= 1){
                telemetry.addData("Skystone Position: ", "center");
                telemetry.update();
                goBackward(0.5,500);
                goBackward(0.3, 500);
                sleep(1000);
                stoneArm.stoneArm.setPower(-0.5);
                sleep(750);
                stoneClaw.clawServo.setPosition(0);
                sleep(750);
                strafeRight(1.0,400);
                flywheelIntake.toggleFlyReverse();
                sleep(500);
                goForward(1.0,1150);
                sleep(500);
                turnRight(88);
                sleep(500);
                goForward(0.4,400);
                sleep(500);
                pins.deployPins();
                sleep(500);
                goBackward(0.5,950);
                turnLeft(200);
                stoneArm.stoneArm.setPower(-0.5);
                sleep(500);
                stoneArm.deploy();
                sleep(2000);
                stoneClaw.clawServo.setPosition(0.7);
                sleep(1000);
                stoneArm.deploy();
                sleep(2000);
                pins.deployPins();
                sleep(300);
                goBackward(1.0,200);
                sleep(300);
                strafeLeft(1.0,200);
                turnLeft(90);
                sleep(200);
                flywheelIntake.toggleFlyReverse();
                sleep(1000);
                flywheelIntake.toggleFlyReverse();
                sleep(300);
                strafeRight(0.6,1000);
            }else if(valLeft >= 1 && valMid == 0 && valRight >= 1){
                telemetry.addData("Skystone Position: ", "center");
                telemetry.update();
                goForward(1.0,150);
                sleep(100);
                strafeLeft(1.0, 770);
                sleep(100);
                stoneArm.stoneArm.setPower(.05);
                sleep(200);
                strafeLeft(0.5,1800);
                stoneArm.stoneArm.setPower(0);
                flywheelIntake.toggleFly();
                sleep(1500);
                goForward(0.5,700);
                sleep(200);
                strafeRight(1.0,500);
                stoneArm.stoneArm.setPower(-0.7);
                sleep(2000);
                stoneClaw.clawServo.setPosition(0.7);
                sleep(1500);
                goForward(1.0,1050);
                sleep(200);
                turnRight(88);
                sleep(1000);
                goBackward(0.4,900);
                sleep(200);
                pins.deployPins();
                stoneArm.deploy();
                sleep(1500);
                stoneClaw.clawServo.setPosition(0);
                sleep(500);
                sleep(500);
                goBackward(0.3, 400);
                sleep(1000);
                stoneArm.stoneArm.setPower(-0.1);
                sleep(500);
                stoneClaw.clawServo.setPosition(0);
                sleep(500);
                strafeRight(1.0,400);
                flywheelIntake.toggleFlyReverse();
                sleep(500);
                goForward(1.0,950);
                sleep(500);
                turnRight(88);
                sleep(500);
                goForward(0.4,400);
                sleep(500);
                pins.deployPins();
                sleep(500);
                goBackward(0.5,950);
                turnLeft(200);
                stoneArm.stoneArm.setPower(-0.5);
                sleep(1000);
                stoneArm.deploy();
                sleep(2000);
                stoneClaw.clawServo.setPosition(0.7);
                sleep(1000);
                stoneArm.deploy();
                sleep(500);
                goForward(0.5,1000);
                sleep(500);
                turnRight(120);
                pins.deployPins();
                sleep(1000);
                strafeRight(1.0,300);
                sleep(200);
                goForward(1.0,600);
            }
            turnLeft(170);
            sleep(200);
            flywheelIntake.toggleFlyReverse();
            sleep(1000);
            flywheelIntake.toggleFlyReverse();
            stopDriving();
            stop();
            sleep(10000);
            ///////////////////////////////////////////////////
                sleep(500);
                goBackward(1.0,200);
                sleep(300);
                strafeLeft(1.0,200);
                turnLeft(90);
                sleep(200);
                flywheelIntake.toggleFlyReverse();
                sleep(1000);
                flywheelIntake.toggleFly();
                sleep(300);
                strafeRight(0.6,1000);
                sleep(300);
                goBackward(0.6,900);
            }

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
        movement.frontLeft.setPower(-0.8);
        movement.backLeft.setPower(-0.8);
        movement.frontRight.setPower(0.8);
        movement.backRight.setPower(0.8);
        sleep((long)(angle*13.3)/(long)Math.PI);
        movement.frontLeft.setPower(0);
        movement.frontRight.setPower(0);
        movement.backLeft.setPower(0);
        movement.backRight.setPower(0);
    }
    public void turnLeft(int angle){
        telemetry.update();
        movement.frontLeft.setPower(0.8);
        movement.backLeft.setPower(0.8);
        movement.frontRight.setPower(-0.8);
        movement.backRight.setPower(-0.8);
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

}
