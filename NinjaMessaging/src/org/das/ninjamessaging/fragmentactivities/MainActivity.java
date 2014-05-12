package org.das.ninjamessaging.fragmentactivities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.activities.Contacts;
import org.das.ninjamessaging.activities.SettingsActivity;
import org.das.ninjamessaging.fragments.Chat;
import org.das.ninjamessaging.fragments.RecentChats.IListFragmentListener;
import org.das.ninjamessaging.services.NotificationService;
import org.das.ninjamessaging.utils.ConexionBD;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
	private static final String SENDER_ID = "951286565886";
	private GoogleCloudMessaging gcm;
	private static String REGID;
	
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
		
		if(checkPlayServices()) {
			if((REGID = sharedPref.getString("REGID", null)) == null) {
				Registrarse();
			}
		}
		
		
		obtenerDatosDeLaPantalla();
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		checkPlayServices();
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
	
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				//Dispositivo no configurado. Mostrar ventana de configuraciónde Google Play Services
				//PLAY_SERVICES_RESOLUTION_REQUEST debe valer 9000
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
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
					gcm = GoogleCloudMessaging.getInstance(getBaseContext());
					//SENDER_ID es el número de proyecto que os ha asignado el Google Developer Console
					String regid = gcm.register(SENDER_ID);
					
					SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					
					sharedPref.edit()
					.putString("REGID", regid)
					.commit();
					return regid;

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
				try {
					ConexionBD.getMiConexionBD(getApplicationContext()).registrar(msg);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
		}.execute(null, null, null);
	}
	
}
