package org.das.labo5;

import java.util.Iterator;
import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private LocationManager aManager;
//	private List<String> providers;
	//private TextView aTextView;
//	private EditText editTextMetros;
	private Button buttonSigueme;
	
	private EditText editTextLatitud, editTextLongitud;
	private Location initialLocation;
	private LocationListener aListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initialLocation = new Location("gps");
		
		editTextLatitud = (EditText) findViewById(R.id.editTextLatitud);
		editTextLongitud = (EditText) findViewById(R.id.editTextLongitud);
		
		//aTextView = (TextView) findViewById(R.id.textView1);
//		editTextMetros = (EditText) findViewById(R.id.editTextMetros);
		buttonSigueme = (Button) findViewById(R.id.buttonSigueme);
		buttonSigueme.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				initialLocation.setLatitude(Float.parseFloat(editTextLatitud.getText().toString()));
				initialLocation.setLongitude(Float.parseFloat(editTextLongitud.getText().toString()));
				aManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, aListener);
			}
		});
		
		aManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		aListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
					Toast.makeText(MainActivity.this, "Te has alejado: " + 
						location.distanceTo(initialLocation) + " metros" , Toast.LENGTH_LONG).show();
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
//		providers = aManager.getAllProviders();
		
		//Iterator<String> itr = providers.iterator();
		
//		Criteria loscriterios = setCriteriaForPositioning();
//		String mejorproveedor= aManager.getBestProvider(loscriterios, true);
		
		//aTextView.append("Mejor proveedor: " + mejorproveedor + "\n\n");
		
//		while(itr.hasNext()) {
//			LocationProvider aProvider = aManager.getProvider(itr.next());
//			aTextView.append("Name: " + aProvider.getName() + "\n");
//			aTextView.append("Precision: " + aProvider.getAccuracy() + " \n");
//			aTextView.append("Power requirement: " + aProvider.getPowerRequirement() +" \n");
//			aTextView.append("Supports altitude: " + aProvider.supportsAltitude() +" \n\n");	
//		}
		
		
		
		encenderGPS();
		
		//getLastLocation();
	}

//	private Criteria setCriteriaForPositioning() {
//		Criteria loscriterios = new Criteria();
//		loscriterios.setAccuracy(Criteria.ACCURACY_FINE);
//		loscriterios.setAltitudeRequired(true);
//		return loscriterios;
//	}

	private void encenderGPS() {
		if (!aManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Intent i= new Intent (android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(i);
		} 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
