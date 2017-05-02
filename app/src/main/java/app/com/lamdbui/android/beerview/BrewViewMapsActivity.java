package app.com.lamdbui.android.beerview;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

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

public class BrewViewMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String ARG_BREWERIES = "breweries";

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

        for(Brewery brewery : mBreweries) {
            LatLng location = new LatLng(brewery.getLatitude(), brewery.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(brewery.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer_icon_32)));
        }

        //LatLngBounds mapBounds = new LatLngBounds()

        LatLng sunsetReservoir = new LatLng(37.7539648, -122.4824472);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sunsetReservoir, 12));

        // Add a marker and move the camera
//        LatLng socialKitchen = new LatLng(37.763512, -122.466204);
//        LatLng sunsetReservoir = new LatLng(37.7539648, -122.4824472);
//        mMap.addMarker(new MarkerOptions()
//                .position(sunsetReservoir)
//                .title("Sunset Reservoir")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer_icon_32)));
//        mMap.addMarker(new MarkerOptions()
//                .position(socialKitchen)
//                .title("Social Kitchen")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer_icon_32)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sunsetReservoir, 14));

        // Add a marker click listener
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//
//                startActivity(new Intent(BrewViewMapsActivity.this, BreweryActivity.class));
//                return true;
//            }
//        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(BrewViewMapsActivity.this, BreweryActivity.class));
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
}
