package com.red_beard.android.knots.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.red_beard.android.knots.R;

/**
 * Created by red beard on 27.09.2018.
 test
 */

public class SettingFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String KEY_PREF_TYPE_OF_VIEW = "pref_type_of_view_entries";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        if (key.equals(KEY_PREF_TYPE_OF_VIEW)){
            Preference typeOfViewPref = findPreference(key);
            typeOfViewPref.setSummary(sharedPreferences.getString(key,""));
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
