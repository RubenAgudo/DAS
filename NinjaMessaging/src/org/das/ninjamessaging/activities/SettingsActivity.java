package org.das.ninjamessaging.activities;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.services.NotificationService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class SettingsActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		if (savedInstanceState == null) {
			// Display the fragment as the main content.
	        getFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new SettingsFragment())
	                .commit();
		}
	}
	
	public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.preferences);
	        
	    }

		@Override
		public PreferenceManager getPreferenceManager() {
			// TODO Auto-generated method stub
			return super.getPreferenceManager();
		}

		@Override
		public PreferenceScreen getPreferenceScreen() {
			// TODO Auto-generated method stub
			return super.getPreferenceScreen();
		}

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			getPreferenceManager().getSharedPreferences()
			.unregisterOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if(key.equals("pref_service")) {
				if(!sharedPreferences.getBoolean(key, true)) {
					getActivity().getApplicationContext().stopService(new Intent(getActivity().getApplicationContext(), NotificationService.class));
				} else {
					getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), NotificationService.class));
				}
				
			}
			
		}
	}

}
