package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.etsiitgo.GusiAssistant;
import com.example.etsiitgo.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String DEBUG_TAG = "SettingsFragmentDEBUG";

    private static final String LANGUAGE_SP = "es-ES";
    private static final String LANGUAGE_EN = "en-UK";
    private static final String LANGUAGE_FR = "fr-FR";
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat enableGusiVoicePreference = findPreference("enable_gusi_voice");

        if (enableGusiVoicePreference != null) {
            enableGusiVoicePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                GusiAssistant.getInstance(requireActivity()).setEnabled((boolean)newValue);
                return true;
            });
        }

        ListPreference idiomaGusiPreference = findPreference("gusi_language");

        if (idiomaGusiPreference != null) {
            idiomaGusiPreference.setOnPreferenceChangeListener((preference, newValue) -> {

                Log.d(DEBUG_TAG,"LanguageSettingChanged");

                switch (newValue.toString()) {
                    case LANGUAGE_EN:
                        Log.d(DEBUG_TAG,"changeToEnglish");
                        GusiAssistant.getInstance(requireActivity()).setLanguage(GusiAssistant.LANGUAGE_ENGLISH);
                        break;
                    case LANGUAGE_SP:
                        Log.d(DEBUG_TAG,"changeToSpanish");
                        GusiAssistant.getInstance(requireActivity()).setLanguage(GusiAssistant.LANGUAGE_SPANISH);
                        break;
                    case LANGUAGE_FR:
                        Log.d(DEBUG_TAG,"changeToFrench");
                        GusiAssistant.getInstance(requireActivity()).setLanguage(GusiAssistant.LANGUAGE_FRENCH);
                        break;
                }

                return true;
            });
        }
    }

}
