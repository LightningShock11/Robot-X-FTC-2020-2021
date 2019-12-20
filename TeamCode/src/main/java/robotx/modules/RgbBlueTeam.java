package robotx.modules;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import java.text.DecimalFormat;

import robotx.libraries.StopWatch;
import robotx.libraries.XModule;

public class RgbSignals extends XModule {
    RevBlinkinLedDriver rgbLights;
    RevBlinkinLedDriver.BlinkinPattern pattern;
    StopWatch stopWatch;

    public boolean displayTimeTilEndGame = false;
    public boolean displayElapsedTime = true;
    public boolean displayGamePeriod = true; // i.e. "TeleOp" or "End Game"
    public boolean displayTimeUntilEnd = false;

    boolean autoStopOpMode = false;

    public RgbSignals(OpMode op){super(op);}

    public void init(){
        rgbLights = opMode.hardwareMap.get(RevBlinkinLedDriver.class, "rgbLights");
        pattern = RevBlinkinLedDriver.BlinkinPattern.SKY_BLUE;
        rgbLights.setPattern(pattern); //Set color to sky blue
        stopWatch = new StopWatch();
    }
    public void start(){
        pattern = RevBlinkinLedDriver.BlinkinPattern.YELLOW;
        rgbLights.setPattern(pattern); //Set color to yellow
        stopWatch.startTimer(0);
    }

    public void loop(){
        if (displayTimeTilEndGame) {
            if (stopWatch.elapsedMillis() < 90_000) { // Current period is TeleOp.
                opMode.telemetry.addData("Time until End Game", formatTime(90_000 - stopWatch.elapsedMillis()));
            } else { // Current period is End Game.
                opMode.telemetry.addData("Time until End Game", "END GAME!" );
            }
        }
        if (displayElapsedTime) {
            opMode.telemetry.addData("Elapsed Time", formatTime(stopWatch.elapsedMillis()) );
        }

        if (displayGamePeriod) {
            if (stopWatch.elapsedMillis() < 90_000) { // Current period is TeleOp.
                opMode.telemetry.addData("Period", "TeleOp");
                pattern = RevBlinkinLedDriver.BlinkinPattern.YELLOW;
                rgbLights.setPattern(pattern); //Keeps color at yellow for TeleOp
            } else { // Current period is End Game.
                opMode.telemetry.addData("Period", "End Game");
                pattern = RevBlinkinLedDriver.BlinkinPattern.RED;
                rgbLights.setPattern(pattern); //Sets color to red for endgame
            }
        }

        if (displayTimeUntilEnd) {
            opMode.telemetry.addData("Elapsed Time", formatTime(120_000 - stopWatch.elapsedMillis()) );
        }

        if (autoStopOpMode) {
            if (stopWatch.elapsedMillis() > 120_100) {
                opMode.requestOpModeStop();
            }
        }
    }
    private String formatTime(long timeInMilliseconds) { //Returns time in seconds, i.e. 5.7s
        DecimalFormat dFormat = new DecimalFormat("0.0");
        double seconds = timeInMilliseconds / 1000.0;
        String formattedString = dFormat.format(seconds);

        return formattedString;
    }
}
