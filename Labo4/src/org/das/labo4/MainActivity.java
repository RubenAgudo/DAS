package org.das.labo4;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private Button startService;
	private Button stopService;
	private Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService = (Button)findViewById(R.id.button1);
		stopService = (Button)findViewById(R.id.button2);
		startService.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				i = new Intent(MainActivity.this, MiServicio.class);
				startService(i);
				
			}
		});
		
		stopService.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				stopService(i);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
