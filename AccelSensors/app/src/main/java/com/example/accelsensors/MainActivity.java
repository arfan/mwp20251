package com.example.accelsensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView accel0, accel1, accel2, position, magnetic;
    SensorManager sensorManager;
    Sensor sensorAccelerometer;
    Sensor sensorMagnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        accel0 = findViewById(R.id.accel0);
        accel1 = findViewById(R.id.accel1);
        accel2 = findViewById(R.id.accel2);
        position = findViewById(R.id.position);
        magnetic = findViewById(R.id.temperature);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (sensorAccelerometer != null) {
            sensorManager.registerListener(MainActivity.this, sensorAccelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensorMagnetic != null) {
            sensorManager.registerListener(MainActivity.this, sensorMagnetic, sensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // Compute total magnetic field strength
            double magnitude = Math.sqrt(x * x + y * y + z * z);
            magnetic.setText("Magnetic Strength: " + String.format("%.2f", magnitude) + " µT");
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float values0 = 0, values1, values2;

            System.out.println(values0);
            values0 = sensorEvent.values[0];
            values1 = sensorEvent.values[1];
            values2 = sensorEvent.values[2];

            accel0.setText("X-axis: " + values0);
            accel1.setText("Y-axis: " + values1);
            accel2.setText("Z-axis: " + values2);

            if (values0 > 9) {
                position.setText("Device Orientation Turned Landscape Left");
//            Toast.makeText(this, "Device Orientation Turned Landscape Left",Toast.LENGTH_SHORT).show();
            } else if (values0 < -9) {
                position.setText("Device Orientation Turned Landscape Right");
//            Toast.makeText(this, "Device Orientation Turned Landscape Right",Toast.LENGTH_SHORT).show();
            } else if (values0 > -0.5 && values0 < 0.5) {
                position.setText("Device Orientation is Portrait");
//            Toast.makeText(this, "Device Orientation is Portrait",Toast.LENGTH_SHORT).show();
            }
        }

    }
}