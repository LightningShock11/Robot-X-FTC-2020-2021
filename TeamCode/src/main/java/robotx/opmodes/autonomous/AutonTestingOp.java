package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Robot-X Team Member on 11/30/2017.
 */
@Autonomous(name = "AutonTestingOp", group = "Autonomous")
public class AutonTestingOp extends XLinearOpMode {

    OmniAutonomousMovement movement;
    MechanumAuton sensors;
    MechanumDrive mechanumDrive;
    GlyphClaw glyphClaw;
    VuMarkDetection vuMarkDetection;

    public void runOpMode() {
        // Do initialization.
        telemetry.addData("Stage", "Init");
        this.updateTelemetry(telemetry);

        mechanumDrive = new MechanumDrive(this);
        mechanumDrive.init();

        sensors = new MechanumAuton(this);
        sensors.init();

        mechanumDrive.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mechanumDrive.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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


        ///

        mechanumDrive.setYPower(1.0);
        sleep(800);
        mechanumDrive.brakeAllMotors();
        sleep(2000);

        mechanumDrive.setYPower(-1.0);
        sleep(800);
        mechanumDrive.brakeAllMotors();
        sleep(2000);

        movement.driveForward(0.5, 50);
        sleep(2000);

        movement.driveBackward(0.5, 50);
        sleep(2000);

    }

}
