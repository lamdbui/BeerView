package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BeerNavigationActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_BEER = "beer";
    private static final String ARG_BREWERY_LOCATIONS = "brewery_locations";

    private TextView mTextMessage;

    private Beer mBeer;
    private List<BreweryLocation> mBreweryLocations;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_map:
//                    mTextMessage.setText(R.string.title_map);
//                    return true;
//                case R.id.navigation_more:
//                    mTextMessage.setText(R.string.title_more);
//                    return true;
//            }
//            return false;
//        }
//
//    };

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
        fm.beginTransaction().replace(R.id.content, BeerNavigationHomeFragment.newInstance(mBreweryLocations)).commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();

        switch(item.getItemId()) {
            case R.id.navigation_home:
                fm.beginTransaction().replace(R.id.content, BeerNavigationHomeFragment.newInstance(mBreweryLocations)).commit();
                //getSupportActionBar().setTitle("LOLWUT");
                //fm.beginTransaction().replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer)).commit();
                getSupportActionBar().hide();
                break;
            case R.id.navigation_map:
                fm.beginTransaction().replace(R.id.content, BeerNavigationHomeFragment.newInstance(mBreweryLocations)).commit();
                getSupportActionBar().hide();
                break;
            case R.id.navigation_more:
                fm.beginTransaction().replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer)).commit();
                break;
        }

        return true;
    }
}
