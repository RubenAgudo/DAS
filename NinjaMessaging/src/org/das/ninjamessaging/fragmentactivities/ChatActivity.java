package org.das.ninjamessaging.fragmentactivities;

import org.das.ninjamessaging.DetallesUsuario;
import org.das.ninjamessaging.R;
import org.das.ninjamessaging.R.id;
import org.das.ninjamessaging.R.layout;
import org.das.ninjamessaging.R.menu;
import org.das.ninjamessaging.fragments.Chat;
import org.das.ninjamessaging.fragments.Chat.IListFragmentListener;
import org.das.ninjamessaging.utils.LaBD;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class ChatActivity extends FragmentActivity implements IListFragmentListener {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_activity);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_activity, menu);
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
		
		switch (id) {
			case R.id.VerDetallesUsuario:
				Intent i = new Intent(getApplicationContext(), DetallesUsuario.class);
				//i.putExtra("detallesDe", hablandoCon);
				startActivity(i);
				
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
	
		return true;
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ActionBar actionBar;
		private Button enviar;
		private EditText mensaje;
		private String hablandoCon;
		private Chat chat;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_chat_activity, container,
					false);
			
			hablandoCon = getActivity().getIntent().getStringExtra("opcionSeleccionada");
			
			actionBar = getActivity().getActionBar();
			actionBar.setTitle(getActivity().getString(R.string.title_activity_chat) +
					" " + getActivity().getIntent().getStringExtra("opcionSeleccionada"));

			//chat = (Chat) getActivity().getFragmentManager().findFragmentById(R.id.chat);
			
			mensaje = (EditText) getView().findViewById(R.id.message);
			enviar = (Button) getView().findViewById(R.id.send);
			
			enviar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!mensaje.getText().equals("")) {
						LaBD.getMiBD(getActivity()).anadirMensaje(hablandoCon, 
								mensaje.getText().toString(), 1);
						//updateList(hablandoCon);
					}
					
				}
			});
			
			return rootView;
		}
		
	}

	@Override
	public void onItemSelected(String item) {
		
		
	}

}
