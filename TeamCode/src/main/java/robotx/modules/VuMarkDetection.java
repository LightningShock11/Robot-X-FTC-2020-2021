package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.*;
import org.firstinspires.ftc.robotcore.external.navigation.*;

import robotx.libraries.XModule;

/**
 * Created by Robot-X Team Member on 11/20/2017.
 */

public class VuMarkDetection extends XModule {

    public static final String LICENSE_KEY = "AaxLsq//////AAAAGSFJk9eNu0C8p0756xps11ldkjgwInDdO4QlKRR9BSShfQIuQCd+iY+mtl0OiOZijZ3JUC5gol2gSSFwYL8bwcUn9oSe0eNSb17rCJ5ghlAtV/pka0KwhzNTEM9oeFExK91ohikwtUb4+naf+zo1hHHvKwC4kjwfeoUF+V6572vyN94r2n/KYEnOGS6rXjm5p8RMdtYu/EZN6WsuHpqlkp7doJr1sc2xwKWKBDfesFX9JF24zj920rjtC6TGeUt+kH22MjsXGInRxqibbuRatmZKQQFRfEFPeWbqeh0MCd3fTbkdX2rsXTT+wF4NJFhC39+Dzg6Zxb9VOJd5/IRDVb67e6XwRu129cHtkuKL6aM8";

    VuforiaLocalizer vuforia;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;

    public VuMarkDetection (OpMode op){
        super(op);
    }

    @Override
    public void init() {
        // Vuforia setup code
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // OR...  Do Not Activate the Camera Monitor View, to save power
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = LICENSE_KEY;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
    }
    public void start() {
        // Start looking for relic pictures.
        relicTrackables.activate();
    }

    public RelicRecoveryVuMark getVuMarkStatus() {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        return vuMark;
    }

    public boolean isLeft() {
        return getVuMarkStatus() == RelicRecoveryVuMark.LEFT;
    }
    public boolean isCenter() {
        return getVuMarkStatus() == RelicRecoveryVuMark.CENTER;
    }
    public boolean isRight() {
        return getVuMarkStatus() == RelicRecoveryVuMark.RIGHT;
    }
    public boolean isUnknown() {
        return getVuMarkStatus() == RelicRecoveryVuMark.UNKNOWN;
    }


}
