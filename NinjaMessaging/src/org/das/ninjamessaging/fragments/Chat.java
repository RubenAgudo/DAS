package org.das.ninjamessaging.fragments;

import java.util.ArrayList;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.R.id;
import org.das.ninjamessaging.R.layout;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	private ArrayAdapter<String> adaptador;
	private IListFragmentListener listInterface;
	private ArrayList<String> datos;
	private ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adaptador = loadData();
		setListAdapter(adaptador);
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
	 * @return an ArrayAdapter containing all the users.
	 */
	private ArrayAdapter<String> loadData() {
		
		//para sustuir por LaBD
		datos= new ArrayList<String>(100); 
		
		for(int x = 0; x < 100; x++) {
			
			datos.add("chat" + x); 
		}
		
		ArrayAdapter<String> adaptador= new ArrayAdapter<String> (getActivity(),
				android.R.layout.simple_list_item_1, datos);
		return adaptador;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			//unimos la actividad al listener
			listInterface = (IListFragmentListener) activity;
			
		} catch (ClassCastException e) {}
	
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

	public void updateData(String stringExtra) {
		//actionBar.setTitle(actionBar.getTitle() + " " + stringExtra);
		Toast.makeText(getActivity(), stringExtra, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//actionBar = getActivity().getActionBar();
	}

}
