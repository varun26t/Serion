package io.nerditall.serion;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;

/**
 * Created by a0_ on 2/7/16.
 */
public class Settings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
