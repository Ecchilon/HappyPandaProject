package com.ecchilon.happypandaproject.storage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;

/**
 * Created by Alex on 2/8/14.
 */
public class DownloadClient {
	public interface BindingListener {
		public void serviceConnected();

		public void serviceDisconnected();
	}

	/**
	 * Flag indicating whether we have called bind on the service.
	 */
	boolean mIsBound;

	private ServiceConnection mConnection;

	/**
	 * Interface for our service callbacks
	 */
	BindingListener mListener;

	private DownloadTaskHandler mBinder;

	public DownloadClient() {
		mConnection = new DownloadServiceConnection();
	}

	public void setBindingListener(BindingListener listener) {
		mListener = listener;
	}

	public void sendStartTask(IMangaItem item) {
		mBinder.startTask(item);
	}

	public void sendStopTask(IMangaItem item) {
		mBinder.stopTask(item);
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
			context.unbindService(mConnection);
		}
	}

	/**
	 * Class for interacting with the main interface of the service.
	 */
	class DownloadServiceConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName className,
				IBinder service) {

			mBinder = (DownloadTaskHandler) service;

			if (mListener != null) {
				mListener.serviceConnected();
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			mBinder = null;
			mIsBound = false;

			if (mListener != null) {
				mListener.serviceDisconnected();
			}
		}
	}
}
