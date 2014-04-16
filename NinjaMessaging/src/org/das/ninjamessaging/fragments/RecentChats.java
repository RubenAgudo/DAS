package org.das.ninjamessaging.fragments;

import java.util.ArrayList;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.utils.LaBD;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecentChats extends ListFragment {

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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		datos = new ArrayList<String>();
		adaptador= new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, datos);
		setListAdapter(adaptador);
		updateList();
		
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		listInterface.onItemSelected(datos.get(position));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_recent_chats, container, false);
	}
	
	/**
	 * This methods loads the users from the BD
	 * @param string 
	 * @return an ArrayAdapter containing all the users.
	 */
	private void updateList() {
		
		adaptador.clear();
		Cursor aCursor = LaBD.getMiBD(getActivity().getApplicationContext()).getRecentChats();
		
		String nombre;
		
		if(aCursor.moveToFirst()) {
			
			do {
				
				nombre = aCursor.getString(0);
				datos.add(nombre);
				
			} while(aCursor.moveToNext());
			aCursor.close();
		}
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			//unimos la actividad al listener
			listInterface = (IListFragmentListener) activity;
			
		} catch (ClassCastException e) {}
	
	}
	
	
	//Autogenerado por android
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
			View rootView = inflater.inflate(R.layout.fragment_recent_chats,
					container, false);
			return rootView;
		}
	}

}
