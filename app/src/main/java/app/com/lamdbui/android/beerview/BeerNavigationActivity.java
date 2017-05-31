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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.BreweryLocation;

public class BeerNavigationActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_BEER = "beer";
    private static final String ARG_BREWERY_LOCATIONS = "brewery_locations";

    private TextView mTextMessage;

    private Beer mBeer;
    private List<BreweryLocation> mBreweryLocations;

    private Fragment mHomeFragment;
    private Fragment mMapsFragment;

    public static Intent newIntent(Context context, Beer beer, List<BreweryLocation> breweryLocations) {
        Intent intent = new Intent(context, BeerNavigationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BEER, beer);
        bundle.putParcelableArrayList(ARG_BREWERY_LOCATIONS, (ArrayList) breweryLocations);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_navigation);

        mBeer = getIntent().getParcelableExtra(ARG_BEER);
        mBreweryLocations = getIntent().getParcelableArrayListExtra(ARG_BREWERY_LOCATIONS);

        FragmentManager fm = getSupportFragmentManager();
//        //fm.beginTransaction().replace(R.id.content, BeerNavigationHomeFragment.newInstance(mBreweryLocations)).commit();
//        if(mHomeFragment == null)
//            mHomeFragment = BeerNavigationHomeFragment.newInstance(mBreweryLocations);
//        fm.beginTransaction()
//                .replace(R.id.content, BeerNavigationHomeFragment.newInstance(mBreweryLocations), BeerNavigationHomeFragment.TAG)
//                .addToBackStack(null)
//                .commit();

        mHomeFragment = fm.findFragmentByTag(BeerNavigationHomeFragment.TAG);

        if(mHomeFragment == null) {
            mHomeFragment = BeerNavigationHomeFragment.newInstance(mBreweryLocations);
        }
            fm.beginTransaction()
                    .replace(R.id.content, mHomeFragment, BeerNavigationHomeFragment.TAG)
                    .addToBackStack(null)
                    .commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemSelectedListener(this);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        //super.onSaveInstanceState(outState);
//        FragmentManager fm = getSupportFragmentManager();
//        fm.putFragment(outState, "fragment");
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();

        switch(item.getItemId()) {
            case R.id.navigation_home:
//                FragmentManager fm = getSupportFragmentManager();
//                // see if a Fragment already exists
//                Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//
//                // if not, create one
//                if(fragment == null) {
//                    fragment = createFragment();
//                    // initiate a transaction event for the Fragment
//                    fm.beginTransaction()
//                            .add(R.id.fragment_container, fragment)
//                            .commit();
//                }

                mHomeFragment = fm.findFragmentByTag(BeerNavigationHomeFragment.TAG);

                if(mHomeFragment == null) {
                    mHomeFragment = BeerNavigationHomeFragment.newInstance(mBreweryLocations);
                }

                fm.beginTransaction()
                        .replace(R.id.content, mHomeFragment, BeerNavigationHomeFragment.TAG)
                        .addToBackStack(null)
                        .commit();

                //getSupportActionBar().setTitle("LOLWUT");
                //fm.beginTransaction().replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer)).commit();
//                if(getSupportActionBar() != null)
//                    getSupportActionBar().hide();
                break;
            case R.id.navigation_map:
                //fm.beginTransaction().replace(R.id.content, BeerNavigationHomeFragment.newInstance(mBreweryLocations)).commit();

                mMapsFragment = fm.findFragmentByTag(BeerViewMapsFragment.TAG);

                if(mMapsFragment == null) {
                    mMapsFragment = BeerViewMapsFragment.newInstance(mBreweryLocations);
                }
                fm.beginTransaction()
                        .replace(R.id.content, mMapsFragment, BeerViewMapsFragment.TAG)
                        .addToBackStack(null)
                        .commit();

//                if(getSupportActionBar() != null)
//                    getSupportActionBar().hide();
                break;
            case R.id.navigation_more:
                fm.beginTransaction()
                        .replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer))
                        .addToBackStack(null)
                        .commit();
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
