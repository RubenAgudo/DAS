package org.das.ninjamessaging.fragments;

import java.util.ArrayList;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.R.id;
import org.das.ninjamessaging.R.layout;
import org.das.ninjamessaging.utils.LaBD;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Build;

public class Chat extends ListFragment {

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

	/**
	 * Interface to communicate with all the FragmentActivity
	 * using this ListFragment
	 * @author Rub�n
	 *
	 */
	public interface IListFragmentListener {
		void onItemSelected(String item);
	}

	//atributtes
	private IListFragmentListener listInterface;
	private ArrayList<String> datos;
	private ArrayAdapter<String> adaptador;
	private Button enviar;
	private EditText mensaje;
	private String hablandoCon;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		hablandoCon = getActivity().getIntent().getStringExtra("opcionSeleccionada");
		datos = new ArrayList<String>();
		adaptador= new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, datos);
		setListAdapter(adaptador);
		updateList(hablandoCon);		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		listInterface.onItemSelected(datos.get(position));
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recent_chats, container, false);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			//unimos la actividad al listener
			listInterface = (IListFragmentListener) activity;
			
		} catch (ClassCastException e) {}
	
	}
	
	public void updateList(String user) {

		ArrayAdapter<String> adaptador = new ArrayAdapter<String> (getActivity(),
				android.R.layout.simple_expandable_list_item_1, datos);
		
		Cursor aCursor = LaBD.getMiBD(getActivity()).getMessagesWithUser(user);
		String nombre;
		String texto;
		int enviadoPorMi = 0; //0 = enviado por el otro, 1 = enviado por mi
		
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
