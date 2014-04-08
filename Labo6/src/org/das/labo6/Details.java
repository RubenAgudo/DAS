package org.das.labo6;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Details extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.fragment_activity_details);
		FragmentDetails fd = (FragmentDetails) getSupportFragmentManager().
				findFragmentById(R.id.fragmentDetails);
		fd.updateData(getIntent().getStringExtra("opcionSeleccionada"));
	}

	
	
}
