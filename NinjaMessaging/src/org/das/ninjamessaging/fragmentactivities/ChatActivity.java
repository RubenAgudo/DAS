package org.das.ninjamessaging.fragmentactivities;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.R.id;
import org.das.ninjamessaging.R.layout;
import org.das.ninjamessaging.R.menu;
import org.das.ninjamessaging.fragments.Chat;
import org.das.ninjamessaging.fragments.Chat.IListFragmentListener;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class ChatActivity extends FragmentActivity implements IListFragmentListener {

	private Chat chat;
	
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_activity);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		actionBar = getActionBar();
		actionBar.setTitle(actionBar.getTitle() + " " + getIntent().getStringExtra("opcionSeleccionada"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_chat_activity, container,
					false);
			
			
			return rootView;
		}
	}

	@Override
	public void onItemSelected(String item) {
		
		
	}

}
