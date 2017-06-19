package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.Brewery;
import app.com.lamdbui.android.beerview.network.FetchUrlImageTask;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BeerDetailActivity extends AppCompatActivity
    implements FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener {

    public static final String ARG_BEER = "beer";

    @BindView(R.id.toolbar_photo)
    ImageView mToolbarImageView;
    @BindView(R.id.beer_detail_name)
    TextView mBeerNameTextView;
    @BindView(R.id.beer_detail_brewery)
    TextView mBeerBreweryTextView;
    @BindView(R.id.beer_detail_image)
    ImageView mBeerImageView;
    @BindView(R.id.beer_detail_abv)
    TextView mBeerAbvTextView;
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
        Intent intent = new Intent(context, BeerDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BEER, beer);
        intent.putExtras(bundle);

        return intent;
    }

    // interface from FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener
    @Override
    public void completedFetchUrlImageTask(Bitmap bitmap) {
        if(bitmap != null) {
            mBeerImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        ButterKnife.bind(this);

        mBeer = getIntent().getParcelableExtra(ARG_BEER);

        // initiate our background tasks
        FetchUrlImageTask urlImageTask = new FetchUrlImageTask(this);
        if(mBeer.getLabelsMedium() != null)
            urlImageTask.execute(mBeer.getLabelsMedium());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mBeer.getNameDisplay());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        mBeerAbvTextView.setText(Double.toString(mBeer.getAbv()) + "% ABV");
        mBeerTypeTextView.setText(mBeer.getBeerStyleName());
        //mBeerTypeTextView.setText(mBeer.get)
        //mBeerIBUTextView.setText(mBeer.get)
        //mBeerOriginalGravityTextView.setText(Double.toString(mBeer.getOriginalGravity()));
        mBeerIBUTextView.setVisibility(View.GONE);

        StringBuilder originalGravity = new StringBuilder();
        originalGravity.append(getString(R.string.text_original_gravity));
        originalGravity.append(": ");
        if(mBeer.getOriginalGravity() == 0)
            originalGravity.append(getString(R.string.info_none_specified));
        else
            originalGravity.append(mBeer.getOriginalGravity());
        mBeerOriginalGravityTextView.setText(originalGravity.toString());
        mBeerDescriptionTextView.setText(
                (mBeer.getDescription() == null) ? getString(R.string.info_none) : mBeer.getDescription());
    }
}
