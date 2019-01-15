package robotx.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.libraries.XOpMode;
import robotx.modules.*;

/**
 * Created by Robot-X Team Member on 11/20/2017.
 */
/*@TeleOp (name = "VuforiaTestOp", group = "Tests")
public class VuforiaTestOp extends XOpMode {
    VuMarkDetection vuMarkDetection;

    public void initModules(){
        super.initModules();

        vuMarkDetection = new VuMarkDetection(this);
        activeModules.add(vuMarkDetection);
    }
    public void init(){
        super.init();
    }

    public void loop() {
        if (vuMarkDetection.isLeft()) {
            telemetry.addData("VuMark", "Left");
        } else if (vuMarkDetection.isCenter()) {
            telemetry.addData("VuMark", "Center");
        } else if (vuMarkDetection.isRight()) {
            telemetry.addData("VuMark", "Right");
        } else {
            telemetry.addData("VuMark", "Unkown");
        }
    }
}
*/