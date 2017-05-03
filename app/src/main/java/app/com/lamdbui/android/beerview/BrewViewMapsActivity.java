package app.com.lamdbui.android.beerview;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.jar.Manifest;

public class BrewViewMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String ARG_BREWERIES = "breweries";
    public static final int PERMISSION_LOCATION_FINE = 1;

    private GoogleMap mMap;

    private List<Brewery> mBreweries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_view_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        mBreweries = bundle.getParcelableArrayList(ARG_BREWERIES);
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

        // TODO: Fix issue with location only showing after reloading the Application
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //mMap.setMyLocationEnabled(true);
            // show the explanation?
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Request for location to determine nearest Breweries", Toast.LENGTH_LONG).show();
            }

            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_FINE);
        }
        else {
            mMap.setMyLocationEnabled(true);
        }

        for(Brewery brewery : mBreweries) {
            LatLng location = new LatLng(brewery.getLatitude(), brewery.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(brewery.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer_icon_32)));
            marker.setTag(brewery);
        }

        //LatLngBounds mapBounds = new LatLngBounds()

        LatLng sunsetReservoir = new LatLng(37.7539648, -122.4824472);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sunsetReservoir, 12));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // get the Brewery object back
                Brewery brewery = (Brewery) marker.getTag();

                Intent intent = new Intent(BrewViewMapsActivity.this, BreweryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(BreweryActivity.ARG_BREWERY, brewery);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        // Set listeners for marker events
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnInfoWindowClickListener(this);
//        mMap.setOnMarkerDragListener(this);
//        mMap.setOnInfoWindowCloseListener(this);
//        mMap.setOnInfoWindowLongClickListener(this);

//
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_LOCATION_FINE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //mMap.setMyLocationEnabled(true);
                    try {
                        mMap.setMyLocationEnabled(true);
                    }
                    catch(SecurityException e) {
                        // print error here
                    }
                }
                else {
                    // permission not granted
                }
                break;
                //}
        }
    }
}
