package com.ecchilon.happypandaproject.storage;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;

/**
 * Created by Alex on 2/8/14.
 */
public class DownloadService extends Service {

	private static final int NOTIFY_ID = 2131268947;

	/**
	 * For showing and hiding our notification.
	 */
	NotificationManager mNM;
	/**
	 * Holds last value set by a client.
	 */
	int mValue = 0;

	private DownloadTaskHandler mTaskHandler;

	public DownloadService() {
		mTaskHandler = new ThreadedDownloader(this);
	}

	@Override
	public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// Display a notification about us starting.
		showNotification();
	}

	@Override
	public void onDestroy() {
		// Cancel the persistent notification.
		mNM.cancel(NOTIFY_ID);
	}

	/**
	 * When binding to the service, we return an interface to our messenger for sending messages to the service.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mTaskHandler;
	}

	/**
	 * Starts the notification without any extra information. Updates get handled in {@link
	 * #updateTask(com.ecchilon.happypandaproject.imageviewer.IMangaItem, float)}
	 */
	private void showNotification() {
		//TODO fix this nonsense with actual notification/click handling
	    /*
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.remote_service_started);


        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher, text,
                System.currentTimeMillis());


        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Controller.class), 0);


        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.remote_service_label),
                text, null);

        // Send the notification.
        // We use a string id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.remote_service_started, notification);*/
	}

	/**
	 * updates the notification for an album item and notifies any clients listening
	 *
	 * @param item     The item being downloaded
	 * @param progress Progress of the item, from 0 (starting) to 1 (finished)
	 */
	public void updateTask(IMangaItem item, float progress) {
		//TODO update notification here

	}
}