package com.example.CarStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SensorsActivity extends AppCompatActivity implements SensorEventListener {
    TextView gpsData;
    TextView sensorData;

    private static final int INITIAL_REQUEST = 1337;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    private SensorManager mSensorManager;
    private Sensor mSensorProximity,mSensorAcceleration;
    private String proximity = "";
    private String acceleration = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensors);
        sensorData = findViewById(R.id.sensorData);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final String[] PERMS = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

                ActivityCompat.requestPermissions(this,
                        PERMS,
                        INITIAL_REQUEST);
            } else {
                Task location = fusedLocationClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        double lat = currentLocation.getLatitude();
                        double longitude = currentLocation.getLongitude();
                        gpsData = findViewById(R.id.gpsData);
                        gpsData.setText("lat:"+lat+" long:"+longitude);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception", e.getMessage());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximity = Float.toString(event.values[0]);
        }
        else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            this.acceleration = Float.toString(event.values[0]);
        }
        sensorData.setText("Proximity: " + this.proximity + ";   Acceleration: "+ this.acceleration);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

