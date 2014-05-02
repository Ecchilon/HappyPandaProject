package com.ecchilon.happypandaproject.storage;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.ecchilon.happypandaproject.GalleryItem;

import java.util.ArrayList;

/**
 * Created by Alex on 2/8/14.
 */
public class DownloadService extends Service {
    public interface DownloadTaskHandler {
        public void startTask(GalleryItem task);
        public void stopTask(GalleryItem task);
    }

    private static final int NOTIFY_ID = 2131268947;

    /** For showing and hiding our notification. */
    NotificationManager mNM;
    /** Keeps track of all current registered clients. */
    ArrayList<Messenger> mClients = new ArrayList<Messenger>();
    /** Holds last value set by a client. */
    int mValue = 0;

    /**
     * Command to the service to register a client, receiving callbacks
     * from the service.  The Message's replyTo field must be a Messenger of
     * the client where callbacks should be sent.
     */
    static final int MSG_REGISTER_CLIENT = 1;

    /**
     * Command to the service to unregister a client, ot stop receiving callbacks
     * from the service.  The Message's replyTo field must be a Messenger of
     * the client as previously given with MSG_REGISTER_CLIENT.
     */
    static final int MSG_UNREGISTER_CLIENT = 2;

    static final int MSG_START_TASK = 3;

    static final int MSG_STOP_TASK = 4;

    static final int MSG_UPDATE_TASK = 5;

    private DownloadTaskHandler mTaskHandler;

    public  DownloadService(){
        mTaskHandler = new ThreadedDownloader(this);
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.
        showNotification();
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFY_ID);
    }

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    /**
     * Starts the notification without any extra information. Updates get handled in
     * {@link #updateTask(com.ecchilon.happypandaproject.GalleryItem, float)}
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
     * @param item The item being downloaded
     * @param progress Progress of the item, from 0 (starting) to 1 (finished)
     */
    public void updateTask(GalleryItem item, float progress){
        //TODO update notification here

        for(int i = 0; i < mClients.size(); i++){
            try
            {
                mClients.get(i).send(Message.obtain(null, MSG_UPDATE_TASK, item.toJSONString()));
            }catch(RemoteException e){
                mClients.remove(i);
            }
        }
    }

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    mClients.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    mClients.remove(msg.replyTo);
                    break;
                case MSG_START_TASK:
                    mTaskHandler.startTask(GalleryItem.fromJSONString((String) msg.obj));
                    break;
                case MSG_STOP_TASK:
                    mTaskHandler.stopTask(GalleryItem.fromJSONString((String) msg.obj));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}