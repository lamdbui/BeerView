package app.com.lamdbui.android.beerview;

import android.graphics.Bitmap;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.com.lamdbui.android.beerview.model.Address;
import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.Brewery;
import app.com.lamdbui.android.beerview.model.BreweryLocation;
import app.com.lamdbui.android.beerview.network.BeerListResponse;
import app.com.lamdbui.android.beerview.network.BreweryDbClient;
import app.com.lamdbui.android.beerview.network.BreweryDbInterface;
import app.com.lamdbui.android.beerview.network.BreweryResponse;
import app.com.lamdbui.android.beerview.network.FetchUrlImageTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lamdbui on 5/29/17.
 */

public class BeerNavigationHomeFragment extends Fragment {

    public static final String TAG = BeerNavigationHomeFragment.class.getSimpleName();

    private static final String LOG_TAG = BeerNavigationHomeFragment.class.getSimpleName();

    private static final String ARG_BREWERY_LOCATIONS = "brewery_locations";
    private static final String ARG_LOCATION_DATA = "location_data";

    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

    @BindView(R.id.navigation_city_textview)
    TextView mCityTextView;
    @BindView(R.id.navigation_state_textview)
    TextView mStateTextView;
    @BindView(R.id.home_breweries_recyclerview)
    RecyclerView mHomeBreweriesRecyclerView;
    @BindView(R.id.home_beers_recyclerview)
    RecyclerView mHomeBeersRecyclerView;

    private BreweryLocationAdapter mBreweryLocationAdapter;
    private BeerAdapter mBreweryBeersAdapter;

    private List<BreweryLocation> mBreweryLocations;
    private List<Beer> mBreweryBeers;
    private List<Address> mAddresses;

    private BreweryDbInterface mBreweryDbService;

    private FirebaseAnalytics mFirebaseAnalytics;

