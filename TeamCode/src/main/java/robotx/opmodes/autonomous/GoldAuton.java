package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.LiftSystemXY;
import robotx.modules.TwoMotorDrive;
import robotx.modules.TwoWheelAutonIMU;

/**
 * Created by Kush Dalal on 10/24/2018.
 */

@Autonomous(name = "GoldAuton", group = "Autonomous")
public class GoldAuton extends XLinearOpMode {

    AutonomousMovement movement;
    TwoWheelAutonIMU sensors;
    TwoMotorDrive twoMotorDrive;
    LiftSystemXY liftSystemXY;


    public void runOpMode() {
        //////////////////////init//////////////////////////

        //Initialize all systems on the robot. ALL SYSTEMS GO
        telemetry.addData("Stage", "Init: Gold Auton");
        this.updateTelemetry(telemetry);

        twoMotorDrive = new TwoMotorDrive(this);
        twoMotorDrive.init();

        sensors = new TwoWheelAutonIMU(this);
        sensors.init();

        liftSystemXY = new LiftSystemXY(this);
        liftSystemXY.init();

        movement = new AutonomousMovement(this, sensors, twoMotorDrive);
        movement.init();

        sensors.leftMotor = twoMotorDrive.leftMotor;
        sensors.rightMotor = twoMotorDrive.rightMotor;


        /////////////////////Start//////////////////////////

        //Start all systems.
        waitForStart(); // Wait for start to be pressed.
        telemetry.addData("Stage", "Start");
        telemetry.addLine().addData("Heading", sensors.getHeadingAngle());
        this.updateTelemetry(telemetry);

        liftSystemXY.start();
        movement.start();
        sensors.start();
        twoMotorDrive.start();
        twoMotorDrive.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        twoMotorDrive.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        /////////////////////Movement///////////////////////

        //movement test
        // movement.driveForward(3.0, 10.0);
        liftSystemXY.yMotor(1.0);
        sleep(900);
        liftSystemXY.yMotor(0.0);
        liftSystemXY.stop();
        sleep(150);
        movement.pointTurnLeft(45);
        sleep(150);
        goForward(1.0, 2000);
        movement.pointTurnLeft(90);
        sleep(500);
        goForward(1.0, 2000);
        stopDriving();
        twoMotorDrive.stop();
        movement.stop();



        ////////////////////////////////////////////////////
    }


    /////////////////////Controls///////////////////////

    public void goForward(double power, int time){

        twoMotorDrive.leftMotor.setPower(power);
        twoMotorDrive.rightMotor.setPower(power);
        sleep(time);
    }
    public void goBackward(double power, int time){

        twoMotorDrive.leftMotor.setPower(-power);
        twoMotorDrive.rightMotor.setPower(-power);
        sleep(time);

    }

    public  void stopDriving (){
        twoMotorDrive.brakeAllMotors();
    }

}
