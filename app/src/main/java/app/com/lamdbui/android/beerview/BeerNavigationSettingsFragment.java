package app.com.lamdbui.android.beerview;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lamdbui on 6/20/17.
 */

public class BeerNavigationSettingsFragment extends Fragment {

    public static final String TAG = BeerNavigationSettingsFragment.class.getSimpleName();

    @BindView(R.id.pref_postalcode_textview)
    TextView mPrefPostalCodeTextView;
    @BindView(R.id.settings_postal_code_layout)
    LinearLayout mSettingsPostalCodeLayout;

    private SharedPreferences mSettings;

    public static  BeerNavigationSettingsFragment newInstance() {
        return new BeerNavigationSettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brew_settings, container, false);

        ButterKnife.bind(this, view);

        mSettingsPostalCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                final View innerView = view;
                input.setHint(getString(R.string.setting_postal_code_hint));
                input.setMaxLines(1);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog postalCodeAlertDialog = new AlertDialog.Builder(getActivity())
                        .setNegativeButton(getString(R.string.dialog_cancel), null)
                        .setPositiveButton(getString(R.string.dialog_save), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor editor = mSettings.edit();
                                String postalCode = input.getText().toString();
                                if(!postalCode.equals("")) {
                                    editor.putString(getString(R.string.pref_location_postal_code), postalCode);
                                    editor.apply();
                                    Snackbar.make(innerView, getString(R.string.setting_saved), Snackbar.LENGTH_SHORT)
                                            .setAction("SavedPostalCode", null).show();
                                }
                                else {
                                    Snackbar.make(innerView, getString(R.string.setting_postal_code_error), Snackbar.LENGTH_SHORT)
                                            .setAction("SavedPostalCode", null).show();
                                }
                                updateUI();
                            }
                        })
                        .create();

                postalCodeAlertDialog.setTitle(R.string.setting_postal_code);
                postalCodeAlertDialog.setMessage(getString(R.string.setting_postal_code_description));
                postalCodeAlertDialog.setView(input);
                postalCodeAlertDialog.show();
            }
        });

        updateUI();

        return view;
    }

    public void updateUI() {
        String postalCode = mSettings.getString(getString(R.string.pref_location_postal_code), getString(R.string.pref_location_none));
        if(!postalCode.equals(""))
            mPrefPostalCodeTextView.setText(postalCode);
        else
            mPrefPostalCodeTextView.setText(getString(R.string.setting_postal_code_hint));
    }
}
