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

import static robotx.RAPS.MathFunctions.*;


public class RAPSMovement {

    public static double xMotorPower;
    public static double yMotorPower;
    public static double rotationPower;

    public static double movementXPower = 0;
    public static double movementYPower = 0;





    public static void goToPos(double x, double y, double moveSpeed, double preferredAngle, double turnSpeed){

        double distanceToTarget = Math.hypot(x - RAPSOpMode.worldXpos, y - RAPSOpMode.worldYpos);

        double absoluteAngleToTarget = Math.atan2(y - RAPSOpMode.worldYpos, x - RAPSOpMode.worldXpos);

        double relativeAngleToTarget = angleWrap(absoluteAngleToTarget - (RAPSOpMode.worldAngle - Math.toRadians(90)));

        double relativeXToPoint = Math.cos(relativeAngleToTarget)*distanceToTarget;
        double relativeYtoPoint = Math.sin(relativeAngleToTarget)*distanceToTarget;

        movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));
        movementYPower = relativeYtoPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYtoPoint));

        xMotorPower = movementXPower; //* moveSpeed;
        yMotorPower = movementYPower; //* moveSpeed;

        double relativeTurnAngle = relativeAngleToTarget - Math.toRadians(180) + preferredAngle;
        rotationPower = Range.clip(relativeTurnAngle/Math.toRadians(30),-1, 1); // * turnSpeed;

        if(distanceToTarget < 30){
            rotationPower = 0;
            xMotorPower = 0;
            yMotorPower = 0;
        }

    }
}
