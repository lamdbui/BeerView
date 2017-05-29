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

public class BeerNavigationActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_BEER = "beer";

    private TextView mTextMessage;

    private Beer mBeer;

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

    public static Intent newIntent(Context context, Beer beer) {
        Intent intent = new Intent(context, BeerNavigationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BEER, beer);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_navigation);

        mBeer = (Beer) getIntent().getParcelableExtra(ARG_BEER);

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
                fm.beginTransaction().replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer)).commit();
                break;
            case R.id.navigation_map:
                break;
            case R.id.navigation_more:
                break;
        }

        return true;
    }
}
