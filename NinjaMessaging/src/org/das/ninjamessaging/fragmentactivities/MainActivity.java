package org.das.ninjamessaging.fragmentactivities;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.activities.Contacts;
import org.das.ninjamessaging.activities.SettingsActivity;
import org.das.ninjamessaging.fragments.Chat;
import org.das.ninjamessaging.fragments.RecentChats.IListFragmentListener;
import org.das.ninjamessaging.services.NotificationService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends FragmentActivity implements IListFragmentListener {

	private WindowManager mWindowManager;
	private Display mDisplay;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean startService = sharedPref.getBoolean(SettingsActivity.PREF_SERVICE, true);
		
		if(startService) {
			
			Intent i = new Intent(getApplicationContext(), NotificationService.class);
			startService(i);
		}
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		obtenerDatosDeLaPantalla();
		
	}

	private void obtenerDatosDeLaPantalla() {
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
			case R.id.anadirChat:
				abrirContactos();
				break;
			case R.id.preferences:
				Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
				startActivity(i);
			default:
				return super.onOptionsItemSelected(item);
		}
		
		return true;
		
	}

	private void abrirContactos() {
		Intent i = new Intent(getApplicationContext(), Contacts.class);
		startActivity(i);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			
			return rootView;
		}
	}

	@Override
	public void onItemSelected(String item) {
		
		//si la pantalla esta en apaisado mostramos los dos fragments, si no, llamamos al otro fragment
		if(mDisplay.getRotation() != Surface.ROTATION_0 &&
				mDisplay.getRotation() != Surface.ROTATION_180) {
			Chat chatDetails = (Chat) getSupportFragmentManager().findFragmentById(R.id.landMessages);
			chatDetails.updateList(item);
		} else {
			Intent i = new Intent(MainActivity.this, ChatActivity.class);
			i.putExtra("opcionSeleccionada", item);
			startActivity(i);
		}
		
	}
	
	

}
