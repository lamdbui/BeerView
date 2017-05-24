package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreweryDetailActivity extends AppCompatActivity
    implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String ARG_BREWERY = "brewery";

    @BindView(R.id.brewery_detail_map)
    MapView mBreweryMapView;
    @BindView(R.id.brewery_detail_name)
    TextView mBreweryNameTextView;
    @BindView(R.id.brewery_detail_established)
    TextView mBreweryEstablishedTextView;
    @BindView(R.id.brewery_detail_address)
    TextView mBreweryAddressTextView;
    @BindView(R.id.brewery_detail_phone)
    TextView mBreweryPhoneTextView;
    @BindView(R.id.brewery_detail_website)
    TextView mBreweryWebsiteTextView;
    @BindView(R.id.brewery_detail_description)
    TextView mBreweryDescriptionTextView;

    private Brewery mBrewery;

    public static Intent newIntent(Context context, Brewery brewery) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREWERY, brewery);
        Intent intent = new Intent(context, BreweryDetailActivity.class);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mBrewery = getIntent().getParcelableExtra(ARG_BREWERY);

        ButterKnife.bind(this);

        mBreweryMapView.onCreate(mapViewBundle);
        mBreweryMapView.getMapAsync(this);

        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mBreweryMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBreweryMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBreweryMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBreweryMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        // TODO: Remove the static location here and set zoom level
        LatLng breweryLocation = new LatLng(38.4414632, -122.7117124);
        CameraUpdate breweryMapPosition = CameraUpdateFactory.newLatLngZoom(breweryLocation, 13);
        map.moveCamera(breweryMapPosition);
        map.addMarker(new MarkerOptions().position(breweryLocation).title(mBrewery.getName()));
    }

    @Override
    protected void onPause() {
        mBreweryMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBreweryMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBreweryMapView.onLowMemory();
    }

    public void updateUI() {
        mBreweryNameTextView.setText(mBrewery.getName());
        mBreweryEstablishedTextView.setText(Integer.toString(mBrewery.getEstablished()));
        //mBreweryAddressTextView.setText(mBrewery.get)
        mBreweryWebsiteTextView.setText(mBrewery.getWebsite());
        mBreweryDescriptionTextView.setText(mBrewery.getDescription());
        // TODO: Add address stuff from the Locations
    }
}
