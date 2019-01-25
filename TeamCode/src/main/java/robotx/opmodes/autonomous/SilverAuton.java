package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.AutonomousMovement;
import robotx.libraries.XLinearOpMode;
import robotx.modules.DumpingBucket;
import robotx.modules.LiftSystemXY;
import robotx.modules.MineralColor;
import robotx.modules.MineralColorV3;
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
    MineralColorV3 mineralColorV3;
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

        mineralColorV3 = new MineralColorV3(this);
        mineralColorV3.movement = movement;
        mineralColorV3.liftSystemXY = liftSystemXY;
        mineralColorV3.init();

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
        telemetry.addLine().addData("Gold Pos", mineralColorV3.position);
        this.updateTelemetry(telemetry);

        /////////////////////Movement///////////////////////


        liftSystemXY.extendY(1300);
        sleep(2000);
        movement.pointTurnRight(50);
        sleep(1500);
        liftSystemXY.retractY();
        sleep(2000);
        stopDriving();

        //-----------Dehanging complete-----------\\

        goForward(0.4, 1880);
        sleep(250);
        movement.pointTurnRight(26);
        sleep(1000);
        goBackward(0.6, 440);
        sleep(1000);
        mineralColorV3.DetectGold3();
        sleep(1000);
        if(mineralColorV3.isThirdWhite){
            goBackward(0.6, 900);
            sleep(500);
            mineralColorV3.DetectGold2();
            if(mineralColorV3.isSecondWhite){
                goBackward(0.6, 900);
                sleep(500);
                movement.pointTurnRight(340);
                sleep(250);
            }
            else if(mineralColorV3.isSecondGold){
                movement.pointTurnRight(340);
                sleep(500);
                goBackward(1.0, 600);
            }
        }
        else if(mineralColorV3.isThirdGold){
            movement.pointTurnRight(340);
            sleep(500);
            goBackward(1.0, 1500);
        }
        sleep(10000);
        goBackward(1.0, 500);
        sleep(1000);
        movement.pointTurnLeft(40);
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
