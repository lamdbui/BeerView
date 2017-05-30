package app.com.lamdbui.android.beerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.network.FetchUrlImageTask;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BreweryDetailActivity extends AppCompatActivity
    implements OnMapReadyCallback, FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String ARG_BREWERY = "brewery";
    private static final String ARG_BREWERY_BEERS = "brewery_beers";

    @BindView(R.id.brewery_detail_map)
    MapView mBreweryMapView;
    @BindView(R.id.brewery_detail_name)
    TextView mBreweryNameTextView;
    @BindView(R.id.brewery_detail_established)
    TextView mBreweryEstablishedTextView;
    @BindView(R.id.brewery_detail_address_line1)
    TextView mBreweryAddressLine1TextView;
    @BindView(R.id.brewery_detail_address_line2)
    TextView mBreweryAddressLine2TextView;
    @BindView(R.id.brewery_detail_phone)
    TextView mBreweryPhoneTextView;
    @BindView(R.id.brewery_detail_website)
    TextView mBreweryWebsiteTextView;
    @BindView(R.id.brewery_detail_description)
    TextView mBreweryDescriptionTextView;
    @BindView(R.id.brewery_detail_image)
    ImageView mBreweryImageView;
    @BindView(R.id.brewery_beers_recycler_view)
    RecyclerView mBreweryBeersRecyclerView;

    // used for displaying beers in the recyclerview
    private BeerAdapter mBeerAdapter;

    private List<Beer> mBreweryBeers;
    private Brewery mBrewery;

    public static Intent newIntent(Context context, Brewery brewery) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREWERY, brewery);
        Intent intent = new Intent(context, BreweryDetailActivity.class);
        intent.putExtras(args);
        return intent;
    }

    public static Intent newIntent(Context context, Brewery brewery, List<Beer> breweryBeers) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREWERY, brewery);
        args.putParcelableArrayList(ARG_BREWERY_BEERS, (ArrayList<Beer>)breweryBeers);
        Intent intent = new Intent(context, BreweryDetailActivity.class);
        intent.putExtras(args);
        return intent;
    }

    // interface from FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener
    @Override
    public void completedFetchUrlImageTask(Bitmap bitmap) {
        if(bitmap != null)
            mBreweryImageView.setImageBitmap(bitmap);
            //view.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        mBrewery = getIntent().getParcelableExtra(ARG_BREWERY);
        //mBreweryBeers = new ArrayList<>();
        //test
        mBreweryBeers = getIntent().getParcelableArrayListExtra(ARG_BREWERY_BEERS);
        if(mBreweryBeers == null)
            mBreweryBeers = new ArrayList<>();

        // initiate our background tasks
        FetchUrlImageTask urlImageTask = new FetchUrlImageTask(this);
        //urlImageTask.execute("https://s3.amazonaws.com/brewerydbapi/brewery/BSsTGw/upload_ozwafH-squareMedium.png");
        // TODO: Use stock default image or no image, if URL not available
        if(mBrewery.getImagesMedium() != null)
            urlImageTask.execute(mBrewery.getImagesMedium());

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        ButterKnife.bind(this);

        // configure our recyclerview to be horizontal scrolling
        mBreweryBeersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mBreweryMapView.onCreate(mapViewBundle);
        mBreweryMapView.getMapAsync(this);

        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mBreweryMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBreweryMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBreweryMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBreweryMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        // TODO: Remove the static location here and set zoom level
        LatLng breweryLocation = new LatLng(38.4414632, -122.7117124);
        //LatLng breweryLocation = new LatLng(mBrewery)
        CameraUpdate breweryMapPosition = CameraUpdateFactory.newLatLngZoom(breweryLocation, 13);
        map.moveCamera(breweryMapPosition);
        map.addMarker(new MarkerOptions().position(breweryLocation).title(mBrewery.getName()));

        // disable the little map navigation toolbar
        map.getUiSettings().setMapToolbarEnabled(false);
    }

    @Override
    protected void onPause() {
        mBreweryMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBreweryMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBreweryMapView.onLowMemory();
    }

    public void updateUI() {
        mBreweryNameTextView.setText(mBrewery.getName());
        mBreweryEstablishedTextView.setText("Established: " + Integer.toString(mBrewery.getEstablished()));

        BreweryLocation mainBreweryLocation = getMainBreweryLocation();
        mBreweryAddressLine1TextView.setText(mainBreweryLocation.getStreetAddress());
        mBreweryAddressLine2TextView.setText(mainBreweryLocation.getLocality() + " "
                + mainBreweryLocation.getRegion() + " "
                + mainBreweryLocation.getPostalCode());
        mBreweryPhoneTextView.setText(mainBreweryLocation.getPhone());
        mBreweryWebsiteTextView.setText(mBrewery.getWebsite());
        mBreweryDescriptionTextView.setText(mBrewery.getDescription());

        // handle the data for the beers at a particular brewery
        if(mBeerAdapter == null) {
            mBeerAdapter = new BeerAdapter(mBreweryBeers);
            mBreweryBeersRecyclerView.setAdapter(mBeerAdapter);
        }
        else {
            mBeerAdapter.notifyDataSetChanged();
        }
    }

