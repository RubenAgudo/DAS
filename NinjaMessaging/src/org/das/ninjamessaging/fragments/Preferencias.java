package org.das.ninjamessaging.fragments;

import org.das.ninjamessaging.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Preferencias extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
	}

}
