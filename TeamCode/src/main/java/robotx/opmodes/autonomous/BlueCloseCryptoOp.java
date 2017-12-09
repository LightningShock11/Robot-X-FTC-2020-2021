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
 * Created by Robot-X Team Member on 12/6/2017.
 */
@Autonomous(name = "BlueCloseCryptoOp", group = "Autonomous")
public class BlueCloseCryptoOp extends XLinearOpMode {
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

        jewelColor = new JewelColor(this);
        jewelColor.init();

        mechanumDrive = new MechanumDrive(this);
        mechanumDrive.init();

        sensors = new MechanumAuton(this);
        sensors.init();

        /*mechanumDrive.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mechanumDrive.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
        sensors.frontLeftMotor = mechanumDrive.frontLeft;
        sensors.frontRightMotor = mechanumDrive.frontRight;

        movement = new OmniAutonomousMovement(this, sensors, mechanumDrive);
        movement.init();

        glyphClaw = new GlyphClaw(this);
        glyphClaw.init();

        vuMarkDetection = new VuMarkDetection(this);
        vuMarkDetection.init();

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

        // Knock off the correct jewel.
        jewelColor.lowerArm();
        sleep(500);
        jewelColor.colorEval();
        sleep(500);
        jewelColor.raiseArm();
        sleep(2000);

        // Drive straight off the balancing stone.
        movement.driveForward(0.4, 60);

        sleep(1000);

        // Turn and face the cryptobox.
        movement.pointTurnLeft(90);

        // Do something based on the vuMarkStatus
        if (isLeft) {
            telemetry.addData("VuMark", "LEFT");
        } else if (isCenter) {
            telemetry.addData("VuMark", "CENTER");
        } else if (isRight) {
            telemetry.addData("VuMark", "RIGHT");
        } else {
            telemetry.addData("VuMark", "UNKNOWN");
        }
        this.updateTelemetry(telemetry);

        sleep(1000);

    }
}
