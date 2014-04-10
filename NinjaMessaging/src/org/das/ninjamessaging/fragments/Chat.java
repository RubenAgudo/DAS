package org.das.ninjamessaging.fragments;

import java.util.ArrayList;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.utils.LaBD;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Chat extends Fragment {

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_contacts);
//
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.contacts, menu);
//		return true;
//	}

	//atributtes
	private Button enviar;
	private EditText mensaje;
	private ListView listMessages;
	private ArrayList<String> datos;
	private ArrayAdapter<String> adaptador;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View aView = inflater.inflate(R.layout.fragment_chat, container, false);
		
		String hablandoCon = getActivity().getIntent().getStringExtra("opcionSeleccionada");
		datos = new ArrayList<String>();
		adaptador= new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, datos);
		
		mensaje = (EditText) aView.findViewById(R.id.message);
		enviar = (Button) aView.findViewById(R.id.send);
		
		listMessages = (ListView) aView.findViewById(R.id.listMessages);
		listMessages.setAdapter(adaptador);

		
		updateList(hablandoCon);
		
		enviar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!mensaje.getText().equals("")) {
					String hablandoCon = getActivity().getIntent().getStringExtra("opcionSeleccionada");
					LaBD.getMiBD(getActivity()).anadirMensaje(hablandoCon, 
							mensaje.getText().toString(), 1);
					mensaje.setText("");
					updateList(hablandoCon);
				}
				
			}
		});
		
		return aView;
	}

	/**
	 * Metodo que actualiza la lista de mensajes
	 * @param user indica la persona con la que estamos chateando
	 */
	public void updateList(String user) {
		
		Cursor aCursor = LaBD.getMiBD(getActivity()).getMessagesWithUser(user);
		String nombre;
		String texto;
		int enviadoPorMi = 0; //0 = enviado por el otro, 1 = enviado por mi
		adaptador.clear();
		if(aCursor.moveToFirst()) {
			
			do {
				
				nombre = aCursor.getString(0);
				texto = aCursor.getString(1);
				enviadoPorMi = aCursor.getInt(2);
				if(enviadoPorMi == 0) {
					adaptador.add(nombre + ": " + texto);
				} else {
					adaptador.add("Yo: " + texto);
				}
				
				
			} while(aCursor.moveToNext());
			
		}
	}
	
	
	//Generado por android

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
			View rootView = inflater.inflate(R.layout.fragment_chat, container,
					false);
			
			
			
			return rootView;
		}
		
		
		
		
	}

}
