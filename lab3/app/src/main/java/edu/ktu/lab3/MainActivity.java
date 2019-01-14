package edu.ktu.lab3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senCompass;
    private LocationManager locationManager;
    private TextView xValue;
    private TextView networkCoord;
    private TextView zValue;
    private TextView coordinates;
    private Button startAndStop;
    private boolean InformationObtained;

    private Button openCamera;

    private ImageView imageView;
    private TextView angleView;
    private float azimuth = 0f;
    private float currentAzimuth = 0f;
    private float[] mGeomagnetic = new float[3];
    private float[] mGravity = new float[3];

    private LocationManager networkLocation;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private double[] previousValues = {0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senCompass = senSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        networkLocation = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        startAndStop = (Button)findViewById(R.id.start_and_stop);
        startAndStop.setOnClickListener(StartAndStopButtonListener);

        xValue = (TextView)findViewById(R.id.x_value);
        networkCoord = (TextView)findViewById(R.id.network_coordinates);

        coordinates = (TextView)findViewById(R.id.coordinates);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        openCamera = (Button)findViewById(R.id.open_camera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });

        imageView = (ImageView)findViewById(R.id.compass);

        senSensorManager.registerListener(MainActivity.this, senCompass, SensorManager.SENSOR_DELAY_NORMAL);
    }

    View.OnClickListener StartAndStopButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(senAccelerometer == null){
                Toast.makeText(MainActivity.this, getString(R.string.no_sensor), Toast.LENGTH_LONG).show();
            }

            if(InformationObtained){
                startAndStop.setText(getString(R.string.start));
                senSensorManager.unregisterListener(MainActivity.this, senAccelerometer);
                InformationObtained = false;
            } else {
                senSensorManager.registerListener(MainActivity.this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                startAndStop.setText(getString(R.string.stop));
                InformationObtained = true;
            }
        }
    };


    @Override
    public void onSensorChanged(SensorEvent event){
        Sensor mySensor = event.sensor;
        final float alpha = 0.97f;

        synchronized (this){
            if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

                if(event.values[0] < 1 && event.values[0] > -1
                        && event.values[1] < 1 && event.values[1] > -1
                        && event.values[2] < 10 && event.values[2] > 9){
                    xValue.setText("Screen up");
                }
                if(event.values[0] < 1 && event.values[0] > -1
                        && event.values[1] < 1 && event.values[1] > -1
                        && event.values[2] < -9 && event.values[2] > -10){
                    xValue.setText("Screen down");

                    finish();
                }
                if(event.values[0] < 1 && event.values[0] > -1
                        && event.values[1] < 10 && event.values[1] > 9
                        && event.values[2] < 1 && event.values[2] > -1){
                    xValue.setText("Vertical");
                }
                if(event.values[0] < 1 && event.values[0] > -1
                        && event.values[1] < -9 && event.values[1] > -10
                        && event.values[2] < 1 && event.values[2] > -1){
                    xValue.setText("Vertical (up side down)");
                }
                if(event.values[0] < -9 && event.values[0] > -10
                        && event.values[1] < 1 && event.values[1] > -1
                        && event.values[2] < 1 && event.values[2] > -1){
                    xValue.setText("Right side down");
                }
                if(event.values[0] < 10 && event.values[0] > 9
                        && event.values[1] < 1 && event.values[1] > -1
                        && event.values[2] < 1 && event.values[2] > -1){
                    xValue.setText("Left side down");
                }
            }

            if(mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {


                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * event.values[0];
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * event.values[1];
                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * event.values[2];

                float R[] = new float[9];
                float I[] = new float[9];

                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

                if(success){
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    azimuth = (float)Math.toDegrees(orientation[0]);
                    azimuth = (azimuth+360)%360;
                    Animation animation = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    currentAzimuth = azimuth;

                    animation.setDuration(500);
                    animation.setRepeatCount(0);
                    animation.setFillAfter(true);

                    imageView.startAnimation(animation);

                    if(azimuth < 1 && azimuth > 359){
                        startActivity(new Intent(MainActivity.this, CameraActivity.class));
                    }
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onPause(){
        super.onPause();
        if(senAccelerometer != null){
            senSensorManager.unregisterListener(MainActivity.this, senAccelerometer);
        }

        if(senCompass != null){
            senSensorManager.unregisterListener(MainActivity.this, senCompass);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            return;
        }

        this.locationManager.removeUpdates(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume(){
        super.onResume();
        if(senAccelerometer != null && InformationObtained){
            senSensorManager.registerListener(MainActivity.this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(senCompass != null){
            senSensorManager.registerListener(MainActivity.this, senCompass, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            return;
        }

        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);

        Location net_loc;

        if(networkLocation.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            net_loc = networkLocation.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            coordinates.setText(getString(R.string.Latitude_text) + " " + location.getLatitude()+ " \n" + getString(R.string.Longitude_text) + " " + location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}



