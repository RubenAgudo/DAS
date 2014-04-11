package org.das.ninjamessaging.fragmentactivities;

import java.util.List;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.activities.DetallesUsuario;
import org.das.ninjamessaging.fragments.Chat;
import org.das.ninjamessaging.utils.LaBD;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
		if (id == R.id.action_settings) {
			return true;
		}
		
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
        List<String> providers = lm.getProviders(true);

        /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
        Location l = null;
        
        double[] gps = obtenerUltimasCoordenadas(lm, providers, l);
        String message = "Latitud: " + gps[0] + " Longitud: " + gps[1];
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        LaBD.getMiBD(getApplicationContext()).anadirMensaje(user, message, 1);
        Chat chat = (Chat) getSupportFragmentManager().findFragmentById(R.id.chat);
        chat.updateList(user);
		
	}

	private double[] obtenerUltimasCoordenadas(LocationManager lm,
			List<String> providers, Location l) {
		for (int i=providers.size()-1; i>=0; i--) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) break;
        }
        
        double[] gps = new double[2];
        if (l != null) {
                gps[0] = l.getLatitude();
                gps[1] = l.getLongitude();
        }
		return gps;
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
