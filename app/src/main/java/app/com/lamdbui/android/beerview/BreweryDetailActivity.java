package app.com.lamdbui.android.beerview;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.data.BreweryContract;
import app.com.lamdbui.android.beerview.data.BreweryDbUtils;
import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.Brewery;
import app.com.lamdbui.android.beerview.model.BreweryLocation;
import app.com.lamdbui.android.beerview.network.BeerListResponse;
import app.com.lamdbui.android.beerview.network.BreweryDbClient;
import app.com.lamdbui.android.beerview.network.BreweryDbInterface;
import app.com.lamdbui.android.beerview.network.FetchUrlImageTask;
import app.com.lamdbui.android.beerview.widget.BeerMapperWidgetProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreweryDetailActivity extends AppCompatActivity
    implements OnMapReadyCallback, FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String ARG_BREWERY = "brewery";
    private static final String ARG_BREWERY_BEERS = "brewery_beers";
    // use this if there's a particular LOCATION_ID that should be used for location
    private static final String ARG_BREWERY_PREFERRED_LOCATION_ID = "preferred_location_id";

    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

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
    @BindView(R.id.brewery_beers_none_textview)
    TextView mBreweryBeersNoneTextView;
    @BindView(R.id.fab_brewery_detail)
    FloatingActionButton mFavoriteFab;

    // used for displaying beers in the recyclerview
    private BeerAdapter mBeerAdapter;

    // passed in arguments
    private List<Beer> mBreweryBeers;
    private Brewery mBrewery;
    private String mPreferredLocationId;
    private BreweryLocation mBreweryLocation;
    private boolean mIsFavorite;

    // data fetching
    private BreweryDbInterface mBreweryDbService;

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

    public static Intent newIntent(Context context, Brewery brewery, List<Beer> breweryBeers,
                                   String preferredLocationId) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BREWERY, brewery);
        args.putParcelableArrayList(ARG_BREWERY_BEERS, (ArrayList<Beer>)breweryBeers);
        args.putString(ARG_BREWERY_PREFERRED_LOCATION_ID, preferredLocationId);
        Intent intent = new Intent(context, BreweryDetailActivity.class);
        intent.putExtras(args);
        return intent;
    }

    // interface from FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener
    @Override
    public void completedFetchUrlImageTask(Bitmap bitmap) {
        if(bitmap != null)
            mBreweryImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mBreweryDbService = BreweryDbClient.getClient().create(BreweryDbInterface.class);

        mBrewery = getIntent().getParcelableExtra(ARG_BREWERY);

        mPreferredLocationId = getIntent().getStringExtra(ARG_BREWERY_PREFERRED_LOCATION_ID);

        mBreweryLocation = getBreweryLocationById(mPreferredLocationId);

        mBreweryBeers = getIntent().getParcelableArrayListExtra(ARG_BREWERY_BEERS);
        if(mBreweryBeers == null) {
            mBreweryBeers = new ArrayList<>();
            getBeersAtBrewery();
        }

        // initiate our background tasks
        if(mBrewery != null && mBrewery.getImagesSquareMedium() != null) {
            FetchUrlImageTask urlImageTask = new FetchUrlImageTask(this);
            urlImageTask.execute(mBrewery.getImagesSquareMedium());
            //urlImageTask.execute(mBrewery.getImagesMedium());
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        ButterKnife.bind(this);

        // check to see if it is a Favorite
        // TODO: Should we move this into a function?
        if(mBreweryLocation != null) {
            String[] selectionArgs = {mBreweryLocation.getId()};
            Cursor cursorResults = getContentResolver().query(
                    BreweryContract.BreweryTable.CONTENT_URI,
                    null,
                    "ID=?",
                    selectionArgs,
                    null);
            mIsFavorite = (cursorResults.getCount() > 0) ? true : false;

            mFavoriteFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String favoritesResponse = "";
                    if (mIsFavorite) {
                        String[] selectionArgs = {mBreweryLocation.getId()};
                        getContentResolver().delete(BreweryContract.BreweryTable.CONTENT_URI, "ID=?", selectionArgs);
                        mFavoriteFab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        favoritesResponse = getString(R.string.favorites_removed);
                    } else {
                        mBreweryLocation.setBreweryId(mBrewery.getId());
                        getContentResolver().insert(BreweryContract.BreweryTable.CONTENT_URI,
                                BreweryDbUtils.convertBreweryLocationToContentValues(mBreweryLocation));
                        mFavoriteFab.setImageResource(R.drawable.ic_favorite_black_24dp);
                        favoritesResponse = getString(R.string.favorites_added);
                    }
                    Snackbar.make(view, favoritesResponse, Snackbar.LENGTH_LONG)
                            .setAction("ToggleFavorites", null).show();
                    mIsFavorite = !mIsFavorite;

                    // notify any App Widgets of change
                    updateAppWidget();
                }
            });
        }
        mFavoriteFab.setImageResource(R.drawable.ic_favorite_border_black_24dp);

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
        if(mPreferredLocationId != null) {
            //BreweryLocation preferredLocation = getBreweryLocationById(mPreferredLocationId);
            mBreweryLocation = getBreweryLocationById(mPreferredLocationId);
            if(mBreweryLocation != null)
                breweryLocation = new LatLng(mBreweryLocation.getLatitude(), mBreweryLocation.getLongitude());
        }
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

    public void updateAppWidget() {
        Intent intent = new Intent(getApplicationContext(), BeerMapperWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), BeerMapperWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    public void updateUI() {
        if(mBrewery != null) {
            mBreweryNameTextView.setText(mBrewery.getName());
            if (mBrewery.getEstablished() != 0)
                mBreweryEstablishedTextView.setText("Established: " + Integer.toString(mBrewery.getEstablished()));
            else
                mBreweryEstablishedTextView.setVisibility(View.GONE);

            BreweryLocation mainBreweryLocation = getMainBreweryLocation();
            if (mainBreweryLocation.getStreetAddress() != null)
                mBreweryAddressLine1TextView.setText(mainBreweryLocation.getStreetAddress());
            else
                mBreweryAddressLine1TextView.setVisibility(View.GONE);

            StringBuilder builder = new StringBuilder();
            if (mainBreweryLocation.getLocality() != null)
                builder.append(mainBreweryLocation.getLocality() + ", ");
            if (mainBreweryLocation.getRegion() != null)
                builder.append(mainBreweryLocation.getRegion() + " ");
            if (mainBreweryLocation.getPostalCode() != null)
                builder.append(mainBreweryLocation.getPostalCode());
            String addressLine2String = builder.toString();
            if (!addressLine2String.isEmpty())
                mBreweryAddressLine2TextView.setText(builder.toString());
            else
                mBreweryAddressLine2TextView.setVisibility(View.GONE);
            //        mBreweryAddressLine2TextView.setText(mainBreweryLocation.getLocality() + " "
            //                + mainBreweryLocation.getRegion() + " "
            //                + mainBreweryLocation.getPostalCode());
            if (mainBreweryLocation.getPhone() != null)
                mBreweryPhoneTextView.setText(mainBreweryLocation.getPhone());
            else
                mBreweryPhoneTextView.setVisibility(View.GONE);
            if (mBrewery.getWebsite() != null)
                mBreweryWebsiteTextView.setText(mBrewery.getWebsite());
            else
                mBreweryWebsiteTextView.setVisibility(View.GONE);
            if (mBrewery.getDescription() != null)
                mBreweryDescriptionTextView.setText(mBrewery.getDescription());
            else
                mBreweryDescriptionTextView.setText(R.string.info_none);

            // handle the data for the beers at a particular brewery
            if (mBeerAdapter == null) {
                mBeerAdapter = new BeerAdapter(mBreweryBeers);
                mBreweryBeersRecyclerView.setAdapter(mBeerAdapter);
            } else {
                mBeerAdapter.notifyDataSetChanged();
            }
            if(mBeerAdapter.getItemCount() > 0) {
                mBreweryBeersRecyclerView.setVisibility(View.VISIBLE);
                mBreweryBeersNoneTextView.setVisibility(View.GONE);
            }
            else {
                mBreweryBeersRecyclerView.setVisibility(View.GONE);
                mBreweryBeersNoneTextView.setVisibility(View.VISIBLE);
            }
            if (mIsFavorite)
                mFavoriteFab.setImageResource(R.drawable.ic_favorite_black_24dp);
            else
                mFavoriteFab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    public BreweryLocation getBreweryLocationById(String id) {
        if(mBrewery != null) {
            List<BreweryLocation> breweryLocations = mBrewery.getLocations();

            for(BreweryLocation location : breweryLocations) {
                if(location.getId().equals(id)) {
                    return location;
                }
            }
        }

        // didn't find it (shouldn't happen)
        return null;
    }

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

    public void getBeersAtBrewery() {
        Call<BeerListResponse> callBeersAtBrewery = mBreweryDbService.getBeersAtBrewery(mBrewery.getId(), API_KEY, "Y");
        callBeersAtBrewery.enqueue(new Callback<BeerListResponse>() {
            @Override
            public void onResponse(Call<BeerListResponse> call, Response<BeerListResponse> response) {
                // we could possibly get no beers available
                if(response.body().getData() != null) {
                    mBreweryBeers = response.body().getBeerList();
                    mBeerAdapter.setBeers(mBreweryBeers);
                    mBeerAdapter.notifyDataSetChanged();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<BeerListResponse> call, Throwable t) {
            }
        });
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
            if(mBeer.getLabelsMedium() != null) {
//                FetchUrlImageTask beerIconTask = new FetchUrlImageTask(this);
//                beerIconTask.execute(mBeer.getLabelsIcon());
                Picasso.with(getApplicationContext())
                        .load(mBeer.getLabelsMedium())
                        .into(mBeerIconImageView);
            }
        }

        @Override
        public void completedFetchUrlImageTask(Bitmap bitmap) {
            if(bitmap != null)
                mBeerIconImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {
            startActivity(BeerDetailActivity.newIntent(getApplicationContext(), mBeer));
        }
    }

    private class BeerAdapter extends RecyclerView.Adapter<BeerHolder> {

        List<Beer> mBeers;

        public BeerAdapter(List<Beer> beers) {
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
