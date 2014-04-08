package org.das.labo2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	private TextView textView1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //Toast.makeText(getApplicationContext(), "Creada!!!", Toast.LENGTH_LONG).show();
        textView1 = (TextView)findViewById(R.id.textView1);
        
    }


    public Main() {
		super();
		System.out.println("estoy aqui");
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "Destruida!!!", Toast.LENGTH_LONG).show();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		textView1.setText(textView1.getText().toString() + " Pausada!!!");
		
	}


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		textView1.setText(textView1.getText().toString() + " Reiniciada!!!");
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		textView1.setText(textView1.getText().toString() + " onResume!!!");
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		textView1.setText(textView1.getText().toString() + " Empezada!!!");
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		textView1.setText(textView1.getText().toString() + " Parada!!!");
	}
    
}
