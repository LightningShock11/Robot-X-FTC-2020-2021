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
@Autonomous(name = "BlueFarWorkingOp", group = "Autonomous")
public class BlueFarWorkingOp extends XLinearOpMode {

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
        jewelColor.knockOffBlueGem();
        sleep(2000);
        jewelColor.raiseArm();
        sleep(1000);


        //Vuforia Movement that defines where the robot goes to
        if(isLeft){
            movement.driveForward(0.8, 40);
            sleep(1000);
        } else if (isCenter){
            movement.driveForward(0.8, 40);
            sleep(1000);
        } else if (isRight){
            movement.driveForward(0.8, 40);
            sleep(1000);
        } else {
            movement.driveForward(1, 40);
            sleep(1000);
        }
        //try to fill the cryptobox
        movement.pointTurnRight(10);
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
