package robotx.libraries;

import com.qualcomm.robotcore.hardware.ColorSensor;

import java.util.ArrayList;
import static robotx.libraries.ColorEvaluator.LightLevel.*;
import static robotx.libraries.ColorEvaluator.Material.*;

/**
 * Created by Nicholas on 1/8/2016.
 */
public class ColorEvaluator {

	public enum Material {
		GREY_FLOOR,
		RED_TAPE,
		BLUE_TAPE,
		WHITE_TAPE,
		OTHER;

		public String toString() {
			String output = "";
			if (this==GREY_FLOOR) {
				output = "GREY_FLOOR";
			} else if (this==RED_TAPE) {
				output = "RED_TAPE";
			} else if (this==BLUE_TAPE) {
				output = "BLUE_TAPE";
			} else if (this==WHITE_TAPE) {
				output = "WHITE_TAPE";
			} else if (this==OTHER) {
				output = "OTHER";
			}

			return output;
		}
	}
	enum LightLevel {
		LOW,
		MED,
		HIGH
	}

	ArrayList<Integer> dataList;

	public double mean = 0;
	public double stdDev = 1;

	private void calcMean() {
		double sum = 0;
		for (Object data : dataList.toArray()) {
			sum += ((Integer)data);
		}
		double mean = sum / (double)dataList.size();

		this.mean = mean;
	}


	private void calcStdDev() {
		double sum = 0;
		for (Object data : dataList.toArray()) {
			int x = ((Integer)data);
			sum += Math.pow((x - mean), 2);
		}
		double variance = sum / ((double)dataList.size()-1);
		double stdDev = Math.sqrt(variance);

		this.stdDev = stdDev;
	}

	public ColorEvaluator() {
		dataList = new ArrayList<Integer>();
	}

	public void addData(int data) {
		dataList.add(data);
		calcMean();
		calcStdDev();
	}
	public void addData(ColorSensor sensor) {
		addData(sensor.red());
		addData(sensor.green());
		addData(sensor.blue());
	}

	public double zScore(int data) {
		double zScore = (data - mean)/stdDev;

		return zScore;
	}

	public Material evaluateColor(int redValue, int greenValue, int blueValue) {
		LightLevel red = getLightLevel(redValue);
		LightLevel green = getLightLevel(greenValue);
		LightLevel blue = getLightLevel(blueValue);

		Material output = OTHER;
		if (red==LOW &&green==LOW &&blue==LOW ) {
			output = GREY_FLOOR;
		} else if (red==LOW &&green==LOW &&blue==MED ) {
			output = BLUE_TAPE;
		} else if (red==LOW &&green==LOW &&blue==HIGH) {
			output = BLUE_TAPE;
		} else if (red==LOW &&green==MED &&blue==LOW ) {
			output = GREY_FLOOR;
		} else if (red==LOW &&green==MED &&blue==MED ) {
			output = BLUE_TAPE;
		} else if (red==LOW &&green==MED &&blue==HIGH) {
			output = BLUE_TAPE;
		} else if (red==LOW &&green==HIGH&&blue==LOW ) {
			output = OTHER;
		} else if (red==LOW &&green==HIGH&&blue==MED ) {
			output = OTHER;
		} else if (red==LOW &&green==HIGH&&blue==HIGH) {
			output = BLUE_TAPE;
		} else if (red==MED &&green==LOW &&blue==LOW ) {
			output = RED_TAPE;
		} else if (red==MED &&green==LOW &&blue==MED ) {
			output = OTHER;
		} else if (red==MED &&green==LOW &&blue==HIGH) {
			output = OTHER;
		} else if (red==MED &&green==MED &&blue==LOW ) {
			output = RED_TAPE;
		} else if (red==MED &&green==MED &&blue==MED ) {
			output = GREY_FLOOR;
		} else if (red==MED &&green==MED &&blue==HIGH) {
			output = BLUE_TAPE;
		} else if (red==MED &&green==HIGH&&blue==LOW ) {
			output = OTHER;
		} else if (red==MED &&green==HIGH&&blue==MED ) {
			output = WHITE_TAPE;
		} else if (red==MED &&green==HIGH&&blue==HIGH) {
			output = WHITE_TAPE;
		} else if (red==HIGH&&green==LOW &&blue==LOW ) {
			output = RED_TAPE;
		} else if (red==HIGH&&green==LOW &&blue==MED ) {
			output = OTHER;
		} else if (red==HIGH&&green==LOW &&blue==HIGH) {
			output = OTHER;
		} else if (red==HIGH&&green==MED &&blue==LOW ) {
			output = RED_TAPE;
		} else if (red==HIGH&&green==MED &&blue==MED ) {
			output = RED_TAPE;
		} else if (red==HIGH&&green==MED &&blue==HIGH) {
			output = WHITE_TAPE;
		} else if (red==HIGH&&green==HIGH&&blue==LOW ) {
			output = RED_TAPE;
		} else if (red==HIGH&&green==HIGH&&blue==MED ) {
			output = WHITE_TAPE;
		} else if (red==HIGH&&green==HIGH&&blue==HIGH) {
			output = WHITE_TAPE;
		}

		return output;
	}
	public Material evaluateColor(ColorSensor sensor) {
		return evaluateColor(sensor.red(), sensor.green(), sensor.blue());
	}

	private LightLevel getLightLevel(int color) { // TODO These values work well.  Check if there are better ones.
		double zScore = zScore(color);
		LightLevel output = LOW;

		if (zScore < 3) {
			output = LOW;
		} else if (3 < zScore && zScore < 6) {
			output = MED;
		} else if (6 < zScore) {
			output = HIGH;
		}

		return output;
	}

}

