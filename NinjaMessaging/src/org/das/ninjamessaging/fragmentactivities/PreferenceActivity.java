package org.das.ninjamessaging.fragmentactivities;

import org.das.ninjamessaging.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class PreferenceActivity extends FragmentActivity {

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 getLayoutInflater().inflate(R.xml.preferencias, null);
   

	 }
	
}
