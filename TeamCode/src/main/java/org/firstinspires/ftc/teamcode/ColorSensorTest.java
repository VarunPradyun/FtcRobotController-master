package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous
@Disabled
public class ColorSensorTest extends LinearOpMode {

  /** The colorSensor field will contain a reference to our color sensor hardware object */
  NormalizedColorSensor colorSensor;

  @Override public void runOpMode() {

    try {
      runSample();
    } finally {
      
    }
  }

  protected void runSample() {
    
    colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");

    // If possible, turn the light on in the beginning (it might already be on anyway,
    // we just make sure it is if we can).
    if (colorSensor instanceof SwitchableLight) {
      ((SwitchableLight)colorSensor).enableLight(true);
    }

    // Wait for the start button to be pressed.
    waitForStart();

    // Loop until we are asked to stop
    while (opModeIsActive()) {
      colorSensor.setGain(2);

      if (colorSensor instanceof SwitchableLight) {
          SwitchableLight light = (SwitchableLight)colorSensor;
          light.enableLight(!light.isLightOn());
      }

      NormalizedRGBA colors = colorSensor.getNormalizedColors();

      /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
       * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
       * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
       * for an explanation of HSV color. */

      // Update the hsvValues array by passing it to Color.colorToHSV()
      Color.colorToHSV(colors.toColor(), hsvValues);

      telemetry.addLine()
              .addData("Red", "%.3f", colors.red)
              .addData("Green", "%.3f", colors.green)
              .addData("Blue", "%.3f", colors.blue);
      telemetry.addLine()
              .addData("Hue", "%.3f", hsvValues[0])
              .addData("Saturation", "%.3f", hsvValues[1])
              .addData("Value", "%.3f", hsvValues[2]);
      telemetry.addData("Alpha", "%.3f", colors.alpha);

      /* If this color sensor also has a distance sensor, display the measured distance.
       * Note that the reported distance is only useful at very close range, and is impacted by
       * ambient light and surface reflectivity. */
      if (colorSensor instanceof DistanceSensor) {
        telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
      }

      telemetry.update();
    }
  }
}
