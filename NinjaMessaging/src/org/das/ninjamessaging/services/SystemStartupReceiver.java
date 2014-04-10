package org.das.ninjamessaging.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SystemStartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, NotificationService.class));
	}

}
