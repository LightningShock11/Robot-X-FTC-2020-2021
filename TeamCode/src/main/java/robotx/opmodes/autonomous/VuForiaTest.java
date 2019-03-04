package robotx.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptTensorFlowObjectDetection;

import robotx.libraries.VuForiaObjectDetection;
import robotx.libraries.XLinearOpMode;

@Disabled
@Autonomous(name = "VuForiaTest", group = "Autonomous")
public class VuForiaTest extends XLinearOpMode {
    VuForiaObjectDetection vuForiaObjectDetection;

    public void runOpMode(){
    }
}
