package robotx.opmodes.OldOpModes.OldAutons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import robotx.libraries.OmniAutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.OldModules.GlyphClaw;
import robotx.modules.OldModules.JewelColor;
import robotx.modules.OldModules.MechanumAuton;
import robotx.modules.OldModules.MechanumDrive;
import robotx.modules.VuMarkDetection;

/**
 * Created by Kush Dalal on 12/6/2017.
 */
@Disabled
@Autonomous(name = "BlueCloseNOTWorkingOp", group = "Autonomous")
public class BlueCloseWorkingOp extends XLinearOpMode {

    OmniAutonomousMovement movement;
    MechanumAuton sensors;
    MechanumDrive mechanumDrive;
    GlyphClaw glyphClaw;
    VuMarkDetection vuMarkDetection;
    JewelColor jewelColor;

    public void runOpMode() {
        // Do initialization.
        telemetry.addData("Stage", "Init");
        this.updateTelemetry(telemetry);

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
        glyphClaw.closeClaw();
        sleep(1500);
        glyphClaw.raiseClaw();
        sleep(750);
        jewelColor.raiseArm();
        sleep(250);
        glyphClaw.lowerClaw();
        sleep(750);

        vuMarkDetection = new VuMarkDetection(this);
        vuMarkDetection.init();

        // Initialize servo positions.
        glyphClaw.start();
        jewelColor.start();

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
        jewelColor.start();

        // Get and store the vuMarkStatus
        boolean isLeft = vuMarkDetection.isLeft();
        boolean isCenter = vuMarkDetection.isCenter();
        boolean isRight = vuMarkDetection.isRight();
        telemetry.addData("Left:", isLeft);
        telemetry.addData("Center:", isCenter);
        telemetry.addData("Right:", isRight);

        //New Glyph Off the top Mechanism
        glyphClaw.closeClaw();
        sleep(2000);

        //Knock Jewels
        jewelColor.lowerArm();
        sleep(1000);
        jewelColor.colorEval();
        jewelColor.knockOffRedGem();
        sleep(4000);
        jewelColor.raiseArm();
        sleep(2000);

        //Vuforia Movement that defines where the robot goes to
        if(isLeft){
            movement.driveForward(0.8, 70);
            sleep(1000);
        } else if (isCenter){
            movement.driveForward(0.8, 90);
            sleep(1000);
        } else if (isRight){
            movement.driveForward(0.8, 110);
            sleep(1000);
        } else {
            movement.driveForward(0.8, 90);
            sleep(2000);
        }
        //try to fill the cryptobox
        movement.pointTurnLeft(90);
        sleep(500);
        glyphClaw.rotateClawDown();
        sleep(1000);
        glyphClaw.openClaw();
        sleep(1000);
        glyphClaw.rotateClawUp();
        sleep(1000);
        movement.driveBackward(0.8,5);
        sleep(1000);
        movement.driveForward(0.8, 10);
        sleep(1000);
        movement.driveBackward(0.8, 5);
        sleep(1000);
        movement.stop();

    }
}
