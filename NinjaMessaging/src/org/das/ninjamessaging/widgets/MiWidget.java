package org.das.ninjamessaging.widgets;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.fragmentactivities.MainActivity;
import org.das.ninjamessaging.utils.LaBD;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class MiWidget extends AppWidgetProvider {
	@Override
    public void onUpdate(Context context,
                 AppWidgetManager appWidgetManager,
                 int[] appWidgetIds) {
		
		//Iteramos la lista de widgets en ejecuci�n
	    for (int i = 0; i < appWidgetIds.length; i++)
	    {
	        //ID del widget actual
	        int widgetId = appWidgetIds[i];
	 
	        //Actualizamos el widget actual
	        actualizarWidget(context, appWidgetManager, widgetId);
	    }
    }
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    if (intent.getAction().equals("org.das.ninjamessaging.widgets.ACTUALIZAR_WIDGET")) {
	    	    	
	        //Obtenemos el ID del widget a actualizar
	        int widgetId = intent.getIntExtra(
	            AppWidgetManager.EXTRA_APPWIDGET_ID,
	            AppWidgetManager.INVALID_APPWIDGET_ID);
	 
	        //Obtenemos el widget manager de nuestro contexto
	        AppWidgetManager widgetManager =
	            AppWidgetManager.getInstance(context);
	 
	        //Actualizamos el widget
	        if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
	            actualizarWidget(context, widgetManager, widgetId);
	        }
	    }
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		//Accedemos a las preferencias de la aplicaci�n
		SharedPreferences prefs = 
			context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		//Eliminamos las preferencias de los widgets borrados
		for(int i=0; i<appWidgetIds.length; i++)
		{
			//ID del widget actual
			int widgetId = appWidgetIds[i];
			
			editor.remove("msg_" + widgetId);
		}
		
		//Aceptamos los cambios
		editor.commit();
		
		super.onDeleted(context, appWidgetIds);
	}
	
	public static void actualizarWidget(Context context,
            AppWidgetManager appWidgetManager, int widgetId)
	{
		//Recuperamos el mensaje personalizado para el widget actual
		SharedPreferences prefs =
		    context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
		String mensaje = prefs.getString("msg_" + widgetId, "Hora actual:");
		
		//Obtenemos la lista de controles del widget actual
		RemoteViews controles =
		    new RemoteViews(context.getPackageName(), R.layout.miwidget);
		
		//Asociamos los 'eventos' al widget
		Intent intent = new Intent("org.das.ninjamessaging.widgets.ACTUALIZAR_WIDGET");
		intent.putExtra(
			     AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
		PendingIntent pendingIntent = 
			PendingIntent.getBroadcast(context, widgetId, 
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		controles.setOnClickPendingIntent(R.id.BtnActualizar, pendingIntent);
		
		Intent intent2 = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent2 = 
				PendingIntent.getActivity(context, widgetId, 
						intent2, PendingIntent.FLAG_UPDATE_CURRENT);
		
		controles.setOnClickPendingIntent(R.id.FrmWidget, pendingIntent2);
		
		//Actualizamos el mensaje en el control del widget
		controles.setTextViewText(R.id.LblMensaje, mensaje);
		
		String[] data = LaBD.getMiBD(context).getLastMessage(mensaje);
		if(data != null) {
			controles.setTextViewText(R.id.LblMensaje, data[1]);
			controles.setTextViewText(R.id.LblUsuario, data[0]); 
		}
		
		//Notificamos al manager de la actualizaci�n del widget actual
		appWidgetManager.updateAppWidget(widgetId, controles);
	}
}
