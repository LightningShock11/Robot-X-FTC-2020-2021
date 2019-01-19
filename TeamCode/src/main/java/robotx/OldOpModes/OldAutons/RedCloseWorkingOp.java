package robotx.OldOpModes.OldAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import robotx.libraries.OmniAutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.OldModules.GlyphClaw;
import robotx.OldModules.JewelColor;
import robotx.OldModules.LedAlwaysOn;
import robotx.modules.MechanumAuton;
import robotx.modules.MechanumDrive;
import robotx.modules.VuMarkDetection;

/**
 * Created by Kush Dalal on 12/6/2017.
 */
@Disabled

@Autonomous(name = "RedCloseWorkingOp", group = "Autonomous")
public class RedCloseWorkingOp extends XLinearOpMode {

    OmniAutonomousMovement movement;
    MechanumAuton sensors;
    MechanumDrive mechanumDrive;
    GlyphClaw glyphClaw;
    VuMarkDetection vuMarkDetection;
    JewelColor jewelColor;
    LedAlwaysOn led;

    public void runOpMode() {
        // Do initialization.
        telemetry.addData("Stage", "Init");
        this.updateTelemetry(telemetry);

        led = new LedAlwaysOn(this);
        led.init();

        mechanumDrive = new MechanumDrive(this);
        mechanumDrive.init();

        sensors = new MechanumAuton(this);
        sensors.init();

        sensors.frontLeftMotor = mechanumDrive.frontLeft;
        sensors.frontRightMotor = mechanumDrive.frontRight;
        sensors.backLeftMotor = mechanumDrive.backLeft;
        sensors.backRightMotor = mechanumDrive.backRight;

        movement = new OmniAutonomousMovement(this, sensors, mechanumDrive);
        movement.init();

        jewelColor = new JewelColor(this);
        jewelColor.autonomousMovement = movement;
        jewelColor.init();
        jewelColor.raiseArmAuton();
        sleep(500);

        glyphClaw = new GlyphClaw(this);
        glyphClaw.init();

        vuMarkDetection = new VuMarkDetection(this);
        vuMarkDetection.init();

        // Initialize servo positions.
        glyphClaw.start();

        // Calibrate gyro.
        sensors.calibrateGyro();

        waitForStart(); // Wait for start to be pressed.
        telemetry.addData("Stage", "Start");
        this.updateTelemetry(telemetry);

        movement.start();
        sensors.start();
        mechanumDrive.start();
        glyphClaw.start();
        vuMarkDetection.start();
        led.start();

        // Get and store the vuMarkStatus
        sleep(1000);
        boolean isLeft = vuMarkDetection.isLeft();
        boolean isCenter = vuMarkDetection.isCenter();
        boolean isRight = vuMarkDetection.isRight();
        telemetry.addData("Left:", isLeft);
        telemetry.addData("Center:", isCenter);
        telemetry.addData("Right:", isRight);
        updateTelemetry(telemetry);
        sleep(1000);

        //New Glyph Off the top Mechanism
        glyphClaw.closeClaw();
        sleep(2000);

        //Get arm servo into correct position
       /*glyphClaw.startRaisingClaw();
        long startRaiseTime = System.currentTimeMillis();
        while ((System.currentTimeMillis()-startRaiseTime)<1200) {
            glyphClaw.raiseClaw();
            sleep(5);
        }
        glyphClaw.stopRaisingClaw();*/
        glyphClaw.raiseClaw();
        sleep(250);
        jewelColor.raiseArm();
        sleep(500);
        glyphClaw.lowerClaw();
        sleep(1200);
        glyphClaw.stopClaw();


        //Knock Jewels
        jewelColor.lowerArm();
        sleep(1000);
        jewelColor.colorEval();
        sleep(10);
        jewelColor.knockOffBlueGem();
        sleep(2000);
        jewelColor.raiseArm();
        sleep(1000);
        movement.pointTurnRight(6);


        //Vuforia Movement that defines where the robot goes to
        if(isLeft){
            movement.driveBackward(0.8, 86);
            sleep(1000);
        } else if (isCenter){
            movement.driveBackward(0.8, 70);
            sleep(1000);
        } else if (isRight){
            movement.driveBackward(0.8, 50);
            sleep(1000);
        } else {
            movement.driveBackward(0.8, 70);
            sleep(1000);
        }
        //try to fill the cryptobox
        movement.pointTurnLeft(85);
        sleep(500);
        movement.driveBackward(0.5, 15);
        sleep(250);
        glyphClaw.rotateClawDown();
        sleep(1500);
        movement.pointTurnLeft(7);
        sleep(250);
        glyphClaw.openClaw();
        sleep(1000);
        glyphClaw.rotateClawUp();
        sleep(1000);
        movement.driveBackward(0.8, 5);
        sleep(1000);
        movement.driveForward(0.8, 40);
        sleep(1000);
        movement.driveBackward(0.8, 5);
        sleep(1000);
        movement.stop();








        // Drive backward slightly to better grab the claw.
        /*

        // Drive forward to go back to the starting position.
        movement.driveBackward(0.2, 8);


        movement.driveBackward(0.8, 1);
        sleep(1000);
        movement.driveBackward(0.3, 3);
        sleep(1000);
        movement.driveBackward(0.2, 10);
        sleep(750);
        movement.driveBackward(0.4, 4);
        sleep(500);
        glyphClaw.rotateClawDown();
        sleep(500);
        glyphClaw.closeClaw();
        sleep(500);
        glyphClaw.raiseClaw();
        sleep(200);
        glyphClaw.stopClaw();
        sleep(200);
        movement.driveBackward(0.4, 45);
        sleep(750);
        movement.pointTurnLeft(90);
        sleep(750);
        glyphClaw.openClaw(); 
        sleep(500);
        glyphClaw.rotateClawUp();
        sleep(750);
        movement.driveBackward(0.4, 10);
        sleep(200);
        movement.stop();/*

        // Knock off the correct jewel.
       /* jewelColor.knockOffRedGem();
        sleep(1000);

        // Drive straight off the balancing stone.
        movement.driveBackward(0.4, 60);

        sleep(1000);
        */

        // Turn and face the cryptobox.


        // Do something based on the vuMarkStatus
        /*if (isLeft) {
            telemetry.addData("VuMark", "LEFT");
            movement.pointTurnLeft(90);
            sleep(200);
            movement.driveBackward(0.6, 15);
            sleep(200);
            movement.pointTurnRight(90);
            sleep(200);
            movement.driveBackward(0.6, 15);
        } else if (isCenter) {
            telemetry.addData("VuMark", "CENTER");
            movement.driveBackward(0.5, 20);
        } else if (isRight) {
            telemetry.addData("VuMark", "RIGHT");
            movement.pointTurnRight(90);
            sleep(200);
            movement.driveBackward(0.6, 15);
            sleep(200);
            movement.pointTurnLeft(90);
            sleep(200);
            movement.driveBackward(0.6, 15);
        } else {
            telemetry.addData("VuMark", "UNKNOWN");
        }
        this.updateTelemetry(telemetry);

        sleep(3000);*/

    }
}
