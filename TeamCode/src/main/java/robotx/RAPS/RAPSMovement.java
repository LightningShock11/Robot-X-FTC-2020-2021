package robotx.RAPS;

public class RAPSMovement {
    public static void goToPos(double x, double y, double moveSpeed){
        double absoluteAngleToTarget = Math.atan2(y-worldYpos, x-worldXpos);
        double relativeAngleToTarget = absoluteAngleToTarget - worldAngle;
    }
}
