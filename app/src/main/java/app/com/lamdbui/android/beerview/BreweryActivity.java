package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import app.com.lamdbui.android.beerview.model.BreweryLocation;

public class BreweryActivity extends AppCompatActivity {

    public static final String ARG_BREWERY = "brewery";

    private TextView mTextMessage;

    private BreweryLocation mBreweryLocation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_map:
                    mTextMessage.setText(R.string.title_map);
                    return true;
                case R.id.navigation_more:
                    mTextMessage.setText(R.string.title_more);
                    return true;
            }
            return false;
        }

    };

    public static Intent newIntent(Context context, BreweryLocation breweryLocation) {
        Intent intent = new Intent(context, BreweryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BreweryActivity.ARG_BREWERY, breweryLocation);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery);

        Bundle bundle = getIntent().getExtras();
        mBreweryLocation = bundle.getParcelable(ARG_BREWERY);

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setText(mBreweryLocation.toString());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
