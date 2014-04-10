package org.das.ninjamessaging.activities;

import java.util.ArrayList;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.R.id;
import org.das.ninjamessaging.R.layout;
import org.das.ninjamessaging.R.menu;
import org.das.ninjamessaging.fragmentactivities.ChatActivity;
import org.das.ninjamessaging.utils.LaBD;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;

public class Contacts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
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

		private ListView listContacts;
		private ArrayList<String> datos;
		private ArrayAdapter<String> adaptador;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_contacts,
					container, false);
			
			listContacts = (ListView) rootView.findViewById(R.id.listContactos);		
			datos = new ArrayList<String>();
			adaptador= new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, datos);
			listContacts.setAdapter(adaptador);
			updateList();
			
			listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent i = new Intent(getActivity(), ChatActivity.class);
					i.putExtra("opcionSeleccionada", adaptador.getItem(position));
					startActivity(i);
					
				}
			});
			
			
			return rootView;
		}

		private void updateList() {
			
			adaptador.clear();
			Cursor aCursor = LaBD.getMiBD(getActivity()).getUsers();
			
			String nombre;
			
			if(aCursor.moveToFirst()) {
				
				do {
					
					nombre = aCursor.getString(0);
					datos.add(nombre);
					
				} while(aCursor.moveToNext());
				
			}
			
		}
	}

}
