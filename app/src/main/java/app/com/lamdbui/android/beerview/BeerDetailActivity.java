package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BeerDetailActivity extends AppCompatActivity {

    public static final String ARG_BEER = "beer";

    private TextView mBeerTextView;

    private Beer mBeer;

    public static Intent newIntent(Context context, Beer beer) {
        Intent intent = new Intent(context, BeerDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BEER, beer);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        Bundle bundle = getIntent().getExtras();
        mBeer = bundle.getParcelable(ARG_BEER);

        mBeerTextView = (TextView) findViewById(R.id.beer_detail_text);
        mBeerTextView.setText((mBeer == null) ? "NO BEER FOUND" : mBeer.toString());
    }
}
