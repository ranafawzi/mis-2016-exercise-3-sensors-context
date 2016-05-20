package com.example.alexander.sensorproject;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySensor extends AppCompatActivity implements SensorEventListener , SeekBar.OnSeekBarChangeListener{
    private SensorManager sensormanager;
    private Sensor accelerometer;
    private AccelerometerView accelerometerView;
    private FFTView fftView;
    SeekBar seekbar, fftSBar;
    private float mSensorX,mSensorY,mSensorZ;



   TextView txt,txtY,txtZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_sensor);

        accelerometerView =(AccelerometerView) findViewById(R.id.accelerView);
        fftView = (FFTView) findViewById(R.id.fftView);
        assert fftView != null;
        fftView.setBackgroundColor(Color.BLACK);


        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensormanager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        if( accelerometer  == null){

            Toast.makeText(this, "The Acceler is not available",Toast.LENGTH_SHORT).show();
        }

        txt = (TextView)findViewById(R.id.textView);
        txtY = (TextView)findViewById(R.id.Ytxt);
        txtZ = (TextView)findViewById(R.id.Ztxt);

        accelerometerView =(AccelerometerView) findViewById(R.id.accelerView);

        seekbar=(SeekBar) findViewById(R.id.seekBar);
        assert seekbar != null;
        seekbar.setOnSeekBarChangeListener(this);

        fftSBar=(SeekBar) findViewById(R.id.fftSBar);
        assert  fftSBar != null;
        fftSBar.setOnSeekBarChangeListener(this);






        }

    @Override
    public void onSensorChanged(SensorEvent event) {

        mSensorX= event.values[0];
        mSensorY= event.values[1];
        mSensorZ = event.values[2];
        txt.setText("X:" + mSensorX );
        txtY.setText("Y:" + mSensorY );
        txtZ.setText("Z:" + mSensorZ );

        accelerometerView.addSensorData(mSensorX, mSensorY, mSensorZ);
        accelerometerView.invalidate();

        fftView.addSensorData(mSensorX, mSensorY, mSensorZ);
        fftView.invalidate();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        if (seekBar.getId() == seekbar.getId())
        {
            int progress = 100 - seekbar.getProgress()  ;
            sensormanager.unregisterListener(this, sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
             accelerometerView.invalidate();
            sensormanager.registerListener(this,sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),progress *2000);
        }
        else{

            int newheight = fftSBar.getProgress()*2;
           // fftView.setMinimumHeight(value);
             RelativeLayout.LayoutParams Viewvalue = (RelativeLayout.LayoutParams) fftView.getLayoutParams();
            Viewvalue.height =  newheight;
            fftView.setLayoutParams(Viewvalue);


        }






    }

    @Override
    protected void onResume() {
        super.onResume();
       sensormanager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensormanager.unregisterListener(this);
    }

}
