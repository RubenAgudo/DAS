package org.das.labo3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class Jugar extends Activity {

	private TextView nivel, intentosRestantes;
	private ExpandableListView exListViewNumero1,
								exListViewNumero2, 
								exListViewNumero3, 
								exListViewNumero4;
	private int[] digitos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jugar);
		// Show the Up button in the action bar.
		setupActionBar();
		
		nivel = (TextView) findViewById(R.id.textViewNivelSeleccionado);
		intentosRestantes = (TextView) findViewById(R.id.textViewIntentosRestantes);
		
		Bundle extras = getIntent().getExtras();
		
		if(extras != null) {
			String dificultad = extras.getString("Dificultad");
			int[] opciones = extras.getIntArray("opciones");
			nivel.setText(dificultad);
			intentosRestantes.setText(opciones[1] + "");
			digitos = new int[opciones[0]];
			
			for(int x = 0; x < digitos.length; x++) {
				digitos[x] = (int)Math.random()*10;
			}
		}
		
		
		
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selecciona_dificultad, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
