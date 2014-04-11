package org.das.ninjamessaging.services;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.fragmentactivities.ChatActivity;
import org.das.ninjamessaging.utils.LaBD;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotificationService extends Service {
	

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//runs without timer be reposting self
		final Handler h2 = new Handler();
		Runnable run = new Runnable() {

			@Override
	        public void run() {
				
				createNotification();

				h2.postDelayed(this, 15000);
			}

			private void createNotification() {
				
				String[] datos = LaBD.getMiBD(getApplicationContext()).getRandomChat();
				
				PendingIntent intentEnNoti = createIntent(datos);
				
				
				//opciones del intent
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
				configurarIntent(intentEnNoti, mBuilder, datos);

				notify(mBuilder);
				
			}

			private PendingIntent createIntent(String[] datos) {
				Intent anIntent = new Intent(getApplicationContext(), ChatActivity.class);
				anIntent.putExtra("opcionSeleccionada", datos[0]);
				
				PendingIntent intentEnNoti = PendingIntent.getActivity(getApplicationContext(), 0, anIntent, PendingIntent.FLAG_CANCEL_CURRENT);
				return intentEnNoti;
			}

			private void notify(NotificationCompat.Builder mBuilder) {
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.notify(1, mBuilder.build());
			}

			private void configurarIntent(PendingIntent intentEnNoti,
					NotificationCompat.Builder mBuilder, String[] datos) {
				mBuilder.setSmallIcon(R.drawable.ic_launcher);
				mBuilder.setLargeIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap());
				mBuilder.setContentTitle(datos[0] + ": " + datos[1]);
				//mBuilder.setDefaults(Notification.DEFAULT_ALL);
				mBuilder.setTicker("Tienes un nuevo mensaje");
				mBuilder.setContentIntent(intentEnNoti);
				mBuilder.setAutoCancel(true);
				
			}
		};
		
		run.run();
				
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {	
		return START_STICKY;
	}
}
