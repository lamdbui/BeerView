package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeerDetailActivity2 extends AppCompatActivity {

    public static final String ARG_BEER = "beer";

    @BindView(R.id.beer_detail_name)
    TextView mBeerNameTextView;
    @BindView(R.id.beer_detail_brewery)
    TextView mBeerBreweryTextView;
    @BindView(R.id.beer_detail_type)
    TextView mBeerTypeTextView;
    @BindView(R.id.beer_detail_ibu)
    TextView mBeerIBUTextView;
    @BindView(R.id.beer_detail_original_gravity)
    TextView mBeerOriginalGravityTextView;
    @BindView(R.id.beer_detail_description)
    TextView mBeerDescriptionTextView;

    private Beer mBeer;

    public static Intent newIntent(Context context, Beer beer) {
        Intent intent = new Intent(context, BeerDetailActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BEER, beer);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail2);

        ButterKnife.bind(this);

        mBeer = getIntent().getParcelableExtra(ARG_BEER);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Pliny the Elder");
        toolbar.setTitle(mBeer.getNameDisplay());
        setSupportActionBar(toolbar);
        //toolbar.setSubtitle("Russian River Brewing");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        updateUI();
    }

    private void updateUI() {
        mBeerNameTextView.setText(mBeer.getNameDisplay());
        Brewery brewery = mBeer.getBreweries().get(0);
        mBeerBreweryTextView.setText(brewery.getName());
        //mBeerTypeTextView.setText(mBeer.get)
        //mBeerIBUTextView.setText(mBeer.get)
        mBeerOriginalGravityTextView.setText(Double.toString(mBeer.getOriginalGravity()));
        mBeerDescriptionTextView.setText(mBeer.getDescription());
    }
}
