package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.Address;
import app.com.lamdbui.android.beerview.model.BreweryLocation;

public class BeerNavigationActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_BREWERY_LOCATIONS = "brewery_locations";
    private static final String ARG_LOCATION_DATA = "location_data";

    public static final int PERMISSION_REQUEST_LOCATION = 2;

    private List<BreweryLocation> mBreweryLocations;
    private List<Address> mAddresses;

    private Fragment mHomeFragment;
    private Fragment mMapsFragment;
    private Fragment mSettingsFragment;

    public static Intent newIntent(Context context, List<BreweryLocation> breweryLocations, List<Address> addresses) {
        Intent intent = new Intent(context, BeerNavigationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_BREWERY_LOCATIONS, (ArrayList) breweryLocations);
        bundle.putParcelableArrayList(ARG_LOCATION_DATA, (ArrayList) addresses);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_navigation);

        mBreweryLocations = new ArrayList<>();
        mAddresses = new ArrayList<>();

        mBreweryLocations = getIntent().getParcelableArrayListExtra(ARG_BREWERY_LOCATIONS);
        mAddresses = getIntent().getParcelableArrayListExtra(ARG_LOCATION_DATA);

        FragmentManager fm = getSupportFragmentManager();

        //checkLocationPermission();

        mHomeFragment = fm.findFragmentByTag(BeerNavigationHomeFragment.TAG);

        if(mHomeFragment == null) {
            mHomeFragment = BeerNavigationHomeFragment.newInstance(null, mAddresses);
            //mHomeFragment = BeerNavigationHomeFragment.newInstance(mBreweryLocations, mAddresses);
        }
            fm.beginTransaction()
                    .replace(R.id.content, mHomeFragment, BeerNavigationHomeFragment.TAG)
                    .addToBackStack(null)
                    .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();

        switch(item.getItemId()) {
            case R.id.navigation_home:
                mHomeFragment = fm.findFragmentByTag(BeerNavigationHomeFragment.TAG);

                if(mHomeFragment == null) {
                    //mHomeFragment = BeerNavigationHomeFragment.newInstance(mBreweryLocations, mAddresses);
                    mHomeFragment = BeerNavigationHomeFragment.newInstance(null, mAddresses);
                }

                fm.beginTransaction()
                        .replace(R.id.content, mHomeFragment, BeerNavigationHomeFragment.TAG)
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.navigation_map:
                mMapsFragment = fm.findFragmentByTag(BeerViewMapsFragment.TAG);

                if(mMapsFragment == null) {
                    //mMapsFragment = BeerViewMapsFragment.newInstance(mBreweryLocations, mAddresses);
                    mMapsFragment = BeerViewMapsFragment.newInstance(null, mAddresses);
                }
                fm.beginTransaction()
                        .replace(R.id.content, mMapsFragment, BeerViewMapsFragment.TAG)
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.navigation_more:
                mSettingsFragment = fm.findFragmentByTag(BeerNavigationSettingsFragment.TAG);
                if(mSettingsFragment == null) {
                    mSettingsFragment = BeerNavigationSettingsFragment.newInstance();
                }
                fm.beginTransaction()
                        .replace(R.id.content, mSettingsFragment, BeerNavigationSettingsFragment.TAG)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        // since we're replacing fragments, if we don't check for this, we can get a blank fragment area
        // on the last back press
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if(fragments == 1) {
            finish();
        }
        else {
            if(fragments > 1) {
                getSupportFragmentManager().popBackStack();
            }
            else {
                super.onBackPressed();
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
////            case PERMISSION_LOCATION_FINE:
////                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    //mMap.setMyLocationEnabled(true);
////                    try {
////                        mMap.setMyLocationEnabled(true);
////                    }
////                    catch(SecurityException e) {
////                        // print error here
////                    }
////                }
////                else {
////                    // permission not granted
////                }
////                break;
////            //}
//            case PERMISSION_REQUEST_LOCATION:
//                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission granted
//                    if(ContextCompat.checkSelfPermission(this,
//                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                    }
//                    else {
//                        Toast.makeText(this, "LocationApi Permission NOT GRANTED!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }
//
//    public boolean checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            // explanation
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//                ActivityCompat.requestPermissions(this,
//                        new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
//                        PERMISSION_REQUEST_LOCATION);
//            } else {
//                // no explanation needed
//                ActivityCompat.requestPermissions(this,
//                        new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
//                        PERMISSION_REQUEST_LOCATION);
//            }
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
}
