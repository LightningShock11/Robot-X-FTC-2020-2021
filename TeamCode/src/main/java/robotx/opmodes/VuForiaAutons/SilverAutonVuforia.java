/* Copyright (c) 2018 FIRST. All rights reserved.
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

package robotx.opmodes.VuForiaAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.DumpingBucket;
import robotx.modules.LiftSystemXY;
import robotx.modules.TwoMotorDrive;
import robotx.modules.TwoWheelAutonIMU;
import robotx.modules.XSweeper;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "SilverAutonVuforia", group = "Sensors")
public class SilverAutonVuforia extends XLinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kTe ...
     * Once you've obtaind a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "ARo9TbL/////AAABmf26xzhrmUw4gqEzENG2yJ4vlkOmEn7b+uFwvT3tcaI7Lyffj/nQNfaGuKXmjtGV733pq+emm9N3nAG0adXVzW3MR+FbROdE5qE+1DtA48aZHxz7OHWssfYIR6trmSkGqjRl4Z60ZqngAK2kuQFyElEcZ4bLvC1qGA14JF020hHXRCfdveF4ecH4DZzeMGmYy/mZBNITgtFdRFQFS4DvmeL5WjjR+w77okz+3Cw2y+ovZnTIG9hNUZjqTAhj9E36gsgTyPAnpGUgM1hnJSNGHSh/qe0XMW5G+IhnksGQCy5smJtN+u4oiOG/g8Tfz/JOwdRFanYF1R/Sx2BCdzGcaf7FnHvNVHYFTYBvGZH+l/Yi";

    /**e
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    public boolean isLeft = false;
    public boolean isCenter = false;
    public boolean isRight = false;

    AutonomousMovement movement;
    TwoMotorDrive twoMotorDrive;
    TwoWheelAutonIMU sensors;
    LiftSystemXY liftSystemXY;
    DumpingBucket dumpingBucket;
    XSweeper xSweeper;


    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();


        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }




        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();

        waitForStart();



        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }



            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                          if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                          } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                          } else {
                            silverMineral2X = (int) recognition.getLeft();
                          }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                          if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                              telemetry.update();
                            isLeft = true;
                            break;
                          } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                              telemetry.update();
                            isRight = true;
                            break;
                          } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                              telemetry.update();
                            isCenter = true;
                            break;

                          }
                        }
                      }
                      telemetry.update();
                    }


                }

            }

        }
        if (tfod != null) {
            tfod.shutdown();
        }

        sensors = new TwoWheelAutonIMU(this);
        sensors.init();

        twoMotorDrive = new TwoMotorDrive(this);
        twoMotorDrive.init();

        movement = new AutonomousMovement(this, sensors, twoMotorDrive);
        movement.init();

        sensors.leftMotor = twoMotorDrive.leftMotor;
        sensors.rightMotor = twoMotorDrive.rightMotor;

        dumpingBucket = new DumpingBucket(this);
        dumpingBucket.init();

        liftSystemXY = new LiftSystemXY(this);
        liftSystemXY.init();

        xSweeper = new XSweeper(this);
        xSweeper.init();



        movement.start();
        twoMotorDrive.start();
        sensors.start();
        liftSystemXY.start();
        xSweeper.start();
        dumpingBucket.start();
        twoMotorDrive.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        twoMotorDrive.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /////////////////////Movement///////////////////////

        xSweeper.rotateFlat();
        sleep(250);
        liftSystemXY.extendY(1300);
        sleep(2050);
        movement.pointTurnRight(30);
        sleep(750);
        goForward(0.5, 250);
        sleep(150);
        movement.pointTurnLeft(30);
        stopDriving();

        //-----------Dehanging complete-----------\\


        if(isLeft){
            movement.pointTurnLeft(20);
            telemetry.addData("Gold:", "Left");
            telemetry.update();
            xSweeper.rotateUp();
            sleep(500);
            goForward(0.6, 1750);
            sleep(1000);
            movement.pointTurnLeft(77);
            sleep(500);
            goBackward(0.5, 850);
            sleep(500);
            liftSystemXY.retractY();
            sleep(1700);
            xSweeper.rotateFlat();
            sleep(1000);
            dumpingBucket.autoDump();
            sleep(500);
            xSweeper.rotateUp();
            sleep(500);
            goForward(0.6, 3000);
            sleep(2000);
            xSweeper.rotateDown();
            stopDriving();
        }else if(isRight){
            movement.pointTurnRight(32);
            telemetry.addData("Gold:", "Right");
            telemetry.update();
            sleep(500);
            liftSystemXY.extendX(1100);
            sleep(500);
            xSweeper.rotateDown();
            xSweeper.sweep(1.0,2000);
            sleep(750);
            xSweeper.rotateFlat();
            liftSystemXY.retractX();
            sleep(500);
            movement.pointTurnLeft(90);
            sleep(250);
            goForward(0.6, 3000);
            movement.pointTurnLeft(50);
            sleep(250);
            goBackward(0.5, 2500);
            liftSystemXY.retractY();
            sleep(1700);
            dumpingBucket.autoDump();
            sleep(250);
            xSweeper.rotateUp();
            sleep(250);
            goForward(0.6, 3000);
            xSweeper.rotateDown();
            stopDriving();
        }else if(isCenter){
            telemetry.addData("Gold:", "Center");
            telemetry.update();
            xSweeper.rotateUp();
            sleep(500);
            liftSystemXY.extendX(100);
            xSweeper.rotateDown();
            xSweeper.sweep(1.0,1250);
            xSweeper.rotateFlat();
            liftSystemXY.retractX();
            sleep(500);
            movement.pointTurnRight(100);
            sleep(500);
            goBackward(0.5, 2500);
            sleep(250);
            movement.pointTurnLeft(58);
            sleep(250);
            goBackward(0.6, 2300);
            liftSystemXY.retractY();
            sleep(1500);
            xSweeper.rotateFlat();
            sleep(1000);
            dumpingBucket.autoDump();
            sleep(500);
            xSweeper.rotateUp();
            sleep(500);
            goForward(0.6, 3000);
            sleep(500);
            xSweeper.rotateDown();
            stopDriving();
        }
        else{
            telemetry.addData("Gold:", "Not detected");
            telemetry.update();
            movement.pointTurnLeft(90);
            goForward(0.6, 2000);
            sleep(250);
            movement.pointTurnLeft(90);
            goBackward(1.0, 1300);
            xSweeper.rotateFlat();
            sleep(550);
            liftSystemXY.retractY();
            sleep(2500);
            dumpingBucket.autoDump();
            sleep(100);
            xSweeper.rotateUp();
            sleep(250);
            goForward(1.0, 2500);
            sleep(250);
            xSweeper.rotateDown();
            sleep(1000);
            stopDriving();
            twoMotorDrive.stop();
            movement.stop();
        }


        ////////////////////////////////////////////////////

    }
    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK; //Use front or back camera

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    /////////////////////Controls///////////////////////

    public void goForward(double power, int time){

        twoMotorDrive.leftMotor.setPower(-power);
        twoMotorDrive.rightMotor.setPower(-power);
        sleep(time);
        twoMotorDrive.rightMotor.setPower(0);
        twoMotorDrive.leftMotor.setPower(0);
    }
    public void goBackward(double power, int time){

        twoMotorDrive.leftMotor.setPower(power);
        twoMotorDrive.rightMotor.setPower(power);
        sleep(time);
        twoMotorDrive.rightMotor.setPower(0);
        twoMotorDrive.leftMotor.setPower(0);
    }

    public  void stopDriving (){
        twoMotorDrive.brakeAllMotors();
    }

}
