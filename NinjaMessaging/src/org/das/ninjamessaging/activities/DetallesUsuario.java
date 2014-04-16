package org.das.ninjamessaging.activities;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.utils.LaBD;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DetallesUsuario extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_usuario);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalles_usuario, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch(id){
		case R.id.editarUsuario:
			editarUsuario(getIntent().getStringExtra("detallesDe"));
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void editarUsuario(final String detallesDe) {
		LayoutInflater inflater = getLayoutInflater();
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Editar datos de: " + detallesDe);
		final View dialogView = inflater.inflate(R.layout.editar_usuario, null);
		dialog.setView(dialogView);
		dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText nombreReal = (EditText) dialogView.findViewById(R.id.editTextNombreReal);
				EditText apellido = (EditText) dialogView.findViewById(R.id.editTextApellido);
				EditText telefono = (EditText) dialogView.findViewById(R.id.editTextTelefono);
				
				String nombreRealString = nombreReal.getText().toString();
				String apellidoString = apellido.getText().toString();
				String telefonoString = telefono.getText().toString();
				
				LaBD.getMiBD(getApplicationContext()).actualizarInfoUsuario(detallesDe, nombreRealString, apellidoString, telefonoString);
				
			}
		});
		dialog.setCancelable(true);
		dialog.show();
		
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private String detallesDe;
		private TextView usuario;
		private TextView nombreReal;
		private TextView apellido;
		private TextView telefono;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_detalles_usuario, container, false);
			
			enlazar(rootView);
			rellenarVista();
			
			
			
			return rootView;
		}

		private void rellenarVista() {
			Cursor aCursor = LaBD.getMiBD(getActivity()).getUserInfo(detallesDe);
			
			if(aCursor.moveToFirst()) {
				usuario.setText(aCursor.getString(0));
				nombreReal.setText(aCursor.getString(1));
				apellido.setText(aCursor.getString(2));
				telefono.setText(aCursor.getString(3));
			}
			aCursor.close();
			
		}

		private void enlazar(View rootView) {
			detallesDe = getActivity().getIntent().getStringExtra("detallesDe");
			usuario = (TextView) rootView.findViewById(R.id.nombreUsuario);
			nombreReal = (TextView) rootView.findViewById(R.id.nombreReal);
			apellido = (TextView) rootView.findViewById(R.id.apellido);
			telefono = (TextView) rootView.findViewById(R.id.telefono);
		}
	}

}
