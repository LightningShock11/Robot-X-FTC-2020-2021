package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import robotx.libraries.*;

/**
 * Created and written by Kush & Ben on 1/28/17.
 */
@TeleOp(name = "BatteryKillerOp", group = "Default")
public class BatteryKillerOp extends XLinearOpMode {

    DcMotor killerMotor;

    public void runOpMode() {
            killerMotor.setPower(1.0);
            while ((System.currentTimeMillis() < 5000)) {

            }
            killerMotor.setPower(-1.0);
            while ((System.currentTimeMillis() < 5000)) {
            }
    }
}








