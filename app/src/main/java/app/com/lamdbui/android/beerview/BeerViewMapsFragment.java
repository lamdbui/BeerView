package app.com.lamdbui.android.beerview;

import android.*;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.Address;
import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.Brewery;
import app.com.lamdbui.android.beerview.model.BreweryLocation;
import app.com.lamdbui.android.beerview.network.AddressResponse;
import app.com.lamdbui.android.beerview.network.BeerListResponse;
import app.com.lamdbui.android.beerview.network.BreweryDbClient;
import app.com.lamdbui.android.beerview.network.BreweryDbInterface;
import app.com.lamdbui.android.beerview.network.BreweryResponse;
import app.com.lamdbui.android.beerview.network.FetchUrlImageTask;
import app.com.lamdbui.android.beerview.network.GoogleGeocodeClient;
import app.com.lamdbui.android.beerview.network.GoogleGeocodeInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lamdbui on 5/30/17.
 */

public class BeerViewMapsFragment extends Fragment
    implements OnMapReadyCallback, LocationDataHelper.LocationDataHelperCallbacks {

    public static final String TAG = BeerViewActivityFragment.class.getSimpleName();

    public static final String ARG_BREWERIES = "breweries";
    public static final String ARG_LOCATION = "location";

    public static final int PERMISSION_LOCATION_FINE = 1;
    public static final int PERMISSION_REQUEST_LOCATION = 2;

    private static final int MAP_DEFAULT_ZOOM_LEVEL = 12;

    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

    @BindView(R.id.map_brewery_recyclerview)
    RecyclerView mBreweryRecyclerView;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.map_postalcode_button)
    Button mPostalcodeButton;

//    @BindView(R.id.location_searchview)
//    SearchView mSearchView;

    //private SearchView mToolbarSearchView;

    private LinearLayoutManager mLinearLayoutManager;

    private BeerViewMapsFragment.BreweryAdapter mBreweryAdapter;

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLocation;

    private List<BreweryLocation> mBreweryLocations;
    private List<Marker> mBreweryLocationMarkers;
    private List<Address> mAddresses;

    private BreweryDbInterface mBreweryDbService;

    // used to hold current Brewery and Beer details
    private Brewery mBrewery;
    private List<Beer> mBreweryBeers;

    private SharedPreferences mSettings;
    private String mCurrPostalCode;

    public static BeerViewMapsFragment newInstance(List<BreweryLocation> breweries, List<Address> addresses) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_BREWERIES, (ArrayList<BreweryLocation>)breweries);
        args.putParcelableArrayList(ARG_LOCATION, (ArrayList<Address>)addresses);
        BeerViewMapsFragment fragment = new BeerViewMapsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBrewery = null;
        mBreweryBeers = new ArrayList<>();
        mBreweryLocationMarkers = new ArrayList<>();
        mBreweryLocations = getArguments().getParcelableArrayList(ARG_BREWERIES);
        mAddresses = getArguments().getParcelableArrayList(ARG_LOCATION);

        mBreweryDbService = BreweryDbClient.getClient().create(BreweryDbInterface.class);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mLocation = location;
                            // move the map to right location as soon as we get a valid location

                            LatLng lastLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            // TODO: What should we do to ensure the map is valid first?
                            if(mMap != null)
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, MAP_DEFAULT_ZOOM_LEVEL));
                        }
                    }
                });

        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrPostalCode = mSettings.getString(getString(R.string.pref_location_postal_code), "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.activity_brew_view_maps, container, false);

        ButterKnife.bind(this, view);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mPostalcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),mSettings.getString(getString(R.string.pref_location_postal_code), "NONE AVAILABLE"), Toast.LENGTH_SHORT).show();
            }
        });

        mBreweryRecyclerView.setLayoutManager(mLinearLayoutManager);

        if(mBreweryAdapter == null) {
            mBreweryAdapter = new BeerViewMapsFragment.BreweryAdapter(mBreweryLocations);
            mBreweryRecyclerView.setAdapter(mBreweryAdapter);
        }
        else {
            mBreweryRecyclerView.setAdapter(mBreweryAdapter);
            mBreweryAdapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onFindBreweryLocationsCallback(List<BreweryLocation> breweryLocations) {
        mBreweryLocations = breweryLocations;
        mBreweryAdapter.setBreweryLocations(mBreweryLocations);
        mBreweryAdapter.notifyDataSetChanged();
        setBreweryLocationMapMarkers();
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
        //mMap.setMyLocationEnabled(true);

        // TODO: Fix issue with location only showing after reloading the Application
        // TODO: Move this info a separate function
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //mMap.setMyLocationEnabled(true);
            // show the explanation?
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getActivity(), getString(R.string.permission_location_message), Toast.LENGTH_LONG).show();
            }

            ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_FINE);
        }
        else {
            mMap.setMyLocationEnabled(true);
        }

        setBreweryLocationMapMarkers();

        moveMapCameraToAddress();

