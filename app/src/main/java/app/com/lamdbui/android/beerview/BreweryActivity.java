package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class BreweryActivity extends AppCompatActivity {

    public static final String ARG_BREWERY = "brewery";

    private TextView mTextMessage;

    private Brewery mBrewery;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    public static Intent newIntent(Context context, Brewery brewery) {
        Intent intent = new Intent(context, BreweryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BreweryActivity.ARG_BREWERY, brewery);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery);

        Bundle bundle = getIntent().getExtras();
        mBrewery = bundle.getParcelable(ARG_BREWERY);

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setText(mBrewery.toString());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
