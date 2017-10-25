package robotx.modules;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import robotx.libraries.*;

/**
 * Created by Nicholas on 11/20/16.
 * Wrapper class for the optical distance sensor.
 * Provides methods for determining distance based on
 * the optical light readings it gives.
 */
public class XOpticalDistanceSensor extends XModule {

	OpticalDistanceSensor sensor;
	String name; // The identifier used to find it in the hardwareMap.

	public XOpticalDistanceSensor(OpMode op, String name) {
		super(op);
		this.name = name;
		sensor = opMode.hardwareMap.opticalDistanceSensor.get(name);
	}

	public void init() {
		sensor = opMode.hardwareMap.opticalDistanceSensor.get(name);
	}

	public double distanceInCentimeters() {
        opMode.telemetry.addData("sensor.getLightDetected()", sensor.getLightDetected());
        opMode.telemetry.addData("optical distance", (16.539526*Math.pow(sensor.getLightDetected(), -0.351733))/10.0);
		return (16.539526*Math.pow(sensor.getLightDetected(), -0.351733))/10.0;
	}

    public void loop() {
        opMode.telemetry.addData("ODS", distanceInCentimeters());
    }

}
