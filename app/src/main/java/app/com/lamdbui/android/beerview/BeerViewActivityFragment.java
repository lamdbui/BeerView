package app.com.lamdbui.android.beerview;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerViewActivityFragment extends Fragment {

    private static final String LOG_TAG = BeerViewActivityFragment.class.getSimpleName();
    private static final String API_KEY = BuildConfig.BREWERY_DB_API_KEY;

    @BindView(R.id.body_text)
    TextView mBodyTextView;
    @BindView(R.id.brewery_recycler_view)
    RecyclerView mBreweryRecyclerView;

    private BreweryAdapter mBreweryAdapter;

    private List<Brewery> mBreweries;

    public BeerViewActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBreweries = new ArrayList<>();

        BreweryDbInterface breweryDbInterface =
                BreweryDbClient.getClient().create(BreweryDbInterface.class);

        Call<BreweryResponse> call = breweryDbInterface.getBreweries(API_KEY, "94122");
        call.enqueue(new Callback<BreweryResponse>() {
            @Override
            public void onResponse(Call<BreweryResponse> call, Response<BreweryResponse> response) {
                // TODO: Maybe check for bad data?
                mBreweries = response.body().getData();
                if(mBreweryAdapter == null)
                    mBreweryAdapter = new BreweryAdapter(mBreweries);
                mBreweryRecyclerView.setAdapter(mBreweryAdapter);
            }

            @Override
            public void onFailure(Call<BreweryResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beer_view, container, false);
        ButterKnife.bind(this, rootView);

        mBreweryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private class BreweryHolder extends RecyclerView.ViewHolder {

        private TextView mBodyTextView;

        private Brewery mBrewery;

        public BreweryHolder(View itemView) {
            super(itemView);

            mBodyTextView = (TextView) itemView.findViewById(R.id.list_item_brewery_body_text);
        }

        public void bind(Brewery brewery) {
            mBrewery = brewery;
            mBodyTextView.setText(mBrewery.toString());
        }
    }

    private class BreweryAdapter extends RecyclerView.Adapter<BreweryHolder> {

        private List<Brewery> mBreweries;

        public BreweryAdapter(List<Brewery> breweries) {
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
            Brewery brewery = mBreweries.get(position);
            holder.bind(brewery);
        }

        @Override
        public int getItemCount() {
            return mBreweries.size();
        }

        public void setBreweries(List<Brewery> breweries) {
            mBreweries = breweries;
        }
    }
}
