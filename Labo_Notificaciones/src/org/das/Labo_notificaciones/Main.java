package org.das.Labo_notificaciones;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Intent anIntent = new Intent(this, SecondActivity.class);
		Intent anIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:606152830"));

		PendingIntent intentEnNoti = PendingIntent.getActivity(this, 0, anIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Main.this);
		mBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
		mBuilder.setLargeIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());
		mBuilder.setContentTitle("Hola");
		mBuilder.setDefaults(Notification.DEFAULT_ALL);
		mBuilder.setTicker("Iñigo marica");
		mBuilder.setContentIntent(intentEnNoti);
		mBuilder.setDefaults(Notification.FLAG_AUTO_CANCEL);
		getApplicationContext();
		
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
