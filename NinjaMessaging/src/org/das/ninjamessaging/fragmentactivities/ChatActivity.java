package org.das.ninjamessaging.fragmentactivities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.activities.DetallesUsuario;
import org.das.ninjamessaging.fragments.Chat;
import org.das.ninjamessaging.utils.LaBD;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ChatActivity extends FragmentActivity {
	
	private String user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_activity);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		user = getIntent().getStringExtra("opcionSeleccionada");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
			case R.id.VerDetallesUsuario:
				verDetallesDelUsuario();
				break;
			case R.id.ExportarConversacion:
				exportarConversacion();
				break;
			case R.id.EnviarLocalizacion:
				enviarLocalizacion();
			default:
				return super.onOptionsItemSelected(item);
		}
	
		return true;
	}

	private void enviarLocalizacion() {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
		
		encenderGPS(lm);
		
        List<String> providers = lm.getProviders(true);

        String message = obtenerUltimasCoordenadas(lm, providers);
        
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        LaBD.getMiBD(getApplicationContext()).anadirMensaje(user, message, 1);
        Chat chat = (Chat) getSupportFragmentManager().findFragmentById(R.id.chat);
        chat.updateList(user);
		
	}
	
	private void encenderGPS(LocationManager lm) {
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Intent i= new Intent (android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(i);
		} 
		
	}

	private String obtenerUltimasCoordenadas(LocationManager lm,
			List<String> providers) {
		Location l = null;
		for (int i=providers.size()-1; i>=0; i--) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) break;
        }
        
        String resultado = null;
        if (l != null) {
            resultado = getAddressFromLocation(l);
        }
		return resultado;
	}
	
	/**
	 * Metodo que transforma una localizacion en una direccion.
	 * 
	 * Si no puede obtener la direccion, devuelve la latitud y longitud.
	 * @param location
	 * @return
	 */
	private String getAddressFromLocation(
	        Location location) {
	    
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());   
        String result = null;
        try {
            List<Address> list = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);
            if (list != null && list.size() > 0) {
                Address address = list.get(0);
                // sending back first address line and locality
                result = address.getAddressLine(0) + ", " + address.getLocality();
            }
        } catch (IOException e) {
            result = "Latitud: " + location.getLatitude() + " Longitud: " + location.getLongitude();
        }
        
        return result;
    }

	private void exportarConversacion() {
		String mensaje = "Chat exportado correctamente";
		LaBD.getMiBD(getApplicationContext()).exportarChat(user);
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
	}

	private void verDetallesDelUsuario() {
		Intent i = new Intent(getApplicationContext(), DetallesUsuario.class);
		i.putExtra("detallesDe", user);
		startActivity(i);
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ActionBar actionBar;
		private String hablandoCon;
		
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_chat_activity, container,
					false);
			
			inicializarActionBar();

			return rootView;
		}

		private void inicializarActionBar() {
			hablandoCon = getActivity().getIntent().getStringExtra("opcionSeleccionada");
			
			actionBar = getActivity().getActionBar();
			actionBar.setTitle(getActivity().getString(R.string.title_activity_chat) +
					" " + hablandoCon);
		}
		
	}
	
	

}
