package org.das.labo6;

import org.das.labo6.FragmentList.IListFragmentListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Menu;
import android.view.Surface;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity implements IListFragmentListener {

	private WindowManager mWindowManager;
	private Display mDisplay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWindowManager =  (WindowManager) getSystemService(WINDOW_SERVICE);
	    mDisplay = mWindowManager.getDefaultDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemSelected(String item) {
		
		//boolean existeelotrofragment =
		//		(getSupportFragmentManager().findFragmentById(R.id.fragment2) != null);
		
		if(mDisplay.getRotation() != Surface.ROTATION_0 ||
				mDisplay.getRotation() != Surface.ROTATION_180) {
			FragmentDetails details = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragment2);
			details.updateData(item);
		} else {
			Intent i = new Intent(MainActivity.this, Details.class);
			i.putExtra("opcionSeleccionada", item);
			startActivity(i);
		}
		
		
		
	}

}
