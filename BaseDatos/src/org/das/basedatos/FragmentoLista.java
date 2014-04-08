package org.das.basedatos;

import java.util.ArrayList;

<<<<<<< HEAD
import org.das.basedatos.R;
=======
import or.das.basedatos.R;
>>>>>>> 5e155a82a1c4b1f37fe4296b1acd00fe22c362af

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class FragmentoLista extends ListFragment {
	
	private ArrayAdapter<String> adaptador;
	private ArrayList<String> datos;


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
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_list_fragment, container, false);
		
	}
	
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
//				final int position2 = position;
				AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
				dialog.setTitle("El título del dialog");
				dialog.setMessage("¿Deseas borrar el elemento " + adaptador.getItem(position) + "?");
				dialog.setCancelable(true);
				dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						LaBD.getMiBD(getActivity()).eliminar(position);
						
					}
				});
				dialog.show();
				return true;
			}
			
		});
	}


	public void updateList() {
		adaptador.clear();
		Cursor aCursor = LaBD.getMiBD(getActivity()).seleccionar();
		int id;
		String nombre;
		if(aCursor.moveToFirst()) {
			do {
				id = aCursor.getInt(0);
				nombre = aCursor.getString(1);
	//			datos.add(id + ", " + nombre);
				adaptador.add(id + ", " + nombre);
			} while(aCursor.moveToNext());
		}
	}


}