package fi.tut.saari5.gps_testi;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * TEstaillaan GPS-moduulin toimintaa. Lähteinä mm:
 * https://developer.android.com/guide/topics/location/strategies.html
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity {
    private Context context;
    private static final String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toast kayttoo varten
        context = getApplicationContext();

        LocationManager locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("testii", location.toString());
                makeUseIfNewLocation(location);
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
        };
        /*
        To request location updates from the GPS provider, use GPS_PROVIDER instead of NETWORK_PROVIDER.
        You can also request location updates from both the GPS and the Network Location Provider by
        calling requestLocationUpdates() twice—once for NETWORK_PROVIDER and once for GPS_PROVIDER.
         */
        requestLocationAccess();
        if(isLocationAccessAllowed(this)) {
            //https://developer.android.com/guide/topics/location/strategies.html#Permission
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }


    }
    public void makeUseIfNewLocation(Location location){
        Toast.makeText(context, " tekstii", Toast.LENGTH_SHORT);
    }
    public static boolean isLocationAccessAllowed(final Context context){
        return context.checkSelfPermission(PERMISSIONS_LOCATION[0]) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean requestLocationAccess( ) {
        Log.d("requestLocationAccess", "requesting location access");
        // Here, thisActivity is the current activity
        if (isLocationAccessAllowed(this)) {
            Log.d("requestLocationAccess", "request already granted");
            return true;
        } else {
            Log.d("requestLocationAccess", "requesting");
            // No explanation needed, we can request the permission.
            this.requestPermissions(PERMISSIONS_LOCATION, 123);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            return false;
        }
    }
}
