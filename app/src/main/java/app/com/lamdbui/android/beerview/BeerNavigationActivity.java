package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.Address;
import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.BreweryLocation;

public class BeerNavigationActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_BEER = "beer";
    private static final String ARG_BREWERY_LOCATIONS = "brewery_locations";
    private static final String ARG_LOCATION_DATA = "location_data";

    private Beer mBeer;
    private List<BreweryLocation> mBreweryLocations;
    private List<Address> mAddresses;

    private Fragment mHomeFragment;
    private Fragment mMapsFragment;
    private Fragment mSettingsFragment;

    public static Intent newIntent(Context context, Beer beer, List<BreweryLocation> breweryLocations, List<Address> addresses) {
        Intent intent = new Intent(context, BeerNavigationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BEER, beer);
        bundle.putParcelableArrayList(ARG_BREWERY_LOCATIONS, (ArrayList) breweryLocations);
        bundle.putParcelableArrayList(ARG_LOCATION_DATA, (ArrayList) addresses);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_navigation);

        mBeer = getIntent().getParcelableExtra(ARG_BEER);
        mBreweryLocations = getIntent().getParcelableArrayListExtra(ARG_BREWERY_LOCATIONS);
        mAddresses = getIntent().getParcelableArrayListExtra(ARG_LOCATION_DATA);

        FragmentManager fm = getSupportFragmentManager();

        mHomeFragment = fm.findFragmentByTag(BeerNavigationHomeFragment.TAG);

        if(mHomeFragment == null) {
            mHomeFragment = BeerNavigationHomeFragment.newInstance(mBreweryLocations, mAddresses);
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
                    mHomeFragment = BeerNavigationHomeFragment.newInstance(mBreweryLocations, mAddresses);
                }

                fm.beginTransaction()
                        .replace(R.id.content, mHomeFragment, BeerNavigationHomeFragment.TAG)
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.navigation_map:
                mMapsFragment = fm.findFragmentByTag(BeerViewMapsFragment.TAG);

                if(mMapsFragment == null) {
                    mMapsFragment = BeerViewMapsFragment.newInstance(mBreweryLocations, mAddresses);
                }
                fm.beginTransaction()
                        .replace(R.id.content, mMapsFragment, BeerViewMapsFragment.TAG)
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.navigation_more:

                //mSettingsPreferenceFragment = fm.findFragmentByTag(BeerNavigationSettingsPreferenceFragment.TAG);
                //mSettingsPreferenceFragment = getFragmentManager().findFragmentByTag(BeerNavigationSettingsPreferenceFragment.TAG);
//                if(mSettingsPreferenceFragment == null) {
//                    //mSettingsPreferenceFragment = (Fragment) BeerNavigationSettingsPreferenceFragment.newInstance();
//                    mSettingsPreferenceFragment = new BeerNavigationSettingsPreferenceFragment();
//                }
//                mMapsFragment = fm.findFragmentByTag(BeerViewMapsFragment.TAG);
//                fm.beginTransaction().hide(mMapsFragment).commit();
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.content, mSettingsPreferenceFragment, BeerNavigationSettingsPreferenceFragment.TAG)
//                        .addToBackStack(null)
//                        .commit();

                mSettingsFragment = fm.findFragmentByTag(BeerNavigationSettingsFragment.TAG);
                if(mSettingsFragment == null) {
                    mSettingsFragment = BeerNavigationSettingsFragment.newInstance();
                }
                fm.beginTransaction()
                        .replace(R.id.content, mSettingsFragment, BeerNavigationSettingsFragment.TAG)
                        .addToBackStack(null)
                        .commit();
//                fm.beginTransaction()
//                        .replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer))
//                        .addToBackStack(null)
//                        .commit();
                //fm.beginTransaction().replace(R.id.content, BreweryD)
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}
