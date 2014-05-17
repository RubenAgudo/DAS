package org.das.ninjamessaging.fragmentactivities;

import org.das.ninjamessaging.R;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MapActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_map, container,
					false);

			GoogleMap mapa = ((SupportMapFragment)getActivity()
					.getSupportFragmentManager()
					.findFragmentById(R.id.elmapa)).getMap();
			
			Bundle extras = getActivity().getIntent().getExtras();
			
			double lat = extras.getDouble("lat");
			double lng = extras.getDouble("long");
			LatLng latLong = new LatLng(lat, lng);
			
			mapa.addMarker(new MarkerOptions()
				.position(latLong)
				.title("Descripción del marcador"));
			
			CameraPosition camPos =	new	CameraPosition.Builder()
				.target(latLong)
				.zoom(18)
				.bearing(0)
				.tilt(0)
				.build();
			
			CameraUpdate actualizar = CameraUpdateFactory.newCameraPosition(camPos);
			mapa.moveCamera(actualizar);
			return rootView;
		}
	}

}
