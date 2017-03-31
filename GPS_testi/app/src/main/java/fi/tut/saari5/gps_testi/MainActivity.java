package fi.tut.saari5.gps_testi;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * TEstaillaan GPS-moduulin toimintaa. Lähteinä mm:
 * https://developer.android.com/guide/topics/location/strategies.html
 *
 *
 *
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final String TAG="MainActivity";
    private Context context;
    private static final String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create an instance of GoogleAPIClient.
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

        buildGoogleApiClient();

    }
    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void makeUseIfNewLocation(Location location){
        Toast.makeText(context, " tekstii", Toast.LENGTH_SHORT);
    }
    public static boolean isLocationAccessAllowed(final Context context){
        return context.checkSelfPermission(PERMISSIONS_LOCATION[0]) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean requestLocationAccess( ) {
        Log.d(TAG, "requesting location access");
        // Here, thisActivity is the current activity
        if (isLocationAccessAllowed(this)) {
            Log.d(TAG, "request already granted");
            return true;
        } else {
            Log.d(TAG, "requesting");
            // No explanation needed, we can request the permission.
            this.requestPermissions(PERMISSIONS_LOCATION, 123);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            return false;
        }
    }
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed -metodissa ollaan");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, " onConnected-metodissa ollaan");
        try{
            // Provides a simple way of getting a device's location and is well suited for
            // applications that do not require a fine-grained location and that do not need location
            // updates. Gets the best and most recent location currently available, which may be null
            // in rare cases when a location is not available.
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
                        mLastLocation.getLatitude()));
                mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
                        mLastLocation.getLongitude()));
            } else {
                Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
            }
        }catch (SecurityException e){
            Log.d(TAG, "Virhe2 : Sovelluksella ei ollut oikeuksia lokaatioon");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, " onConnectionSuspended-metodissa ollaan");
        mGoogleApiClient.connect();
    }
}
