package app.com.lamdbui.android.beerview;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerViewActivityFragment extends Fragment {

    private TextView mBodyTextView;

    private String mBreweryStr;

    public BeerViewActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // test data
        //List<Brewery> breweries = new ArrayList<>();
        Brewery brewery = new Brewery();
        brewery.setId(0);
        brewery.setName("TapLam");
        brewery.setStreetAddress("1234 Hoppy Lane");
        brewery.setLocality("Beerland");
        brewery.setRegion("BL");
        brewery.setPostalCode("99991");
        brewery.setPhone("555-111-222");
        brewery.setWebsite("http://www.beer.com");
        brewery.setLatitude(37.774929);
        brewery.setLongitude(-122.419416);

        mBreweryStr = brewery.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beer_view, container, false);

        mBodyTextView = (TextView) rootView.findViewById(R.id.body_text);
        mBodyTextView.setText(mBreweryStr);

        return rootView;
    }
}
