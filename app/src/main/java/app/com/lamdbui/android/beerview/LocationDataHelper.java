package app.com.lamdbui.android.beerview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.BreweryLocation;
import app.com.lamdbui.android.beerview.network.BreweryDbClient;
import app.com.lamdbui.android.beerview.network.BreweryDbInterface;
import app.com.lamdbui.android.beerview.network.BreweryLocationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lamdbui on 6/22/17.
 */

public class LocationDataHelper {

    public static final int PERMISSION_REQUEST_LOCATION = 2;
    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

    private static LocationDataHelper sLocationDataHelper;

    private Context mContext;
    private LocationDataHelperCallbacks mCallbacks;
    private Location mLocation;
    private List<BreweryLocation> mBreweryLocations;
    private List<Beer> mBeers;
    private BreweryDbInterface mBreweryDbService;

    public interface LocationDataHelperCallbacks {
        void onFindBreweryLocationsCallback(List<BreweryLocation> breweryLocations);
    }

    public static LocationDataHelper get(Context context, LocationDataHelperCallbacks callbacks) {
        if(sLocationDataHelper == null)
            sLocationDataHelper = new LocationDataHelper(context, callbacks);
        // in case there's multiple callbacks listening
        sLocationDataHelper.setContext(context);
        sLocationDataHelper.setCallbacks(callbacks);
        return sLocationDataHelper;
    }

    private LocationDataHelper(Context context, LocationDataHelperCallbacks callbacks) {
        mContext = context;
        mCallbacks = callbacks;
        mBreweryLocations = new ArrayList<>();
        mBeers = new ArrayList<>();
        mLocation = null;
        mBreweryDbService = BreweryDbClient.getClient().create(BreweryDbInterface.class);
    }

    public int findBreweryLocationsByLatLng(LatLng latlng) {
        //Call<BreweryLocationResponse> callBreweriesNearby = mBreweryDbService.getBreweriesNearby(API_KEY, 37.774929, -122.419416);
        Call<BreweryLocationResponse> callBreweriesNearby = mBreweryDbService.getBreweriesNearby(API_KEY, latlng.latitude, latlng.longitude);
        callBreweriesNearby.enqueue(new Callback<BreweryLocationResponse>() {
            @Override
            public void onResponse(Call<BreweryLocationResponse> call, Response<BreweryLocationResponse> response) {
                mBreweryLocations = response.body().getBreweries();
                mCallbacks.onFindBreweryLocationsCallback(mBreweryLocations);
            }

            @Override
            public void onFailure(Call<BreweryLocationResponse> call, Throwable t) {
            }
        });

        return 0;
    }

    public boolean checkLocationPermission(Activity activityContext) {
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // explanation
            if(ActivityCompat.shouldShowRequestPermissionRationale(activityContext,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activityContext,
                        new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                        PERMISSION_REQUEST_LOCATION);
            } else {
                // no explanation needed
                ActivityCompat.requestPermissions(activityContext,
                        new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                        PERMISSION_REQUEST_LOCATION);
            }
            return false;
        }
        else {
            return true;
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public LocationDataHelperCallbacks getCallbacks() {
        return mCallbacks;
    }

    public void setCallbacks(LocationDataHelperCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public List<BreweryLocation> getBreweryLocations() {
        return mBreweryLocations;
    }

    public void setBreweryLocations(List<BreweryLocation> breweryLocations) {
        mBreweryLocations = breweryLocations;
    }

    public List<Beer> getBeers() {
        return mBeers;
    }

    public void setBeers(List<Beer> beers) {
        mBeers = beers;
    }
}
