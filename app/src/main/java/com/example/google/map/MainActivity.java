package com.example.google.map;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnMapReadyCallback {

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap googleMap;
    double x, y;
    LocationManager manager;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        /*if (!gps_enabled && !network_enabled) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "nothing is enabled", duration);
            toast.show();
        }*/




       // timer=new Timer();
       // timer.schedule(new GetLastLocation(), 20000);
     }


    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

       /* Marker seoul = googleMap.addMarker(new MarkerOptions().position(SEOUL)
                .title("Seoul"));
        googleMap.addMarker(new MarkerOptions().position(SEOUL).title("Seoul"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL , 17.0f));*/
       // googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "nothing is enabled", duration);
            toast.show();
            return;
        }


        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER , 0 , 0 , locationListenerGps);
       /* if (gps_enabled) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }
        if(network_enabled){
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER , 0 , 0 , locationListenerGps);
        }*/


    }

    private void stopLocation(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "nothing is enabled", duration);
            toast.show();
            return;
        }

        manager.removeUpdates(locationListenerGps);
    }

    LocationListener locationListenerGps = new LocationListener(){
        public void onLocationChanged(Location location) {
            //timer.cancel();
            x =location.getLatitude();
            y = location.getLongitude();


            //manager.removeUpdates(this);
            //manager.removeUpdates(locationListenerNetwork);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "gps enabled "+x + "\n" + y, duration);
            toast.show();


            LatLng test = new LatLng(x,y);
            Marker seoul = googleMap.addMarker(new MarkerOptions().position(test)
                    .title("Seoul"));
            googleMap.addMarker(new MarkerOptions().position(test).title("Seoul"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test , 17.0f));

            stopLocation();
        }
        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener(){
        public void onLocationChanged(Location location) {
            timer.cancel();
            x = location.getLatitude();
            y = location.getLongitude();
            //manager.removeUpdates(this);
            //manager.removeUpdates(locationListenerGps);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "network enabled"+x + "\n" + y, duration);
            toast.show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private void startLocationService(){


    }

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            //manager.removeUpdates(locationListenerGps);
            //manager.removeUpdates(locationListenerNetwork);

            Location net_loc = null, gps_loc = null;
            Context context = getApplicationContext();
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, "nothing is enabled", duration);
                toast.show();
                return;
            }

            if (gps_enabled)
                gps_loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (network_enabled)
                net_loc = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            //if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime()) {
                    x = gps_loc.getLatitude();
                    y = gps_loc.getLongitude();
                    context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "gps lastknown " + x + "\n" + y, duration);
                    toast.show();
                } else {
                    x = net_loc.getLatitude();
                    y = net_loc.getLongitude();
                    context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "network lastknown " + x + "\n" + y, duration);
                    toast.show();

                }

            }

            if (gps_loc != null) {
                {
                    x = gps_loc.getLatitude();
                    y = gps_loc.getLongitude();
                    context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "gps lastknown " + x + "\n" + y, duration);
                    toast.show();
                }

            }
            if (net_loc != null) {
                {
                    x = net_loc.getLatitude();
                    y = net_loc.getLongitude();
                    context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "network lastknown " + x + "\n" + y, duration);
                    toast.show();

                }
            }
            context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "no last know avilable", duration);
            toast.show();

        }

    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/
}
