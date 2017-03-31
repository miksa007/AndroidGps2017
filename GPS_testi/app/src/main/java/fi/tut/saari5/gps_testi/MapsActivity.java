package fi.tut.saari5.gps_testi;
/*
Muistiinpanoja
https://developers.google.com/maps/documentation/android-api/start

 */
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.StringTokenizer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final String TAG="MapsActivity";
    private GoogleMap mMap;
    private double mLat;
    private double mLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d(TAG,"Viesti tuli 00"+message);
        StringTokenizer st2 = new StringTokenizer(message, ";");
        String alku=st2.nextToken();
        String loppu=st2.nextToken();
        Log.d(TAG,alku+"  "+loppu);
        mLat=Double.parseDouble(alku);
        mLon=Double.parseDouble(loppu);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(0061.4924966, 21.799848);
        LatLng sydney = new LatLng(mLat,mLon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Somewhere"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
