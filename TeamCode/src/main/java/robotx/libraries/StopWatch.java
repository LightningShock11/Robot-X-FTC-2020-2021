package robotx.libraries;

import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 10/29/2015.
 */
public class StopWatch {

	private long tStart;
	private long target;
	public int resetCounter;

	public StopWatch() {
		tStart = 0;
		resetCounter = 0;
		target = 0;
	}

	public void reset() {
		tStart = System.nanoTime();
		resetCounter++;
	}

	public long elapsedNanos() {
		return (System.nanoTime() - tStart);
	}

	public long elapsedMillis() {
		return TimeUnit.NANOSECONDS.toMillis( elapsedNanos() );
	}

	public void startTimer(long millis) {
		reset();
		target = millis;
	}

	public boolean timerDone() {
		return (elapsedMillis() >= target);
	}
}