//    public HashMap<String, Bitmap> fetchBeerIcons() {
//
//        HashMap<String, Bitmap> beerIconBitmaps = new HashMap<>();
//
//        for(Beer beer : mBreweryBeers) {
//            // initiate our background tasks
//            if(beer.getLabelsIcon() != null && !beer.getLabelsIcon().equals("")) {
//                FetchUrlImageTask beerIconTask = new FetchUrlImageTask(this);
//                beerIconTask.execute(beer.getLabelsIcon());
//            }
//        }
//    }

    public BreweryLocation getMainBreweryLocation() {
        if(mBrewery != null) {

            List<BreweryLocation> breweryLocations = mBrewery.getLocations();

            // we assume that the first location is the main location for the Brewery
            if(breweryLocations.size() > 0) {
                return breweryLocations.get(0);
            }
        }
        return null;
    }

    private class BeerHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener {

        private CardView mBeerCardView;
        private TextView mBeerNameTextView;
        private ImageView mBeerIconImageView;

        private Beer mBeer;

        public BeerHolder(View itemView) {
            super(itemView);

            mBeerCardView = (CardView) itemView.findViewById(R.id.beer_card);
            mBeerCardView.setOnClickListener(this);

            mBeerNameTextView = (TextView) itemView.findViewById(R.id.list_item_beer_name);
            //mBeerNameTextView.setOnClickListener(this);

            mBeerIconImageView = (ImageView) itemView.findViewById(R.id.list_item_beer_icon);
        }

        public void bind(Beer beer) {
            mBeer = beer;
            mBeerNameTextView.setText(mBeer.getName());

            // fetch the icon
            FetchUrlImageTask beerIconTask = new FetchUrlImageTask(this);
            beerIconTask.execute(mBeer.getLabelsIcon());
        }

        @Override
        public void completedFetchUrlImageTask(Bitmap bitmap) {
            if(bitmap != null)
                mBeerIconImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {
            startActivity(BeerDetailActivity2.newIntent(getApplicationContext(), mBeer));
        }
    }

    private class BeerAdapter extends RecyclerView.Adapter<BeerHolder> {

        List<Beer> mBeers;

        public BeerAdapter(List<Beer> beers) {

            //mBeers = new ArrayList<>();
            mBeers = beers;
        }

        @Override
        public BeerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(R.layout.list_item_beer, parent, false);

            return new BeerHolder(view);
        }

        @Override
        public void onBindViewHolder(BeerHolder holder, int position) {
            Beer beer = mBeers.get(position);
            holder.bind(beer);
        }

        @Override
        public int getItemCount() {
            return mBeers.size();
        }

        public void setBeers(List<Beer> beers) {
            mBeers = beers;
        }
    }
}
