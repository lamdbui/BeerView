package app.com.lamdbui.android.beerview;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by lamdbui on 6/20/17.
 */

public class BeerNavigationSettingsPreferenceFragment extends PreferenceFragment {

    public static final String TAG = BeerNavigationSettingsPreferenceFragment.class.getSimpleName();

    public static BeerNavigationSettingsPreferenceFragment newInstance() {
        return new BeerNavigationSettingsPreferenceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
    }
}
