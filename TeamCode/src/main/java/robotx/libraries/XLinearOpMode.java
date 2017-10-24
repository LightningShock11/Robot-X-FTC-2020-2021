package robotx.libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Nicholas on 1/25/2017.
 */
public abstract class XLinearOpMode extends LinearOpMode {

    public boolean stopping() {
        return isStopRequested() || Thread.currentThread().isInterrupted() || !opModeIsActive();
    }

    public void safeWaitMillis(long millisecondsToWait) {
        long startWaitTime = System.currentTimeMillis();
        while ( (System.currentTimeMillis()-startWaitTime)<millisecondsToWait && !stopping() ) {

        }
    }

}
