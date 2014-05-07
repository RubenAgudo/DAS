package org.das.labo10;

import java.io.IOException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private static final String SENDER_ID = "286996031694";
	private GoogleCloudMessaging gcm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		if(checkPlayServices()) {
			Toast.makeText(getApplicationContext(), "Funciona!", Toast.LENGTH_SHORT).show();
		} else {
			Registrarse();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(checkPlayServices()) {
			Toast.makeText(getApplicationContext(), "Funciona!", Toast.LENGTH_SHORT).show();
		} else {
			Registrarse();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				//Dispositivo no configurado. Mostrar ventana de configuraciónde Google Play Services
				//PLAY_SERVICES_RESOLUTION_REQUEST debe valer 9000
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
			} else {
				//Dispositivo no compatible. Terminar la aplicación
				Log.i("Blabla", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}
	
	private void Registrarse() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg="";
				try {
					gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					//SENDER_ID es el número de proyecto que os ha asignado el Google Developer Console
					String regid = gcm.register(SENDER_ID);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}
			
			@Override
			protected void onPostExecute(String msg) {
				Log.i("error", msg + "\n");
			}
			
		}.execute(null, null, null);
	}

}
