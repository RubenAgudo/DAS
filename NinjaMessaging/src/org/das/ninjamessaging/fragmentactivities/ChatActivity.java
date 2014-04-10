package org.das.ninjamessaging.fragmentactivities;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.activities.DetallesUsuario;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ChatActivity extends FragmentActivity {
	
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
				i.putExtra("detallesDe", getIntent().getStringExtra("opcionSeleccionada"));
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
		private String hablandoCon;
		
		
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
					" " + hablandoCon);

			return rootView;
		}
		
	}
}
