package org.das.labo3;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

	private Button btnJugar;
	private Button btnInstrucciones;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnJugar = (Button)findViewById(R.id.btnJugar);
		btnInstrucciones = (Button)findViewById(R.id.btnInstrucciones);
		
		btnJugar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Intent newActivity = new Intent(getApplicationContext(), SeleccionaDificultad.class);
				newActivity.putExtra("test", 3);
				startActivity(newActivity);*/
				chooseDifficulty();
				
			}
			
			/**
			 * This method shows a dialog showing the difficulty options.
			 */
			private void chooseDifficulty() {
				AlertDialog.Builder eleccion= new AlertDialog.Builder(Main.this);
				eleccion.setTitle("Elija una dificultad");
				final CharSequence[] textOpciones = {"Facil", "Medio", "Dificil"};
				final int[][] opciones = {{2, 20}, {4, 10}, {8, 5}};
				eleccion.setItems(textOpciones, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent newActivity = new Intent(getApplicationContext(), Jugar.class);
						newActivity.putExtra("Dificultad", textOpciones[which]);
						newActivity.putExtra("opciones", opciones[which]);
						startActivity(newActivity);
					}
				});
				
				eleccion.show();
			}
		});
		
		
		btnInstrucciones.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder aDialog = new AlertDialog.Builder(Main.this);
				aDialog.setTitle("Instrucciones");
				aDialog.setMessage(R.string.textInstrucciones);
				aDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				aDialog.show();
				
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.labo3, menu);
		return true;
	}

	

}
