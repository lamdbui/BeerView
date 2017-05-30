package app.com.lamdbui.android.beerview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.lamdbui.android.beerview.data.BreweryContract;
import app.com.lamdbui.android.beerview.data.BreweryDbUtils;
import app.com.lamdbui.android.beerview.model.Beer;
import app.com.lamdbui.android.beerview.model.Brewery;
import app.com.lamdbui.android.beerview.model.BreweryLocation;
import app.com.lamdbui.android.beerview.network.BeerListResponse;
import app.com.lamdbui.android.beerview.network.BeerResponse;
import app.com.lamdbui.android.beerview.network.BreweryLocationResponse;
import app.com.lamdbui.android.beerview.network.BreweryResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerViewActivityFragment extends Fragment
    implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = BeerViewActivityFragment.class.getSimpleName();
    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

    private static final int LOADER_BREWERY = 0;

    @BindView(R.id.map_button)
    Button mMapButton;
//    @BindView(R.id.show_beer_button)
//    Button mShowBeerButton;
    @BindView(R.id.show_beer_button2)
    Button mShowBeerButton2;
    @BindView(R.id.show_brewery)
    Button mShowBrewery;
    @BindView(R.id.show_navigation_home)
    Button mShowNavigationHome;
    @BindView(R.id.brewery_recycler_view)
    RecyclerView mBreweryRecyclerView;

    private BreweryAdapter mBreweryAdapter;

    private List<BreweryLocation> mBreweries;
    private Beer mBeer;
    private Brewery mBrewery;
    private List<Beer> mBreweryBeers;

    public BeerViewActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBreweries = new ArrayList<>();
        mBreweryBeers = new ArrayList<>();

        BreweryDbInterface breweryDbService =
                BreweryDbClient.getClient().create(BreweryDbInterface.class);

//        Call<BreweryLocationResponse> call = breweryDbService.getBreweries(API_KEY, "94122");
//        call.enqueue(new Callback<BreweryLocationResponse>() {
//            @Override
//            public void onResponse(Call<BreweryLocationResponse> call, Response<BreweryLocationResponse> response) {
//                // TODO: Maybe check for bad data?
//                mBreweries = response.body().getData();
//                updateUI();
//            }
//
//            @Override
//            public void onFailure(Call<BreweryLocationResponse> call, Throwable t) {
//                Log.e(LOG_TAG, t.toString());
//            }
//        });

        Call<BeerListResponse> callBeersAtBrewery = breweryDbService.getBeersAtBrewery("BSsTGw", API_KEY, "Y");
        callBeersAtBrewery.enqueue(new Callback<BeerListResponse>() {
            @Override
            public void onResponse(Call<BeerListResponse> call, Response<BeerListResponse> response) {
                mBreweryBeers = response.body().getBeerList();
            }

            @Override
            public void onFailure(Call<BeerListResponse> call, Throwable t) {
            }
        });

        Call<BeerResponse> callBeerById = breweryDbService.getBeer("9UG4pg", API_KEY, "Y");
        callBeerById.enqueue(new Callback<BeerResponse>() {
            @Override
            public void onResponse(Call<BeerResponse> call, Response<BeerResponse> response) {
                // TODO: Use real data here
                // Pliny the Younger
                mBeer = response.body().getBeer();
            }

            @Override
            public void onFailure(Call<BeerResponse> call, Throwable t) {

            }
        });

        Call<BreweryResponse> callBreweryById = breweryDbService.getBrewery("BSsTGw", API_KEY, "Y");
        callBreweryById.enqueue(new Callback<BreweryResponse>() {
            @Override
            public void onResponse(Call<BreweryResponse> call, Response<BreweryResponse> response) {
                mBrewery = response.body().getBrewery();
            }

            @Override
            public void onFailure(Call<BreweryResponse> call, Throwable t) {

            }
        });

        Call<BreweryLocationResponse> callBreweriesNearby = breweryDbService.getBreweriesNearby(API_KEY, 37.774929, -122.419416);
        callBreweriesNearby.enqueue(new Callback<BreweryLocationResponse>() {
            @Override
            public void onResponse(Call<BreweryLocationResponse> call, Response<BreweryLocationResponse> response) {
                mBreweries = response.body().getBreweries();

                // Add breweries to the database
                ContentValues[] breweryValues = new ContentValues[mBreweries.size()];
                for(int i = 0; i < mBreweries.size(); i++) {
                    breweryValues[i] = BreweryDbUtils.convertBreweryToContentValues(mBreweries.get(i));
                }

                getContext().getContentResolver().bulkInsert(
                        BreweryContract.BreweryTable.CONTENT_URI,
                        breweryValues);

                // *** TEST CODE - get them back
                Cursor cursorResults = getContext().getContentResolver().query(
                        BreweryContract.BreweryTable.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                if(cursorResults != null && cursorResults.getCount() > 0) {
                    cursorResults.moveToFirst();

                    List<BreweryLocation> cursorBreweries = new ArrayList<BreweryLocation>();

                    while(!cursorResults.isAfterLast()) {

                        cursorBreweries.add(BreweryDbUtils.convertCursorToBrewery(cursorResults));

                        cursorResults.moveToNext();
                    }
                }

                updateUI();
            }

            @Override
            public void onFailure(Call<BreweryLocationResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });

        // init our Loader
        getLoaderManager().initLoader(LOADER_BREWERY, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beer_view, container, false);
        ButterKnife.bind(this, rootView);

        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BrewViewMapsActivity.newIntent(getActivity(), mBreweries));
            }
        });

