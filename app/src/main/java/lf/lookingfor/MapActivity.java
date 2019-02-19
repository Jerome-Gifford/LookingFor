package lf.lookingfor;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    DatabaseHandler dbHandler = new DatabaseHandler();
    ArrayList<Event> events = new ArrayList<>();

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
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker
        // and move the map's camera to the same location.

        events.clear();
        events = dbHandler.getEvents();

        for (Event event: events){
            String address = event.getEventAddress() + "," + event.getEventCity() + "," + event.getEventState() + "," + event.getEventZip();
            LatLng pos = getLocationFromAddress(this, address);

            googleMap.addMarker(new MarkerOptions().position(pos)
                    .title(event.getName()).snippet(event.getDescription() + " - Event ID: " + event.getId()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
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