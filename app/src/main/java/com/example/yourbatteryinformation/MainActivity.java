package com.example.yourbatteryinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.BatteryManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

TextView level,voltage,health,batterytype,chargingsource,temprature,chargingstatis;
BroadcastReceiver batterybroadcast;
IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFikterBroadcast();
        level=findViewById(R.id.level);
        voltage=findViewById(R.id.voltage);
        batterytype=findViewById(R.id.batterytype);
        temprature=findViewById(R.id.temprature);
        chargingstatis=findViewById(R.id.chargingstatis);



    }

    private void intentFikterBroadcast() {

        intentFilter =new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batterybroadcast=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction()));
                level.setText(String.valueOf(intent.getIntExtra("level",0))+"%");

         float vol=  (float)(intent.getIntExtra("voltage",0)*0.001);
         voltage.setText(vol+"V");
        batterytype.setText(intent.getStringExtra("technology"));

      //  sethealth(intent);
              float twemp = (float)intent.getIntExtra("Temprsture",-1)/10;
              temprature.setText(twemp+"C");
              setchargstatus(intent);



            }
        };

    }

    private void setchargstatus(Intent intent) {

        int staustemp=intent.getIntExtra("Status",-1);
        switch (staustemp){

            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                chargingstatis.setText("Unknown");
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                chargingstatis.setText("Charging");
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                chargingstatis.setText("Discharging");
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                chargingstatis.setText("Not Charging");
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                chargingstatis.setText("Full");
                break;
            default:
                chargingstatis.setText("Null");





        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batterybroadcast,intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batterybroadcast);
    }
}