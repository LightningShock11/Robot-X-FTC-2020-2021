package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.DumpingBucket;
import robotx.modules.LiftSystemXY;
import robotx.modules.TwoMotorDrive;
import robotx.modules.MineralColor;
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
    MineralColor mineralColor;
    DumpingBucket dumpingBucket;


    public void runOpMode() {
        //////////////////////init//////////////////////////

        //Initialize all systems on the robot. ALL SYSTEMS GO
        telemetry.addData("Stage", "Init: Gold Auton");
        this.updateTelemetry(telemetry);

        twoMotorDrive = new TwoMotorDrive(this);
        twoMotorDrive.init();

        dumpingBucket = new DumpingBucket(this);
        dumpingBucket.init();

        sensors = new TwoWheelAutonIMU(this);
        sensors.init();

        liftSystemXY = new LiftSystemXY(this);
        liftSystemXY.init();

        movement = new AutonomousMovement(this, sensors, twoMotorDrive);
        movement.init();

        mineralColor = new MineralColor(this);
        mineralColor.movement = movement;
        mineralColor.liftSystemXY = liftSystemXY;
        mineralColor.init();

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

        liftSystemXY.yMotor(1.0);
        sleep(1150);
        liftSystemXY.yMotor(0.0);
        sleep(150);
        movement.pointTurnRight(30);
        sleep(100);
        goForward(1.0, 750);
        sleep(100);
        movement.pointTurnLeft(30);
        sleep(250);

        //-----------Dehanging complete-----------\\

        liftSystemXY.extendX(1325);
        mineralColor.DetectGold();
        sleep(100);
        mineralColor.knockMineral();
        liftSystemXY.retractX();
        goForward(1.0, 700);
        movement.pointTurnRight(90);
        goBackward(1.0, 900);
        dumpingBucket.autoDump();
        sleep(100);
        goForward(1.0, 2000);
        stopDriving();
        twoMotorDrive.stop();
        movement.stop();



        ////////////////////////////////////////////////////
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
