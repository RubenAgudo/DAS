package org.das.ninjamessaging.widgets;

import org.das.ninjamessaging.R;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WidgetConfig extends Activity {
	 
	private Button btnAceptar;
	private Button btnCancelar;
	private EditText txtMensaje;
	
    private int widgetId = 0;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config);
 
        //Obtenemos el Intent que ha lanzado esta ventana
        //y recuperamos sus par�metros
        Intent intentOrigen = getIntent();
        Bundle params = intentOrigen.getExtras();
 
        //Obtenemos el ID del widget que se est� configurando
        widgetId = params.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
 
        //Establecemos el resultado por defecto (si se pulsa el bot�n 'Atr�s'
        //del tel�fono ser� �ste el resultado devuelto).
        setResult(RESULT_CANCELED);
 
        //Obtenemos la referencia a los controles de la pantalla
        btnAceptar = (Button)findViewById(R.id.BtnAceptar);
        btnCancelar = (Button)findViewById(R.id.BtnCancelar);
        txtMensaje = (EditText)findViewById(R.id.TxtMensaje);
        
        //Implementaci�n del bot�n "Cancelar"
        btnCancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Devolvemos como resultado: CANCELAR (RESULT_CANCELED)
                finish();
            }
        });
        
        //Implementaci�n del bot�n "Aceptar"
        btnAceptar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //Guardamos el mensaje personalizado en las preferencias
                    SharedPreferences prefs =
                        getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("msg_" + widgetId, txtMensaje.getText().toString());
                    editor.commit();
         
                    //Actualizamos el widget tras la configuraci�n
                    AppWidgetManager appWidgetManager =
                        AppWidgetManager.getInstance(WidgetConfig.this);
                    MiWidget.actualizarWidget(WidgetConfig.this, appWidgetManager, widgetId);
         
                    //Devolvemos como resultado: ACEPTAR (RESULT_OK)
                    Intent resultado = new Intent();
                    resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                    setResult(RESULT_OK, resultado);
                    finish();
                }
        });
    }
}
