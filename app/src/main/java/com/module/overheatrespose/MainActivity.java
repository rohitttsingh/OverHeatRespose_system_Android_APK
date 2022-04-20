package com.module.overheatrespose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
private SensorManager sensorManager;
private Sensor tempSensor;
private TextView textView;
private Boolean isTemperatureSensorAvailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tv);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null){

            tempSensor= sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorAvailable=true;
        }
        else {
            textView.setText("Temperature Sensor is not Available");
            isTemperatureSensorAvailable=false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        textView.setText(sensorEvent.values[0]+"°C");

        if (sensorEvent.values[0]>30) {
            Toast.makeText(getApplicationContext(),"Temperature is above 30 degree",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {

        super.onResume();
        if (isTemperatureSensorAvailable){
            sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTemperatureSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}