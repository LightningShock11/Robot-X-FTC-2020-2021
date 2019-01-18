package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.DumpingBucket;
import robotx.modules.LiftSystemXY;
import robotx.modules.MineralColor;
import robotx.modules.MineralColorV2;
import robotx.modules.TwoMotorDrive;
import robotx.modules.TwoWheelAutonIMU;
import robotx.modules.XSweeper;

/**
 * Created by Kush Dalal on 10/24/2018.
 */

@Autonomous(name = "SilverAuton", group = "Autonomous")
public class SilverAuton extends XLinearOpMode {

    AutonomousMovement movement;
    TwoWheelAutonIMU sensors;
    TwoMotorDrive twoMotorDrive;
    LiftSystemXY liftSystemXY;
    MineralColorV2 mineralColorV2;
    DumpingBucket dumpingBucket;
    XSweeper xSweeper;



    public void runOpMode() {
        //////////////////////init//////////////////////////

        //Initialize all systems on the robot. ALL SYSTEMS GO
        telemetry.addData("Stage", "Init: Silver Auton");
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

        mineralColorV2 = new MineralColorV2(this);
        mineralColorV2.movement = movement;
        mineralColorV2.liftSystemXY = liftSystemXY;
        mineralColorV2.init();

        xSweeper = new XSweeper(this);
        xSweeper.init();

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


        liftSystemXY.extendY(1300);
        sleep(150);
        movement.pointTurnRight(30);
        sleep(1500);
        goForward(1.0, 250);
        sleep(1500);
        movement.pointTurnLeft(30);
        sleep(550);
        stopDriving();

        //-----------Dehanging complete-----------\\

        liftSystemXY.extendX(1325);
        sleep(1000);
        goForward(1.0, 500);
        sleep(1000);
        mineralColorV2.DetectGold();
        sleep(1000);
        liftSystemXY.retractX();
        goForward(1.0, 700);
        movement.pointTurnRight(90);
        goBackward(1.0, 1900);
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


        twoMotorDrive.rightMotor.setPower(-power);
        twoMotorDrive.leftMotor.setPower(-power);
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
