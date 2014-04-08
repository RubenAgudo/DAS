package org.das.leerescribir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainLeerEscribir extends Activity {
	
	private EditText campoTexto;
	private EditText TextoAparecer;
	private Button btn1;
	private String texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_leer_escribir);
		
		btn1 = (Button) findViewById(R.id.button1);
		campoTexto = (EditText) findViewById(R.id.editText1);
		TextoAparecer = (EditText) findViewById(R.id.editText2);
		
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				texto = campoTexto.getText().toString();
				TextoAparecer.setText(texto);
				guardarTexto(texto);
				
			}
		});
	}

	private void guardarTexto(String contenido){
		String estado = Environment.getExternalStorageState();
		boolean Disponible,Escritura;
		
		if (estado.equals(Environment.MEDIA_MOUNTED)){
			Disponible = true;
			Escritura = true;
		}
		else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			Disponible = true;
			Escritura= false;
	}
		else{
			Disponible = false;
			Escritura = false;
		}
		
		if(Disponible && Escritura){
			
			try {
				File path = Environment.getExternalStorageDirectory();
				File f = new File(path.getAbsolutePath(), "nombrefich.txt");
				OutputStreamWriter fich = new OutputStreamWriter( new FileOutputStream(f));
				fich.write(contenido);
				fich.close();
			} catch (IOException e) {
				
				Log.e("Error", e.toString());
			}
		}
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_leer_escribir, menu);
		return true;
	}

}
