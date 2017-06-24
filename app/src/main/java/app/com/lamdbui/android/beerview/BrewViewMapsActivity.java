package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.BreweryLocation;
import app.com.lamdbui.android.beerview.network.FetchUrlImageTask;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BrewViewMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String ARG_BREWERIES = "breweries";
    public static final int PERMISSION_LOCATION_FINE = 1;

    @BindView(R.id.map_brewery_recyclerview)
    RecyclerView mBreweryRecyclerView;

    private BreweryAdapter mBreweryAdapter;

    private GoogleMap mMap;

    private List<BreweryLocation> mBreweryLocations;

    public static Intent newIntent(Context context, List<BreweryLocation> breweries) {
        Intent intent = new Intent(context, BrewViewMapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BrewViewMapsActivity.ARG_BREWERIES, (ArrayList<BreweryLocation>)breweries);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_view_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        mBreweryLocations = bundle.getParcelableArrayList(ARG_BREWERIES);

        ButterKnife.bind(this);

        mBreweryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if(mBreweryAdapter == null) {
            mBreweryAdapter = new BreweryAdapter(mBreweryLocations);
            mBreweryRecyclerView.setAdapter(mBreweryAdapter);
        }
        else {
            mBreweryAdapter.notifyDataSetChanged();
        }
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

        for(BreweryLocation breweryLocation : mBreweryLocations) {
            LatLng location = new LatLng(breweryLocation.getLatitude(), breweryLocation.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(breweryLocation.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer_icon_32)));
            marker.setTag(breweryLocation);
        }

        //LatLngBounds mapBounds = new LatLngBounds()

        LatLng sunsetReservoir = new LatLng(37.7539648, -122.4824472);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sunsetReservoir, 12));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // get the BreweryLocation object back
                BreweryLocation breweryLocation = (BreweryLocation) marker.getTag();

                Intent intent = BreweryActivity.newIntent(BrewViewMapsActivity.this, breweryLocation);

                //startActivity(BreweryDetailActivity.newIntent(this, mBrewery, mBreweryBeers));

                startActivity(intent);
            }
        });

        // Set listeners for marker events
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnInfoWindowClickListener(this);
//        mMap.setOnMarkerDragListener(this);
//        mMap.setOnInfoWindowCloseListener(this);
//        mMap.setOnInfoWindowLongClickListener(this);
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

    private class BreweryLocationHolder extends RecyclerView.ViewHolder
        implements FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener {

        private ImageView mBreweryImage;
        private TextView mBreweryName;

        private BreweryLocation mBreweryLocation;

        public BreweryLocationHolder(View itemView) {
            super(itemView);

            mBreweryImage = (ImageView) itemView.findViewById(R.id.map_brewery_image);
            mBreweryName = (TextView) itemView.findViewById(R.id.map_brewery_name);
        }

        public void bind(BreweryLocation brewery) {
            mBreweryLocation = brewery;

            FetchUrlImageTask fetchBreweryImage = new FetchUrlImageTask(this);
            fetchBreweryImage.execute(mBreweryLocation.getImagesMedium());

            mBreweryName.setText(mBreweryLocation.getName());
            mBreweryImage.setImageResource(R.drawable.beer_icon_32);
        }

        @Override
        public void completedFetchUrlImageTask(Bitmap bitmap) {
            if(bitmap != null)
                mBreweryImage.setImageBitmap(bitmap);
        }
    }

    private class BreweryAdapter extends RecyclerView.Adapter<BreweryLocationHolder> {

        List<BreweryLocation> mBreweryLocations;

        public BreweryAdapter(List<BreweryLocation> breweryLocations) {
            mBreweryLocations = breweryLocations;
        }

        @Override
        public BreweryLocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(R.layout.list_item_map_brewery, parent, false);

            return new BreweryLocationHolder(view);
        }

        @Override
        public void onBindViewHolder(BreweryLocationHolder holder, int position) {
            BreweryLocation brewery = BrewViewMapsActivity.this.mBreweryLocations.get(position);
            holder.bind(brewery);
        }

        @Override
        public int getItemCount() {
            return BrewViewMapsActivity.this.mBreweryLocations.size();
        }
    }
}
