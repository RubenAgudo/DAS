package org.das.ninjamessaging.services;

import org.das.ninjamessaging.R;
import org.das.ninjamessaging.fragmentactivities.MainActivity;
import org.das.ninjamessaging.fragmentactivities.MapActivity;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "NinjaMessaging";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
        	if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
        		String mensaje = extras.getString("my_message");
        		boolean isMap = Boolean.parseBoolean(extras.getString("isMap"));
        		sendNotification("Received: " + mensaje, isMap, extras);
        		
        		//se podria guardar en la bd local...
        		
        	}
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg, boolean isMap, Bundle extras) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i;
        if(isMap) {
        	msg = "Localizacion recibida";
        	double latitude, longitude;
        	latitude = Double.parseDouble(extras.getString("latitude"));
        	longitude = Double.parseDouble(extras.getString("longitude"));
        	i = new Intent(this, MapActivity.class);
        	i.putExtra("lat", latitude);
        	i.putExtra("long", longitude);
        } else {
        	i = new Intent(this, MainActivity.class);
        }
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                i, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("GCM Notification")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg)
        .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
