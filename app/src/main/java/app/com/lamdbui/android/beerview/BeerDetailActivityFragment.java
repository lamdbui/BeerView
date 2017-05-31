package app.com.lamdbui.android.beerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.Brewery;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lamdbui on 5/29/17.
 */

public class BeerDetailActivityFragment extends Fragment {

    private static final String ARG_BEER = "beer";

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

    public static BeerDetailActivityFragment newInstance(Beer beer) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BEER, beer);
        BeerDetailActivityFragment fragment = new BeerDetailActivityFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public BeerDetailActivityFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBeer = getArguments().getParcelable(ARG_BEER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this view
        // TODO: Maybe switch this to a fragment XML?
        View view = inflater.inflate(R.layout.activity_beer_detail, container, false);

        ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateUI();

        return view;
    }

    private void updateUI() {
        mBeerNameTextView.setText(mBeer.getNameDisplay());
        Brewery brewery = mBeer.getBreweries().get(0);
        mBeerBreweryTextView.setText(brewery.getName());
        mBeerTypeTextView.setText(mBeer.getBeerStyleName());

        StringBuilder abv = new StringBuilder();
        abv.append(getString(R.string.text_abv));
        abv.append(": ");
        if(mBeer.getAbv() == 0)
            abv.append(getString(R.string.info_none_specified));
        else
            abv.append(mBeer.getAbv());
        mBeerAbvTextView.setText(abv.toString());

//        // template for IBU stuff later
//        StringBuilder ibu = new StringBuilder();
//        ibu.append(getString(R.string.text_ibu));
//        ibu.append(": ");
        // disable IBU for now
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
