package org.das.labo6;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentList extends ListFragment {
	public interface IListFragmentListener {
		void onItemSelected(String item);
	}

	private ArrayAdapter<String> adaptador;
	private IListFragmentListener aList;
	private ArrayList<String> datos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adaptador = loadData();
		setListAdapter(adaptador);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		aList.onItemSelected(datos.get(position));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_list, container, false);
	}
	
	private ArrayAdapter<String> loadData() {
		datos= new ArrayList<String>(100); 
		
		for(int x = 0; x < 100; x++) {
			
			datos.add("opcion" + x); 
		}
		
		ArrayAdapter<String> adaptador= new ArrayAdapter<String> (getActivity(),
				android.R.layout.simple_list_item_1, datos);
		return adaptador;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			aList = (IListFragmentListener) activity;
			
		} catch (ClassCastException e) {}
	
	}
	
	

}
