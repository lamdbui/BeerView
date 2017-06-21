package app.com.lamdbui.android.beerview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lamdbui on 6/20/17.
 */

public class BeerNavigationSettingsFragment extends Fragment {

    public static final String TAG = BeerNavigationSettingsFragment.class.getSimpleName();

    @BindView(R.id.pref_postalcode_textview)
    TextView mPrefPostalCodeTextView;

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
        //return super.onCreateView(inflater, container, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_beer_navigation_home, container, false);
        View view = inflater.inflate(R.layout.fragment_brew_settings, container, false);

        ButterKnife.bind(this, view);

        mPrefPostalCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                input.setHint("Please enter postal code here");
                input.setMaxLines(1);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                final AlertDialog postalCodeAlertDialog = new AlertDialog.Builder(getActivity())
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor editor = mSettings.edit();
                                // test code
                                //String postalCode = "92612";
                                // TODO: Do some validation of the input here
                                String postalCode = input.getText().toString();
                                editor.putString(getString(R.string.pref_location_postal_code), postalCode);
                                editor.apply();
                                Toast.makeText(getActivity(), "Saved! - " + postalCode, Toast.LENGTH_SHORT).show();
                                updateUI();
                            }
                        })
                        .create();

//                View dialogView = inflater.inflate(R.layout.dialog_edittext, container, false);
//                final EditText dialogEditText = (EditText) dialogView.findViewById(R.id.dialog_edittext);
//                //final Dialog dialog = new Dialog(getActivity());
//                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
//                        .setNegativeButton("Cancel", null)
//                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                SharedPreferences.Editor editor = mSettings.edit();
//                                // test code
//                                //String postalCode = "92612";
//                                // TODO: Do some validation of the input here
//                                String postalCode = dialogEditText.getText().toString();
//                                editor.putString("default_postalCode", postalCode);
//                                editor.apply();
//                                Toast.makeText(getActivity(), "Saved! - " + postalCode, Toast.LENGTH_SHORT).show();
//                                updateUI();
//                            }
//                        })
//                        .setView(R.layout.dialog_edittext)
//                        .create();
//
//                //dialog.setContentView(R.layout.dialog_edittext);
//                dialog.show();
//                //final EditText dialog_edittext = (EditText) view.findViewById(R.id.dialog_edittext);
//                //postalCodeAlertDialog.setContentView(R.layout.dialog_edittext);
                postalCodeAlertDialog.setTitle("Postal Code");
                postalCodeAlertDialog.setMessage("Default location");
                //postalCodeAlertDialog.setView(dialog_edittext);
                postalCodeAlertDialog.setView(input);
                postalCodeAlertDialog.show();
            }
        });

        updateUI();

        return view;
    }

    public void updateUI() {
        mPrefPostalCodeTextView.setText(mSettings.getString("default_postalCode", "NOT AVAILABLE"));
    }
}
