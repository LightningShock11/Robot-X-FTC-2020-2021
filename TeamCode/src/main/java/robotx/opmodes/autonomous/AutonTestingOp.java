package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Robot-X Team Member on 11/30/2017.
 */
@Autonomous(name = "AutonTestingOp", group = "Testing")
public class AutonTestingOp extends XLinearOpMode {

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

        // Test out movement.
        sleep(1000);
        movement.driveForward(0.4, 100);
        sleep(1000);
        movement.pointTurnRight(180);
        sleep(1000);
        movement.driveForward(0.8, 100);
        sleep(1000);
        movement.pointTurnLeft(90);
        sleep(1000);

        // Test glyph claw and lift.
        glyphClaw.openClaw();
        sleep(1000);
        glyphClaw.closeClaw();
        sleep(1000);
        glyphClaw.rotateClawDown();
        sleep(1000);
        glyphClaw.raiseClaw();
        sleep(300);
        glyphClaw.lowerClaw();
        sleep(250);
        glyphClaw.stopClaw();
        sleep(1000);
        glyphClaw.rotateClawUp();
        sleep(1000);

        // Test jewel color arm.
        jewelColor.lowerArm();
        sleep(1000);
        jewelColor.raiseArm();
        sleep(1000);

    }
}
