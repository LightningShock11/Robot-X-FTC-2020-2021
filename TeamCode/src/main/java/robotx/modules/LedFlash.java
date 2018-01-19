package robotx.modules;

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
    }

    public void toggleLed(){
        if (ledOff){
            blueLed.setPower(1.0);
            ledOn = true;
            ledOff = false;
        }
        else{
            blueLed.setPower(0.0);
            ledOff = true;
            ledOn = false;
        }
    }

    public void flash(){
            blueLed.setPower(1.0);
            sleep(500);
            blueLed.setPower(0.0);
            ledOn = true;
        }
    public void fade(){
        blueLed.setPower(1.0);
        for (int x = 1; x > 0; x-= 0.1){
            blueLed.setPower(x);
        }
        blueLed.setPower(0.0);
        ledOn = true;
    }

    public void loop(){
        if (xGamepad1().b.wasPressed()){
            toggleLed();
        }
        if (xGamepad1().x.wasPressed()){
            flash();
        }
        if (xGamepad1().y.wasPressed()){
            fade();
        }
    }
}