//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Toast.makeText(getActivity(), "clicked marker!", Toast.LENGTH_SHORT).show();
//                BreweryLocation breweryLocation = (BreweryLocation) marker.getTag();
//
//                //startActivity(BreweryDetailActivity.newIntent(getActivity(), breweryLocation.));
//            }
//        });

        // Set listeners for marker events
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnInfoWindowClickListener(this);
//        mMap.setOnMarkerDragListener(this);
//        mMap.setOnInfoWindowCloseListener(this);
//        mMap.setOnInfoWindowLongClickListener(this);

//
    }

    public void setBreweryLocationMapMarkers() {
        for(BreweryLocation breweryLocation : mBreweryLocations) {
            LatLng location = new LatLng(breweryLocation.getLatitude(), breweryLocation.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(breweryLocation.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer_icon_32)));
            marker.setTag(breweryLocation);

            mBreweryLocationMarkers.add(marker);
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // get the BreweryLocation object back
                final BreweryLocation breweryLocation = (BreweryLocation) marker.getTag();

                Call<BreweryResponse> callBreweryById = mBreweryDbService.getBrewery(breweryLocation.getBreweryId(), API_KEY, "Y");
                callBreweryById.enqueue(new Callback<BreweryResponse>() {
                    @Override
                    public void onResponse(Call<BreweryResponse> call, Response<BreweryResponse> response) {
                        mBrewery = response.body().getBrewery();
                    }

                    @Override
                    public void onFailure(Call<BreweryResponse> call, Throwable t) {

                    }
                });

                Call<BeerListResponse> callBeersAtBrewery = mBreweryDbService.getBeersAtBrewery(breweryLocation.getBreweryId(), API_KEY, "Y");
                callBeersAtBrewery.enqueue(new Callback<BeerListResponse>() {
                    @Override
                    public void onResponse(Call<BeerListResponse> call, Response<BeerListResponse> response) {
                        // we could possibly get no beers available
                        if(response.body().getData() != null)
                            mBreweryBeers = response.body().getBeerList();
                        startActivity(BreweryDetailActivity.newIntent(getActivity(), mBrewery, mBreweryBeers, breweryLocation.getId()));
                    }

                    @Override
                    public void onFailure(Call<BeerListResponse> call, Throwable t) {
                    }
                });
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();

                BreweryLocation breweryLocation = (BreweryLocation) marker.getTag();

                int position = mBreweryAdapter.getAdapterItemPosition(breweryLocation.getId());

                mMap.animateCamera(CameraUpdateFactory
                        .newLatLng(new LatLng(breweryLocation.getLatitude(), breweryLocation.getLongitude())));

                if(position > -1)
                    mLinearLayoutManager.scrollToPosition(position);

                return true;
            }
        });
    }

    public void moveMapCameraToAddress() {
        if(mMap != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLngFromAddresses(), MAP_DEFAULT_ZOOM_LEVEL));
    }

    public void refreshBreweryLocationData() {
        LocationDataHelper locationDataHelper = LocationDataHelper.get(getActivity(), this);
        //locationDataHelper.findBreweryLocationsByLatLng(new LatLng(37.774929, -122.419416));    // SF - 94122
        //locationDataHelper.findBreweryLocationsByLatLng(new LatLng(33.658895, -117.828212));    // Irvine - 92612
        locationDataHelper.findBreweryLocationsByLatLng(getLatLngFromAddresses());
    }

    public void refreshLocationData() {
        String postalCode = mSettings.getString(getString(R.string.pref_location_postal_code), "");
        if(!postalCode.isEmpty()) {
            GoogleGeocodeInterface geocacheService =
                    GoogleGeocodeClient.getClient().create(GoogleGeocodeInterface.class);

            Call<AddressResponse> callAddressDataByPostalCode = geocacheService.getLocationData(postalCode);
            callAddressDataByPostalCode.enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    mAddresses = response.body().getAddressList();
                    if(mAddresses != null)
                        refreshBreweryLocationData();
                    moveMapCameraToAddress();
                    //updateUI();
                }

                @Override
                public void onFailure(Call<AddressResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        // check to see if postalCode change and move map, if it did
        String postalCode = mSettings.getString(getString(R.string.pref_location_postal_code), "");
        if(!mCurrPostalCode.equals(postalCode)) {
            refreshLocationData();
            //moveMapCameraToAddress();
            mCurrPostalCode = postalCode;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
            case PERMISSION_REQUEST_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    if(ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            // TODO: remove?
                                            Toast.makeText(getActivity(), "Got a valid location!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                    else {
                        Toast.makeText(getActivity(), "LocationApi Permission NOT GRANTED!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public Marker getMarkerFromBreweryLocation(BreweryLocation breweryLocation) {
        for(Marker marker : mBreweryLocationMarkers) {
            BreweryLocation markerBrewery = (BreweryLocation)marker.getTag();
            if(markerBrewery.getId().equals(breweryLocation.getId()))
                return marker;
        }
        // didn't find it
        return null;
    }

    private LatLng getLatLngFromAddresses() {
        if(mAddresses != null) {
            // assume the first is what we want
            Address address = mAddresses.get(0);
            return address.getLatLng();
        }
        return null;
    }

    private class BreweryLocationHolder extends RecyclerView.ViewHolder
            implements FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener, View.OnClickListener {

        private CardView mBreweryCardView;
        private ImageView mBreweryImage;
        private TextView mBreweryName;

        private BreweryLocation mBreweryLocation;

        public BreweryLocationHolder(View itemView) {
            super(itemView);

            mBreweryCardView = (CardView) itemView.findViewById(R.id.map_brewery_cardview);
            mBreweryCardView.setOnClickListener(this);
            mBreweryImage = (ImageView) itemView.findViewById(R.id.map_brewery_image);
            mBreweryName = (TextView) itemView.findViewById(R.id.map_brewery_name);
        }

        public void bind(BreweryLocation brewery) {
            mBreweryLocation = brewery;

            if(mBreweryLocation.getImagesMedium() != null) {
                FetchUrlImageTask fetchBreweryImage = new FetchUrlImageTask(this);
                fetchBreweryImage.execute(mBreweryLocation.getImagesMedium());
            }

            mBreweryName.setText(mBreweryLocation.getName());
            mBreweryImage.setImageResource(R.drawable.beer_icon_32);
        }

        @Override
        public void completedFetchUrlImageTask(Bitmap bitmap) {
            if(bitmap != null)
                mBreweryImage.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {
            // TODO: Move to marker on the Map

            Marker marker = getMarkerFromBreweryLocation(mBreweryLocation);
            if(marker != null)
                marker.showInfoWindow();

            mMap.animateCamera(CameraUpdateFactory
                    .newLatLng(new LatLng(mBreweryLocation.getLatitude(), mBreweryLocation.getLongitude())));
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // explanation
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        PERMISSION_REQUEST_LOCATION);
            } else {
                // no explanation needed
                ActivityCompat.requestPermissions(getActivity(),
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        PERMISSION_REQUEST_LOCATION);
            }
            return false;
        }
        else {
            return true;
        }
    }

    private class BreweryAdapter extends RecyclerView.Adapter<BeerViewMapsFragment.BreweryLocationHolder> {

        List<BreweryLocation> mBreweryLocations;

        public BreweryAdapter(List<BreweryLocation> breweryLocations) {
            mBreweryLocations = breweryLocations;
        }

        @Override
        public BeerViewMapsFragment.BreweryLocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_map_brewery, parent, false);

            return new BeerViewMapsFragment.BreweryLocationHolder(view);
        }

        @Override
        public void onBindViewHolder(BeerViewMapsFragment.BreweryLocationHolder holder, int position) {
            BreweryLocation brewery = BeerViewMapsFragment.this.mBreweryLocations.get(position);
            holder.bind(brewery);
        }

        @Override
        public int getItemCount() {
            return BeerViewMapsFragment.this.mBreweryLocations.size();
        }

        // use the BreweryLocation.mId to get the position
        public int getAdapterItemPosition(String id) {
            for(int i = 0; i < mBreweryLocations.size(); i++) {
                if((mBreweryLocations.get(i).getId()).equals(id)) {
                    return i;
                }
            }
            // didn't find it
            return -1;
        }

        public void setBreweryLocations(List<BreweryLocation> breweryLocations) {
            mBreweryLocations = breweryLocations;
        }
    }
}
