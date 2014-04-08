package org.das.labo4;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MiServicio extends Service {

	private MediaPlayer miMediaPlayer;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		super.onCreate();
//		miMediaPlayer = MediaPlayer.create(this, R.raw.music);
//		miMediaPlayer.start();
//	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		miMediaPlayer.stop();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		miMediaPlayer = MediaPlayer.create(this, R.raw.music);
		miMediaPlayer.start();
		return START_NOT_STICKY;
		
	}
}
