package lf.lookingfor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 3;

    DatabaseHandler dbHandler = new DatabaseHandler();
    ArrayList<Event> events = new ArrayList<>();
    HashMap<MarkerOptions, Event> eventMap = new HashMap<>();
    LocationManager locationManager;
    Location lastKnownLocation;

    private void getLocationPermission() {
        // request the permission
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    try{
                        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    } catch (SecurityException e){
                        e.printStackTrace();
                    }


                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                } else {
                    // permission denied
                    getLocationPermission();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    void getLocation() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MapActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(MapActivity.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        getLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker
        // and move the map's camera to the same location.

        events.clear();
        events = dbHandler.getEvents();

        UiSettings mapSettings = googleMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setCompassEnabled(true);

        googleMap.setMaxZoomPreference(googleMap.getMaxZoomLevel());

        LatLng currentLoc = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( currentLoc,17.0f));

        for (Event event: events){

            String address = event.getEventAddress() + "," + event.getEventCity() + "," + event.getEventState() + "," + event.getEventZip();
            LatLng pos = getLocationFromAddress(this, address);

            MarkerOptions markerOptions = new MarkerOptions().position(pos)
                    .title(event.getName()).snippet(event.getDescription());

            LatLng eventPos = markerOptions.getPosition();
            LatLng currPos = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            if(SphericalUtil.computeDistanceBetween(eventPos, currPos) < 5000){
                googleMap.addMarker(markerOptions);
                eventMap.put(markerOptions, event);
            }
        }

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                finish();
                startActivity(new Intent(MapActivity.this, MapEventActivity.class));
            }
        });

        //googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
        //    @Override
        //    public boolean onMarkerClick(Marker marker) {
        //        finish();
        //        startActivity(new Intent(MapActivity.this, MapEventActivity.class));
        //        return false;
        //    }
        //});
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}