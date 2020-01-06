package robotx.RAPS;

import android.graphics.Point;

import java.util.ArrayList;

public class MathFunctions {
    public static double angleWrap (double angle){
        /**
         * Make sure the angle input is within the range -180 t0 180 degrees
         * @param Angle
         * @return
         */

         while(angle < - Math.PI){
             angle += 2*Math.PI;
         }
         while(angle > Math.PI){
             angle -= 2*Math.PI;
         }
         return angle;
    }
   /* public static ArrayList<Point> lineCircleIntersection(Point circleCenter, double radius, Point linePoint1, Point linepoint2){


    }*/
}
