package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.*;

import java.text.DecimalFormat;

import robotx.libraries.*;

/**
 * Created by Nicholas on 11/15/16.
 * Displays a configurable timer in the telemetry output
 * for TeleOp and End Game periods.
 * Must be an active Module.
 */
public class TeleOpTimerDisplay extends XModule {

	StopWatch stopWatch;

	// Configuration options:

	public boolean displayTimeTilEndGame = false;
	public boolean displayElapsedTime = false;
	public boolean displayGamePeriod = false; // i.e. "TeleOp" or "End Game"
	public boolean displayTimeUntilEnd = true;

	boolean autoStopOpMode = false;

	public TeleOpTimerDisplay(OpMode op) {
		super(op);
	}

	public void init() {
		stopWatch = new StopWatch();
	}

	public void start() {
		stopWatch.startTimer(0); // Don't use timer feature of StopWatch.
	}

	public void loop() {

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
			} else { // Current period is End Game.
				opMode.telemetry.addData("Period", "End Game");
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
