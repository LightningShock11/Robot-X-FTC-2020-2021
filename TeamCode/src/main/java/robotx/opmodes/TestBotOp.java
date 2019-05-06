package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.MecanumDriveGyroBased;
import robotx.modules.MechanumDriveNoLag;
import robotx.modules.TwoWheelAutonIMU;

@TeleOp(name = "TestBotOp", group = "Tests")
public class TestBotOp extends XOpMode {

    MecanumDriveGyroBased mecanumDriveGyroBased;
    TwoWheelAutonIMU twoWheelAutonIMU;

    public void initModules(){
        super.initModules();

        mecanumDriveGyroBased = new MecanumDriveGyroBased(this);
        activeModules.add(mecanumDriveGyroBased);

        twoWheelAutonIMU = new TwoWheelAutonIMU(this);
        activeModules.add(twoWheelAutonIMU);
    }
}
