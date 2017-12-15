package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import robotx.libraries.OmniAutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.GlyphClaw;
import robotx.modules.JewelColor;
import robotx.modules.MechanumAuton;
import robotx.modules.MechanumDrive;
import robotx.modules.VuMarkDetection;

/**
 * Created by Kush Dalal on 12/6/2017.
 */
@Autonomous(name = "BlueCloseTestingOp", group = "Autonomous")
public class BlueCloseTestingOp extends XLinearOpMode {

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

        movement = new OmniAutonomousMovement(this, sensors, mechanumDrive);
        movement.init();

        jewelColor = new JewelColor(this);
        jewelColor.autonomousMovement = movement;
        jewelColor.init();

        glyphClaw = new GlyphClaw(this);
        glyphClaw.init();

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


        ///

        // Get and store the vuMarkStatus
        boolean isLeft = vuMarkDetection.isLeft();
        boolean isCenter = vuMarkDetection.isCenter();
        boolean isRight = vuMarkDetection.isRight();
        movement.driveForward(0.4, 40);
        sleep(1000);
        movement.driveForward(0.4, 60);
        sleep(750);
        glyphClaw.rotateClawDown();
        sleep(500);
        glyphClaw.closeClaw();
        sleep(500);
        movement.driveForward(0.4, 120);
        sleep(750);



        // Knock off the correct jewel.
       /* jewelColor.knockOffRedGem();
        sleep(1000);

        // Drive straight off the balancing stone.
        movement.driveForward(0.4, 60);

        sleep(1000);
        */

        // Turn and face the cryptobox.
        movement.pointTurnLeft(-90);

        // Do something based on the vuMarkStatus
        if (isLeft) {
            telemetry.addData("VuMark", "LEFT");
            //movement.pointTurnLeft(45);
        } else if (isCenter) {
            telemetry.addData("VuMark", "CENTER");
            //movement.driveBackward(0.5, 20);
        } else if (isRight) {
            telemetry.addData("VuMark", "RIGHT");
            //movement.pointTurnRight(45);
        } else {
            telemetry.addData("VuMark", "UNKNOWN");
        }
        this.updateTelemetry(telemetry);

        sleep(8000);

    }
}
