package com.cdac.proximitysensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorMgr;
    Sensor sensor;
    TextView textView;
    MediaPlayer player;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewStatus);
        sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        player = MediaPlayer.create(getApplicationContext(), R.raw.music_file);
        try {
            player.setLooping(true);
            player.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(this);
        if(player != null)
            player.stop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == sensor.TYPE_PROXIMITY){
            if(event.values[0] == 0){
                if(flag == true){
                    player.pause();
                    textView.setText("Pause");
                    flag = false;
                }else{
                    player.start();
                    textView.setText("Now Playing..!");
                    flag = true;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}