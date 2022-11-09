package com.kopoar16.myapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    SensorManager manager;

    void On(){
        if(Build.VERSION.SDK_INT>=23){
            CameraManager cam=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try{ //카메라 없는 경우도 있으니까
                String id=cam.getCameraIdList()[0];
                cam.setTorchMode(id, true);
            }catch (Exception e){

            }
        }
    }
    void Off(){
        if(Build.VERSION.SDK_INT>=23){
            CameraManager cam=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try{ //카메라 없는 경우도 있으니까
                String id=cam.getCameraIdList()[0];
                cam.setTorchMode(id, false);
            }catch (Exception e){

            }
        }
    }

    SensorEventListener listener=new SensorEventListener() { //변수 선언
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_LIGHT){
                int lux=(int) event.values[0];
                //value[0]에 조도값이 넘어옴

                if(lux<100){
                    img.setImageResource(R.drawable.light);
                    On();
                }
                else{
                    img.setImageResource(R.drawable.dark);
                    Off();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=(ImageView) findViewById(R.id.imageView);
        manager=(SensorManager) getSystemService(SENSOR_SERVICE);
        manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

}