//        mShowBeerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(BeerDetailActivity.newIntent(getActivity(), mBeer));
//            }
//        });

        mShowBeerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BeerDetailActivity.newIntent(getActivity(), mBeer));
            }
        });

        mShowBrewery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(BreweryDetailActivity.newIntent(getActivity(), mBrewery));
                startActivity(BreweryDetailActivity.newIntent(getActivity(), mBrewery, mBreweryBeers));
            }
        });

        mShowNavigationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), BeerNavigationActivity.class));
                startActivity(BeerNavigationActivity.newIntent(getActivity(), mBeer, mBreweries));
            }
        });

        mBreweryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // test horizontal scrolling
        //mBreweryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        return rootView;
    }

    public void updateUI() {
        if(mBreweryAdapter == null) {
            mBreweryAdapter = new BreweryAdapter(mBreweries);
            mBreweryRecyclerView.setAdapter(mBreweryAdapter);
        }
        else {
            mBreweryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //return null;

        CursorLoader loader = new CursorLoader(
                getActivity(),
                BreweryContract.BreweryTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null && data.getCount() > 0) {
            data.moveToFirst();
            List<BreweryLocation> cursorBreweries = new ArrayList<BreweryLocation>();

            while(!data.isAfterLast()) {

                cursorBreweries.add(BreweryDbUtils.convertCursorToBrewery(data));

                data.moveToNext();
            }

            mBreweries = cursorBreweries;
            updateUI();
            //mBreweryAdapter.setBreweries(mBreweries);
            //mBreweryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class BreweryHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private TextView mBodyTextView;

        private BreweryLocation mBreweryLocation;

        public BreweryHolder(View itemView) {
            super(itemView);

            mBodyTextView = (TextView) itemView.findViewById(R.id.list_item_brewery_body_text);
            mBodyTextView.setOnClickListener(this);
        }

        public void bind(BreweryLocation breweryLocation) {
            mBreweryLocation = breweryLocation;
            mBodyTextView.setText(mBreweryLocation.toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = BreweryActivity.newIntent(getActivity(), mBreweryLocation);
            startActivity(intent);
        }
    }

    private class BreweryAdapter extends RecyclerView.Adapter<BreweryHolder> {

        private List<BreweryLocation> mBreweries;

        public BreweryAdapter(List<BreweryLocation> breweries) {
            mBreweries = breweries;
        }

        @Override
        public BreweryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_brewery, parent, false);

            return new BreweryHolder(view);
        }

        @Override
        public void onBindViewHolder(BreweryHolder holder, int position) {
            BreweryLocation breweryLocation = mBreweries.get(position);
            holder.bind(breweryLocation);
        }

        @Override
        public int getItemCount() {
            return mBreweries.size();
        }

        public void setBreweries(List<BreweryLocation> breweries) {
            mBreweries = breweries;
        }
    }
}
