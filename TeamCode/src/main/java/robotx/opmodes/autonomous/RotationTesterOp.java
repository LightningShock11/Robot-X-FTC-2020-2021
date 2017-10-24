package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;
import robotx.modules.*;

/**
 * Created by Nicholas on 2/1/2017.
 */
@Autonomous(name = "RotationTesterOp", group = "Testing")
public class RotationTesterOp extends XLinearOpMode {

    TwoMotorDrive tileRunnerDrive;
    TileRunnerAuton sensors;
    AutonomousMovement movement;

    public void runOpMode() {
        // Do initialization.
        telemetry.addData("Stage", "Init");
        this.updateTelemetry(telemetry);
        tileRunnerDrive = new TwoMotorDrive(this);
        tileRunnerDrive.init();

        sensors = new TileRunnerAuton(this);
        sensors.init();

        tileRunnerDrive.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tileRunnerDrive.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sensors.leftMotor = tileRunnerDrive.leftMotor;
        sensors.rightMotor = tileRunnerDrive.rightMotor;

        movement = new AutonomousMovement(this, sensors, tileRunnerDrive);
        movement.init();

        // Calibrate gyro.
        sensors.calibrateGyro();

        waitForStart(); // Wait for start to be pressed.
        telemetry.addData("Stage", "Start");
        this.updateTelemetry(telemetry);

        tileRunnerDrive.start();

        /*for (int i=0; i<40; i++) {
            telemetry.addData("Stage", "Left Turn " + i + " / 40");
            movement.pointTurnLeft(90);
            long startWaitingTime = System.currentTimeMillis();
            while ( (System.currentTimeMillis()-startWaitingTime)<2000 && !stopping() ) {

            }
        }*/

        for (int i=0; i<40; i++) {
            telemetry.addData("Stage", "Right Turn " + i + " / 40");
            movement.pointTurnRight(90);
            long startWaitingTime = System.currentTimeMillis();
            while ( (System.currentTimeMillis()-startWaitingTime)<2000 && !stopping() ) {

            }
        }

    }

}
