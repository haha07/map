package com.example.google.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by won on 2017-02-15.
 * 브로드캐스터란 알람이나 배리터가 없는 경우 ,, 나타나는 작업을 ,,전체적인 이름으로 브로드캐스터라고 한다.
 * 해당영역에 접근하거나 , 벗어났을 경우 , 알려주기 위해서 브로드캐스터 리시버를 만듬.
 */

public class LocationReceiver extends BroadcastReceiver
{
    private Intent receivedIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context , "목표지점에 접근중.." , Toast.LENGTH_LONG).show();

        if(intent != null){
            receivedIntent = intent;
            int id = intent.getIntExtra("id" , 0);
            double lat = intent.getDoubleExtra("lat" , 0.0D);
            double lon = intent.getDoubleExtra("lon" , 0.0D);
        }

        /*boolean isEntering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
        if(isEntering){
            Toast.makeText(context , "목표지점에 접근중.." , Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context , "목표지점에서 벗어납니다." , Toast.LENGTH_LONG).show();
        }*/
    }
}
