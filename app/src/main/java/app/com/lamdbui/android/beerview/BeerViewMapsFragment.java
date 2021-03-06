package app.com.lamdbui.android.beerview;

import android.Manifest;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.com.lamdbui.android.beerview.model.Address;
import app.com.lamdbui.android.beerview.model.Brewery;
import app.com.lamdbui.android.beerview.model.BreweryLocation;
import app.com.lamdbui.android.beerview.network.AddressResponse;
import app.com.lamdbui.android.beerview.network.BreweryDbClient;
import app.com.lamdbui.android.beerview.network.BreweryDbInterface;
import app.com.lamdbui.android.beerview.network.BreweryLocationResponse;
import app.com.lamdbui.android.beerview.network.BreweryResponse;
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
    implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = BeerViewMapsFragment.class.getSimpleName();

    public static final String ARG_BREWERIES = "breweries";
    public static final String ARG_LOCATION = "location";

    public static final int PERMISSION_LOCATION_FINE = 1;
    public static final int PERMISSION_REQUEST_LOCATION = 2;

    private static final int MAP_DEFAULT_ZOOM_LEVEL = 12;

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

    @BindView(R.id.map_brewery_recyclerview)
    RecyclerView mBreweryRecyclerView;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.map_postalcode_button)
    Button mPostalcodeButton;
    @BindView(R.id.map_my_location_button)
    ImageButton mMyLocationButton;
    @BindView(R.id.map_location_none)
    TextView mMapLocationNone;
    @BindView(R.id.map_view_frame)
    FrameLayout mMapFrameLayout;

    private LinearLayoutManager mLinearLayoutManager;

    private BeerViewMapsFragment.BreweryAdapter mBreweryAdapter;

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private List<BreweryLocation> mBreweryLocations;
    private List<Marker> mBreweryLocationMarkers;
    private List<Address> mAddresses;

    // used to keep a hard reference for Picasso to avoid GC
    private List<Target> mMarkerIconTargets;

    private BreweryDbInterface mBreweryDbService;

    // used to hold current Brewery details
    private Brewery mBrewery;

    private SharedPreferences mSettings;
    private String mCurrPostalCode;
    // track currently focused location to determine a second click used to launch Activity
    private BreweryLocation mCurrBreweryLocation;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mSessionLocation;

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

        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCurrPostalCode = mSettings.getString(getString(R.string.pref_session_location_postal_code), "");

        mBrewery = null;
        mBreweryLocationMarkers = new ArrayList<>();
        mMarkerIconTargets = new ArrayList<>();
        mAddresses = getArguments().getParcelableArrayList(ARG_LOCATION);
        if(mAddresses == null) {
            mAddresses = new ArrayList<>();
        }
        mBreweryLocations = getArguments().getParcelableArrayList(ARG_BREWERIES);
        if(mBreweryLocations == null) {
            mBreweryLocations = new ArrayList<>();
        }

        mBreweryDbService = BreweryDbClient.getClient().create(BreweryDbInterface.class);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mCurrBreweryLocation = new BreweryLocation();

        mSessionLocation = null;

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {//setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.activity_brew_view_maps, container, false);

        ButterKnife.bind(this, view);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mBreweryRecyclerView.setLayoutManager(mLinearLayoutManager);

        mPostalcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                final View innerView = view;
                input.setHint(getString(R.string.setting_postal_code_hint));
                input.setMaxLines(1);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog postalCodeAlertDialog = new AlertDialog.Builder(getActivity())
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
                        .setPositiveButton(getString(R.string.dialog_save), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor editor = mSettings.edit();
                                String postalCode = input.getText().toString();
                                if(!postalCode.equals("")) {
                                    editor.putString(getString(R.string.pref_session_location_postal_code), postalCode);
                                    editor.apply();
                                    Snackbar.make(innerView, getString(R.string.map_new_location_set), Snackbar.LENGTH_SHORT)
                                            .setAction("SavedPostalCode", null).show();

                                    refreshLocationData();
                                    updateUI();
                                }
                                else {
                                    Snackbar.make(innerView, getString(R.string.setting_postal_code_error), Snackbar.LENGTH_SHORT)
                                            .setAction("SavedPostalCode", null).show();
                                }
                            }
                        })
                        .create();

                postalCodeAlertDialog.setTitle(R.string.setting_postal_code);
                postalCodeAlertDialog.setMessage(getString(R.string.map_session_current_location_message));
                postalCodeAlertDialog.setView(input);
                postalCodeAlertDialog.show();
            }
        });

        mMyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLocationRequest();
            }
        });

        if (mBreweryAdapter == null) {
            mBreweryAdapter = new BeerViewMapsFragment.BreweryAdapter(mBreweryLocations);
            mBreweryRecyclerView.setAdapter(mBreweryAdapter);
        }
        else {
            mBreweryRecyclerView.setAdapter(mBreweryAdapter);
        }

        updateUI();

        return view;
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
        refreshLocationData();
        if(mAddresses != null & mAddresses.size() > 0) {
            moveMapCameraToAddress();
        }
        setBreweryLocationMapMarkers();
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(checkLocationPermission()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }

    public void updateSessionCurrPostalCode(String newPostalCode) {
        SharedPreferences.Editor editor = mSettings.edit();
        // update only the current session
        editor.putString(getString(R.string.pref_session_location_postal_code), newPostalCode);
        editor.apply();
    }

    public void handleNewLocation(Location location) {
        mSessionLocation = location;
        if(mSessionLocation != null) {
            LatLng latlng = new LatLng(mSessionLocation.getLatitude(), mSessionLocation.getLongitude());
            refreshBreweryLocationData(latlng);
            moveMapCameraToLatLng(latlng);

            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<android.location.Address> addresses = geocoder.getFromLocation(mSessionLocation.getLatitude(), mSessionLocation.getLongitude(), 1);
                String postalCode = addresses.get(0).getPostalCode();

                // update our local session data
                mCurrPostalCode = postalCode;
                updateSessionCurrPostalCode(postalCode);
                updateUI();
            }
            catch(IOException e) {
                // log error
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if(connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        }
        else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    public void createLocationRequest() {
        //mGoogleApiClient.connect();
        if(checkLocationPermission()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            else {
                handleNewLocation(location);
            }
        }
    }

    public void setBreweryLocationMapMarkers() {
        for(BreweryLocation breweryLocation : mBreweryLocations) {
            LatLng location = new LatLng(breweryLocation.getLatitude(), breweryLocation.getLongitude());


            final Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(breweryLocation.getName())
                    .snippet(getString(R.string.map_marker_more_details))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.beer_mug_icon_32)));
            marker.setTag(breweryLocation);

            Target target = new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    mBreweryLocationMarkers.add(marker);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
            mMarkerIconTargets.add(target);

            if(breweryLocation.getImagesIcon() != null) {
                Picasso.with(getActivity())
                        .load(breweryLocation.getImagesIcon())
                        .placeholder(R.drawable.beer_mug_icon_32)
                        .into(target);
            }
            else
                mBreweryLocationMarkers.add(marker);
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // get the BreweryLocation object back
                BreweryLocation breweryLocation = (BreweryLocation) marker.getTag();
                checkAndHandleIfSecondBreweryClick(breweryLocation);
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

                checkAndHandleIfSecondBreweryClick(breweryLocation);

                return true;
            }
        });
    }

    private void checkAndHandleIfSecondBreweryClick(BreweryLocation location) {
        final BreweryLocation clickedLocation = location;
        // check to see if this is the second click
        if(clickedLocation.getBreweryId() == mCurrBreweryLocation.getBreweryId()) {
            Call<BreweryResponse> callBreweryById = mBreweryDbService.getBrewery(clickedLocation.getBreweryId(), API_KEY, "Y");
            callBreweryById.enqueue(new Callback<BreweryResponse>() {
                @Override
                public void onResponse(Call<BreweryResponse> call, Response<BreweryResponse> response) {
                    mBrewery = response.body().getBrewery();
                    if(mBrewery != null)
                        startActivity(BreweryDetailActivity.newIntent(getActivity(), mBrewery, null, clickedLocation));
                }

                @Override
                public void onFailure(Call<BreweryResponse> call, Throwable t) {

                }
            });
        }
        else {
            mCurrBreweryLocation = clickedLocation;
        }
    }

    public void updateUI() {
        if(mCurrPostalCode != "") {
            mMapFrameLayout.setVisibility(View.VISIBLE);
            mMapLocationNone.setVisibility(View.GONE);
            mPostalcodeButton.setText(getString(R.string.map_current_location) + " " + mCurrPostalCode);
        }
        else {
            mMapLocationNone.setVisibility(View.VISIBLE);
            mMapFrameLayout.setVisibility(View.GONE);
        }
    }

    public void moveMapCameraToLatLng(LatLng latlng) {
        if(mMap != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, MAP_DEFAULT_ZOOM_LEVEL));
    }

    public void moveMapCameraToAddress() {
        if(mMap != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLngFromAddresses(mAddresses), MAP_DEFAULT_ZOOM_LEVEL));
    }

    public void findBreweryLocationsByLatLng(LatLng latlng) {
        Call<BreweryLocationResponse> callBreweriesNearby = mBreweryDbService.getBreweriesNearby(API_KEY, latlng.latitude, latlng.longitude);
        callBreweriesNearby.enqueue(new Callback<BreweryLocationResponse>() {
            @Override
            public void onResponse(Call<BreweryLocationResponse> call, Response<BreweryLocationResponse> response) {
                mBreweryLocations = response.body().getBreweries();
                //mCallbacks.onFindBreweryLocationsCallback(mBreweryLocations);
                //mBreweryLocations = breweryLocations;
                mBreweryAdapter.setBreweryLocations(mBreweryLocations);
                mBreweryAdapter.notifyDataSetChanged();
                //fetchAllBeersAtBreweryLocations();
                setBreweryLocationMapMarkers();
            }

            @Override
            public void onFailure(Call<BreweryLocationResponse> call, Throwable t) {
            }
        });
    }

    public void refreshBreweryLocationData(LatLng latlng) {
        findBreweryLocationsByLatLng(latlng);
        setBreweryLocationMapMarkers();
    }

    public void refreshLocationData() {
        String newPostalCode = mSettings.getString(getString(R.string.pref_session_location_postal_code), "");

        //if(!mCurrPostalCode.equals(newPostalCode) && !newPostalCode.isEmpty()) {
        if(!newPostalCode.isEmpty()) {

            mCurrPostalCode = newPostalCode;

            GoogleGeocodeInterface geocacheService =
                    GoogleGeocodeClient.getClient().create(GoogleGeocodeInterface.class);

            Call<AddressResponse> callAddressDataByPostalCode = geocacheService.getLocationData(mCurrPostalCode);
            callAddressDataByPostalCode.enqueue(new Callback<AddressResponse>() {
                @Override
                public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                    mAddresses = response.body().getAddressList();
                    if(mAddresses != null)
                        refreshBreweryLocationData(getLatLngFromAddresses(mAddresses));
                    moveMapCameraToAddress();
                    updateUI();
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

        mGoogleApiClient.connect();

        String newPostalCode = mSettings.getString(getString(R.string.pref_session_location_postal_code), "");
        if(!mCurrPostalCode.equals(newPostalCode) && !newPostalCode.isEmpty())
            refreshLocationData();
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

        if(mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }

        // stash our session data
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(getString(R.string.pref_session_location_postal_code), mCurrPostalCode);
        editor.apply();
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
                                            int m = 4;
                                        }
                                    }
                                });

                    }
                    else {
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

    private LatLng getLatLngFromAddresses(List<Address> addresses) {
        if(addresses != null && addresses.size() > 0) {
            // assume the first is what we want
            Address address = addresses.get(0);
            return address.getLatLng();
        }
        return null;
    }

    private class BreweryLocationHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

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

            mBreweryImage.setImageResource(R.drawable.beer_mug_icon_256);

            if(mBreweryLocation.getImagesMedium() != null) {
                Picasso.with(getActivity())
                        .load(mBreweryLocation.getImagesMedium())
                        .into(mBreweryImage);
            }

            mBreweryName.setText(mBreweryLocation.getName());
        }

        @Override
        public void onClick(View view) {
            Marker marker = getMarkerFromBreweryLocation(mBreweryLocation);
            if(marker != null) {
                marker.showInfoWindow();
                checkAndHandleIfSecondBreweryClick(mBreweryLocation);
            }

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
