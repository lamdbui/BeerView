package app.com.lamdbui.android.beerview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.network.BeerListResponse;
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

    private static final String ARG_BREWERY_LOCATIONS = "brewery_locations";

    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

    @BindView(R.id.home_breweries_recyclerview)
    RecyclerView mHomeBreweriesRecyclerView;

    private BreweryLocationAdapter mBreweryLocationAdapter;

    private List<BreweryLocation> mBreweryLocations;

    private BreweryDbInterface mBreweryDbService;

    public static BeerNavigationHomeFragment newInstance(List<BreweryLocation> breweryLocations) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_BREWERY_LOCATIONS, (ArrayList<BreweryLocation>) breweryLocations);
        BeerNavigationHomeFragment fragment = new BeerNavigationHomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBreweryLocations = new ArrayList<>();

        mBreweryLocations = getArguments().getParcelableArrayList(ARG_BREWERY_LOCATIONS);

        mBreweryDbService = BreweryDbClient.getClient().create(BreweryDbInterface.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_beer_navigation_home, container, false);

        ButterKnife.bind(this, view);

        mHomeBreweriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        if(mBreweryLocationAdapter == null) {
            mBreweryLocationAdapter = new BreweryLocationAdapter(mBreweryLocations);
            mHomeBreweriesRecyclerView.setAdapter(mBreweryLocationAdapter);
        }
        else {
            mBreweryLocationAdapter.notifyDataSetChanged();
        }

//        // handle the data for the beers at a particular brewery
//        if(mBeerAdapter == null) {
//            mBeerAdapter = new BeerAdapter(mBreweryBeers);
//            mBreweryBeersRecyclerView.setAdapter(mBeerAdapter);
//        }
//        else {
//            mBeerAdapter.notifyDataSetChanged();
//        }

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
            beerIconTask.execute(mBreweryLocation.getImagesIcon());
        }

        @Override
        public void completedFetchUrlImageTask(Bitmap bitmap) {
            if(bitmap != null)
                mBreweryImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {
            // TODO: link to the Brewery
            Toast.makeText(getActivity(), "id: " + mBreweryLocation.getBreweryId(), Toast.LENGTH_LONG).show();

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
                    startActivity(BreweryDetailActivity.newIntent(getActivity(), mBrewery, mBreweryBeers));
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
}
