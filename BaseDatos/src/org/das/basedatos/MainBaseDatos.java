package org.das.basedatos;

import or.das.basedatos.R;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainBaseDatos extends FragmentActivity {
	
	private EditText campoTexto;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_bd);
		campoTexto = (EditText) findViewById(R.id.editText1);
		
		
	}
	
	
		
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				AlertDialog.Builder as = new AlertDialog.Builder(MainBaseDatos.this);
				as.setTitle("Ajustes");
				as.setMessage("Este mensaje es de ajustes");
				as.show();
				
				break;
			case R.id.Anadir:
				insertar(campoTexto.getText().toString());
				
				
			
				break;
			case R.id.Eliminar:
				eliminar(campoTexto.getText().toString());

				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		
		actualizarLista();
		return true;
				
		
	}

	private void actualizarLista() {
		
		FragmentoLista lista = (FragmentoLista) getSupportFragmentManager().findFragmentById(R.id.fragment1);
		lista.updateList();
			
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_bd, menu);
		return true;
	}
	
	private void insertar(String pCadena){
		LaBD.getMiBD(this).insertar(pCadena);
		
	}
	
	private void modificar(String[] pCadena){
		
	}
	
	private void eliminar(String pCadena){
		LaBD.getMiBD(this).eliminar(pCadena);
	}

}

