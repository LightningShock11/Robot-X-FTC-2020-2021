package robotx.RAPS;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.opencv.core.Point;

import java.util.ArrayList;

import robotx.modules.RAPS;

import static robotx.RAPS.MathFunctions.*;


public class RAPSMovement {

    public static void followCurve(ArrayList<CurvePoint> allPoints, double followAngle){
        /*for(int i = 0; i < allPoints.size() - 1; i++){
            CurvePoint
        }*/

        CurvePoint followMe = getFollowPointPath(allPoints, new Point(RAPSOpMode.worldXpos, RAPSOpMode.worldYpos),allPoints.get(0).followDistance);

        //goToPos(followMe.x, followMe.y, followMe.moveSpeed, followAngle, followMe.turnSpeed);

    }

    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotLocation, double followRadius){
        CurvePoint followMe = new CurvePoint(pathPoints.get(0));

        for(int i = 0; i < pathPoints.size() - 1; i++){
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endline = pathPoints.get(i + 1);

            ArrayList<Point> intersections = lineCircleIntersection(robotLocation,followRadius,
                    startLine.toPoint(), endline.toPoint());

            double closestAngle = 10000000;

            for(Point thisIntersection : intersections){
                double angle = Math.atan2(thisIntersection.y - RAPSOpMode.worldYpos, thisIntersection.x - RAPSOpMode.worldXpos);
                double deltaAngle = Math.abs(MathFunctions.angleWrap(angle - RAPSOpMode.worldAngle));

                if(deltaAngle < closestAngle){
                    closestAngle = deltaAngle;
                    followMe.setPoint(thisIntersection);
                }
            }
        }
        return followMe;
    }





    public static double xMotorPower;
    public static double yMotorPower;
    public static double rotationPower = 0;

    public static double movementXPower = 0;
    public static double movementYPower = 0;





    public static void goToPos(double x, double y, double moveSpeed, double goalAngle, double turnSpeed){

        double distanceToTarget = Math.hypot(x - RAPSOpMode.worldXpos, y - RAPSOpMode.worldYpos);

        double absoluteAngleToTarget = goalAngle;
        //double absoluteAngleToTarget = Math.atan2(y - RAPSOpMode.worldYpos, x - RAPSOpMode.worldXpos);

        double relativeAngleToTarget = angleWrap(absoluteAngleToTarget - RAPSOpMode.worldAngle);
        //double relativeAngleToTarget = angleWrap(absoluteAngleToTarget - RAPSOpMode.worldAngle);

        double relativeXToPoint = Math.cos(relativeAngleToTarget)*distanceToTarget;
        double relativeYtoPoint = Math.sin(relativeAngleToTarget)*distanceToTarget;

        movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));
        movementYPower = relativeYtoPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));

        xMotorPower = movementXPower * moveSpeed;
        yMotorPower = movementYPower * moveSpeed;

        //double relativeTurnAngle = relativeAngleToTarget - Math.toRadians(180) + preferredAngle;

        rotationPower = Range.clip(relativeAngleToTarget/Math.toRadians(30),-1, 1)* turnSpeed;

        if(distanceToTarget < 660){
            rotationPower = 0;
            xMotorPower = 0;
            yMotorPower = 0;
        }

    }
}
