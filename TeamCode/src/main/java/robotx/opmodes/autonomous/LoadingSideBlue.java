/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import robotx.modules.FlywheelIntake;
import robotx.modules.FoundationPins;
import robotx.modules.OrientationDrive;
import robotx.modules.StoneArm;
import robotx.modules.StoneClaw;
import robotx.modules.StoneDetectionColor;
import robotx.modules.StoneLift;

/**
 * This 2019-2020 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Skystone game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "LoadingSideBlueAuton", group = "Autonomous")
public class LoadingSideBlue extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    public boolean isLeft;
    public boolean isCenter;
    public boolean isRight;


    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "ARo9TbL/////AAABmf26xzhrmUw4gqEzENG2yJ4vlkOmEn7b+uFwvT3tcaI7Lyffj/nQNfaGuKXmjtGV733pq+emm9N3nAG0adXVzW3MR+FbROdE5qE+1DtA48aZHxz7OHWssfYIR6trmSkGqjRl4Z60ZqngAK2kuQFyElEcZ4bLvC1qGA14JF020hHXRCfdveF4ecH4DZzeMGmYy/mZBNITgtFdRFQFS4DvmeL5WjjR+w77okz+3Cw2y+ovZnTIG9hNUZjqTAhj9E36gsgTyPAnpGUgM1hnJSNGHSh/qe0XMW5G+IhnksGQCy5smJtN+u4oiOG/g8Tfz/JOwdRFanYF1R/Sx2BCdzGcaf7FnHvNVHYFTYBvGZH+l/Yi";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;
    public String objective;
    public boolean isTurning;

    FlywheelIntake flywheelIntake;
    OrientationDrive movement;
    StoneArm stoneArm;
    StoneClaw stoneClaw;
    StoneLift stoneLift;
    FoundationPins pins;
    //StoneDetectionColor detection;


    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        /**
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.

        if (tfod != null) {
            tfod.activate();
        }

        /** Wait for the game to begin
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());

                      // step through the list of recognitions and display boundary info.
                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                          recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                      }
                      telemetry.update();
                    }
                }
            }
        }

       if (tfod != null) {
            tfod.shutdown();
        }
        **/
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

        //detection = new StoneDetectionColor(this);
        //detection.init();



        movement.start();
        stoneClaw.start();
        flywheelIntake.start();
        stoneArm.start();
        stoneClaw.start();
        pins.start();
        //detection.start();
        telemetry.addData("Starting Side: ", "Loading/Skystone");
        telemetry.addData("Position: ","Facing back wall, Color Sensor lines up with middle of tile");
        telemetry.update();

        movement.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        movement.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        movement.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        movement.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.update();
        waitForStart();


        if (opModeIsActive()) {

            /////////////////////Movement///////////////////////
            flywheelIntake.flywheelRight.setPower(1.0);
            flywheelIntake.flywheelLeft.setPower(1.0);
            sleep(800);
            flywheelIntake.flywheelRight.setPower(0.0);
            flywheelIntake.flywheelLeft.setPower(0.0);
            stoneArm.stoneArm.setPower(-0.5);
            //goForward(1.0,500);
            //sleep(500);
            //strafeLeft(1.0,650);
            strafeLeft(1.0,200);




            /**if (detection.stoneColor.red() < 60 && detection.stoneColor.green() < 60 && detection.stoneColor.blue() < 60){
                isCenter = true;
                isLeft = false;
                isRight = false;
                telemetry.addData("Skystone Position: ", "center");
                telemetry.update();
                sleep(1000);
            } else if(isCenter == false && isLeft == false && isRight == false) {
                sleep(1000);
                goBackward(1.0, 125);
                sleep(1000);
                if (detection.stoneColor.red() < 60 && detection.stoneColor.green() < 60 && detection.stoneColor.blue() < 60) {
                    isCenter = false;
                    isLeft = true;
                    isRight = false;
                    telemetry.addData("Skystone Position: ", "left");
                    telemetry.update();
                    sleep(2000);
                } else {
                    sleep(1000);
                    isCenter = false;
                    isLeft = false;
                    isRight = true;
                    telemetry.addData("Skystone Position: ", "right");
                    telemetry.update();
                }
            }
            if(isCenter){ //if the robot detected center
                goBackward(1.0,325);
                sleep(500);
                strafeLeft(1.0,400);
                flywheelIntake.toggleFly();
                sleep(500);
                goForward(0.3, 900);
                strafeRight(1.0,370);
                goBackward(1.0,890);
                flywheelIntake.toggleFly();
                sleep(1000);
                turnRight(182);
                flywheelIntake.toggleFlyReverse();
                sleep(1000);
                flywheelIntake.toggleFlyReverse();
                sleep(500);
                goBackward(1.0,425);
            }else if(isRight){ //if the robot detected right
                goBackward(1.0,100);
                strafeLeft(1.0,350);
                flywheelIntake.toggleFly();
                sleep(500);
                goForward(0.3, 900);
                strafeRight(1.0,350);
                goBackward(1.0,890);
                flywheelIntake.toggleFly();
                sleep(1000);
                turnRight(180);
                flywheelIntake.toggleFlyReverse();
                sleep(1000);
                flywheelIntake.toggleFlyReverse();
                sleep(500);
                goBackward(1.0,340);
            }else if(isLeft){ //if the robot detected left
                strafeRight(1.0,150);
                turnLeft(88);
                strafeLeft(0.7,250);
                flywheelIntake.toggleFly();
                sleep(500);
                goForward(0.3, 1100);
                goBackward(1.0,350);
                flywheelIntake.toggleFly();
                sleep(1000);
                turnLeft(88);
                goForward(1.0,900);
                flywheelIntake.toggleFlyReverse();
                sleep(1000);
                flywheelIntake.toggleFlyReverse();
                sleep(500);
                goBackward(1.0,350);
            }
            turnRight(94);
            stopDriving();**/


        }
    }
    ///////////////////////////////////////////////////

    /**
     * Initialize the Vuforia localiszation engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
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
