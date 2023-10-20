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

  int teamPropPos = 0;
  double redVal = 0;
  
  @Override public void runOpMode() {

    colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");

    if (colorSensor instanceof SwitchableLight) {
      ((SwitchableLight)colorSensor).enableLight(true);
    }

    waitForStart();

    runColorTest(1);
    runColorTest(2);
    runColorTest(3);
  }
  protected void runColorTest(int pos){
      colorSensor.setGain(2);
      if (colorSensor instanceof SwitchableLight) {
          SwitchableLight light = (SwitchableLight)colorSensor;
          light.enableLight(!light.isLightOn());
      }
      NormalizedRGBA colors = colorSensor.getNormalizedColors();
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
      redVal = colors.red;

      /** We don't know how the values will read so we can change this later */
      if(redVal > 0){
        teamPropPos = pos;
      }
      if (colorSensor instanceof DistanceSensor) {
        telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
      }
      telemetry.update();
  }
}