    public static BeerNavigationHomeFragment newInstance(List<BreweryLocation> breweryLocations, List<Address> addresses) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_BREWERY_LOCATIONS, (ArrayList<BreweryLocation>) breweryLocations);
        args.putParcelableArrayList(ARG_LOCATION_DATA, (ArrayList<Address>) addresses);
        BeerNavigationHomeFragment fragment = new BeerNavigationHomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBreweryLocations = new ArrayList<>();
        mBreweryBeers = new ArrayList<>();

        mBreweryLocations = getArguments().getParcelableArrayList(ARG_BREWERY_LOCATIONS);
        mAddresses = getArguments().getParcelableArrayList(ARG_LOCATION_DATA);

        mBreweryDbService = BreweryDbClient.getClient().create(BreweryDbInterface.class);


        for(BreweryLocation breweryLocation : mBreweryLocations) {
            Call<BeerListResponse> callBeersAtBrewery = mBreweryDbService.getBeersAtBrewery(breweryLocation.getBreweryId(), API_KEY, "Y");
            callBeersAtBrewery.enqueue(new Callback<BeerListResponse>() {
                @Override
                public void onResponse(Call<BeerListResponse> call, Response<BeerListResponse> response) {
                    List<Beer> beersAtBrewery = response.body().getBeerList();
                    if(beersAtBrewery != null) {
                        mBreweryBeers.addAll(beersAtBrewery);
                        mBreweryBeersAdapter.setBeers(mBreweryBeers);
                        mBreweryBeersAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<BeerListResponse> call, Throwable t) {
                    Log.e(LOG_TAG, "Error fetching beers from brewery");
                }
            });
        }

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);

        //getFragmentManager().findFragmentByTag()

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_beer_navigation_home, container, false);

        ButterKnife.bind(this, view);

        mHomeBreweriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mHomeBeersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // TODO: Move into updateUI()
        if(mBreweryLocationAdapter == null) {
            mBreweryLocationAdapter = new BreweryLocationAdapter(mBreweryLocations);
            mHomeBreweriesRecyclerView.setAdapter(mBreweryLocationAdapter);
        }
        else {
            mHomeBreweriesRecyclerView.setAdapter(mBreweryLocationAdapter);
            //mBreweryLocationAdapter.notifyDataSetChanged();
        }

        if(mBreweryBeersAdapter == null) {
            mBreweryBeersAdapter = new BeerAdapter(mBreweryBeers);
            mHomeBeersRecyclerView.setAdapter(mBreweryBeersAdapter);
        }
        else {
            mHomeBeersRecyclerView.setAdapter(mBreweryBeersAdapter);
            //mBreweryBeersAdapter.notifyDataSetChanged();
        }

        // TODO: Add some code if the city/state is not available
        // assume the first is what we want
        Address address = mAddresses.get(0);
        if(address != null) {
            if(address.getCity() != null)
                mCityTextView.setText(address.getCity().toUpperCase());
            if(address.getState() != null)
                mStateTextView.setText(address.getState().toUpperCase());
        }

        return view;
    }

    public void setBreweryLocations(List<BreweryLocation> breweryLocations) {
        mBreweryLocations = breweryLocations;
    }

    private class BreweryLocationViewHolder extends RecyclerView.ViewHolder
            implements FetchUrlImageTask.OnCompletedFetchUrlImageTaskListener, View.OnClickListener {

        private CardView mBreweryLocationCardView;
        private ImageView mBreweryImageView;
        private TextView mBreweryNameTextView;

        private BreweryLocation mBreweryLocation;

        private Brewery mBrewery;
        private List<Beer> mBreweryBeers;

        public BreweryLocationViewHolder(View itemView) {
            super(itemView);

            mBrewery = null;
            mBreweryBeers = null;

            mBreweryLocationCardView = (CardView) itemView.findViewById(R.id.list_item_brewery_location_cardview);
            mBreweryLocationCardView.setOnClickListener(this);
            mBreweryImageView = (ImageView) itemView.findViewById(R.id.list_item_brewery_location_image);
            mBreweryNameTextView = (TextView) itemView.findViewById(R.id.list_item_brewery_location_name);
        }

        public void bind(BreweryLocation location) {
            mBreweryLocation = location;
            mBreweryNameTextView.setText(mBreweryLocation.getName());

            // fetch the icon
            FetchUrlImageTask beerIconTask = new FetchUrlImageTask(this);
            if(mBreweryLocation.getImagesIcon() != null)
                beerIconTask.execute(mBreweryLocation.getImagesIcon());
        }

        @Override
        public void completedFetchUrlImageTask(Bitmap bitmap) {
            if(bitmap != null)
                mBreweryImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {

            Call<BreweryResponse> callBreweryById = mBreweryDbService.getBrewery(mBreweryLocation.getBreweryId(), API_KEY, "Y");
            callBreweryById.enqueue(new Callback<BreweryResponse>() {
                @Override
                public void onResponse(Call<BreweryResponse> call, Response<BreweryResponse> response) {
                    mBrewery = response.body().getBrewery();
                }

                @Override
                public void onFailure(Call<BreweryResponse> call, Throwable t) {

                }
            });

            Call<BeerListResponse> callBeersAtBrewery = mBreweryDbService.getBeersAtBrewery(mBreweryLocation.getBreweryId(), API_KEY, "Y");
            callBeersAtBrewery.enqueue(new Callback<BeerListResponse>() {
                @Override
                public void onResponse(Call<BeerListResponse> call, Response<BeerListResponse> response) {
                    // we could possibly get no beers available
                    if(response.body().getData() != null)
                        mBreweryBeers = response.body().getBeerList();
                    startActivity(BreweryDetailActivity.newIntent(getActivity(), mBrewery, mBreweryBeers, mBreweryLocation.getId()));
                    //FragmentManager fm = getActivity().getSupportFragmentManager();
                    //fm.beginTransaction().replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer)).commit();
                }

                @Override
                public void onFailure(Call<BeerListResponse> call, Throwable t) {
                }
            });
        }
    }

    private class BreweryLocationAdapter extends RecyclerView.Adapter<BreweryLocationViewHolder> {

        private List<BreweryLocation> mBreweryLocations;

        public BreweryLocationAdapter(List<BreweryLocation> locations) {
            mBreweryLocations = locations;
        }

        @Override
        public BreweryLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_brewery_location, parent, false);

            return new BreweryLocationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BreweryLocationViewHolder holder, int position) {
            BreweryLocation location = mBreweryLocations.get(position);
            holder.bind(location);
        }

        @Override
        public int getItemCount() {
            return mBreweryLocations.size();
        }
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

            mBeerIconImageView = (ImageView) itemView.findViewById(R.id.list_item_beer_icon);
        }

        public void bind(Beer beer) {
            mBeer = beer;
            mBeerNameTextView.setText(mBeer.getName());

            // fetch the icon
            if(mBeer.getLabelsIcon() != null) {
                FetchUrlImageTask beerIconTask = new FetchUrlImageTask(this);
                beerIconTask.execute(mBeer.getLabelsIcon());
            }
            else
                mBeerIconImageView.setImageResource(R.drawable.beer_icon_32);
        }

        @Override
        public void completedFetchUrlImageTask(Bitmap bitmap) {
            if(bitmap != null)
                mBeerIconImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {

//            Bundle bundle = new Bundle();
//            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "beer_click");
//            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "beer_clicked");
//            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content, BeerDetailActivityFragment.newInstance(mBeer))
                    .addToBackStack(null)
                    .commit();
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
            LayoutInflater inflater = LayoutInflater.from(getActivity());
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
