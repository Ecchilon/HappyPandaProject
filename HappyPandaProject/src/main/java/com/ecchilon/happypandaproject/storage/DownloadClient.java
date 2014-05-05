package com.ecchilon.happypandaproject.storage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;

/**
 * Created by Alex on 2/8/14.
 */
public class DownloadClient {
    public interface ServiceInterface{
        public void serviceConnected();
        public void serviceDisconnected();
        public void OnMessageReceived(Message message);
    }

    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private ServiceConnection mConnection;

    /**
     * Interface for our service callbacks
      */
    ServiceInterface mServiceInterface;

    public DownloadClient(ServiceInterface serviceInterface ){
        mServiceInterface = serviceInterface;

        mConnection = new DownloadServiceConnection();
    }

    public void sendStartTask(ImageViewerItem item){
        try{
            mMessenger.send(Message.obtain(null,
                    DownloadService.MSG_START_TASK, item.toJSONString()));
        } catch (RemoteException e){

        }
    }

    public void sendStopTask(ImageViewerItem item){
        try{
            mMessenger.send(Message.obtain(null,
                    DownloadService.MSG_START_TASK, item.toJSONString()));
        } catch (RemoteException e){

        }
    }

    public void doBindService(Context context) {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        context.bindService(new Intent(context,
                DownloadService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void doUnbindService(Context context) {
        if (mIsBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null,
                            DownloadService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }

            // Detach our existing connection.
            context.unbindService(mConnection);
            mIsBound = false;
        }
    }

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(mServiceInterface != null)
                mServiceInterface.OnMessageReceived(msg);
            else
                super.handleMessage(msg);
        }
    }

    /**
     * Class for interacting with the main interface of the service.
     */
    class DownloadServiceConnection implements ServiceConnection {
            public void onServiceConnected(ComponentName className,
                    IBinder service) {
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  We are communicating with our
                // service through an IDL interface, so get a client-side
                // representation of that from the raw service object.
                mService = new Messenger(service);

                // We want to monitor the service for as long as we are
                // connected to it.
                try {
                    Message msg = Message.obtain(null,
                            DownloadService.MSG_REGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);

                    // Give it some value as an example.
                    msg = Message.obtain(null,
                            DownloadService.MSG_START_TASK, this.hashCode(), 0);
                    mService.send(msg);

                    if(mServiceInterface != null)
                        mServiceInterface.serviceConnected();
                } catch (RemoteException e) {
                    // In this case the service has crashed before we could even
                    // do anything with it; we can count on soon being
                    // disconnected (and then reconnected if it can be restarted)
                    // so there is no need to do anything here.
                }
            }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;

            if(mServiceInterface != null)
                mServiceInterface.serviceDisconnected();
        }
    }
}
