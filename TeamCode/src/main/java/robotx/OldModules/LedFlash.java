package robotx.OldModules;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.libraries.XModule;

/**
 * Created by Ben Sabo on 1/19/2018.
 */

public class LedFlash extends XModule {
    DcMotor blueLed;
    boolean ledOn;
    boolean ledOff;
    boolean flashing;
    boolean fading;

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public LedFlash(OpMode op){super(op);}


    public void init(){
        blueLed = opMode.hardwareMap.dcMotor.get("blueLed");
        ledOff = true;
        ledOn = false;
        fading = false;
        flashing = false;
    }

    public void ledOn(){
        blueLed.setPower(1.0);
    }
    public void ledOff(){
        blueLed.setPower(0.0);
    }
    public void decreaseLed() {
            double ledPowerDown = 1.0;
            ledPowerDown -= 0.1;
            blueLed.setPower(ledPowerDown);
            if (ledPowerDown <= 0) {
                increaseLed();
            }
        }

    public void increaseLed() {
        double ledPowerUp = 0.0;
        ledPowerUp += 0.1;
        blueLed.setPower(ledPowerUp);
        if (ledPowerUp >= 1) {
            decreaseLed();
        }
    }



    public void toggleLed(){
        if (ledOff){
            ledOn();
            ledOn = true;
            ledOff = false;
            opMode.telemetry.addLine("Led on");
        }
        else{
            ledOff();
            ledOff = true;
            ledOn = false;
            opMode.telemetry.addLine("Led off");
        }
    }

    int state = 0; // 0 means nothing, 1 means solid, 2 means flash, 3 means fade
    long timeOfLastLightChange = 0;

    public void loop(){
        //Process controls and change state if necessary.
        if (xGamepad1().y.wasPressed()){
            state = 2;
        }
        if (xGamepad1().b.wasPressed()){
            state = 0;
        }
        if (xGamepad1().x.wasPressed()){
            state = 1;
        }
        if (xGamepad1().a.wasPressed()){
            state = 3;
        }
        //Run lights according to current state.
        if (System.currentTimeMillis() - timeOfLastLightChange > 500 && state == 2) {
            toggleLed();
            timeOfLastLightChange = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - timeOfLastLightChange > 500 && state == 1){
            ledOn();
            timeOfLastLightChange = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - timeOfLastLightChange > 500 && state == 0){
            ledOff();
            timeOfLastLightChange = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - timeOfLastLightChange > 500 && state == 3){
            decreaseLed();
        }
    }
}
