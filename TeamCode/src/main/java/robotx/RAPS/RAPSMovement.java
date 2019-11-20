package robotx.RAPS;
import com.qualcomm.robotcore.util.Range;

import static robotx.RAPS.MathFunctions.*;


public class RAPSMovement {
    public static void goToPos(double x, double y, double moveSpeed, double preferredAngle, double turnSpeed){
        //////Placeholder Variables//////
            double worldXpos = 0;
            double worldYpos = 0;
            double worldAngle = 0;

        /////////////////////////////////

        double distanceToTarget = Math.hypot(x-worldXpos,y-worldYpos);

        double absoluteAngleToTarget = Math.atan2(y-worldYpos, x-worldXpos);
        double relativeAngleToTarget = angleWrap(absoluteAngleToTarget - worldAngle);

        double relativeXToPoint = Math.cos(relativeAngleToTarget)*distanceToTarget;
        double relativeYtoPoint = Math.sin(relativeAngleToTarget)*distanceToTarget;

        double movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));
        double movementYPower = relativeYtoPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));

        //put x motor power variable here = movementXPower * moveSpeed;
        //put y motor power variable here = movementYPower * moveSpeed;

        double relativeTurnAngle = relativeAngleToTarget - Math.toRadians(180) + preferredAngle;
        // put movement turning variable here = Range.clip(relativeTurnAngle/Math.toRadians(30),-1, 1) * turnSpeed;
    }
}
