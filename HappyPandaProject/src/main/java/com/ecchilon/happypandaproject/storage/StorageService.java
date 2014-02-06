package com.ecchilon.happypandaproject.storage;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.*;
import android.os.Process;
import android.support.v4.content.LocalBroadcastManager;

import com.ecchilon.happypandaproject.AlbumItem;
import com.ecchilon.happypandaproject.sites.AlbumPagesModuleInterface;
import com.ecchilon.happypandaproject.sites.util.SiteFactory;

import java.io.File;

/**
 * Created by Alex on 1/26/14.
 */
class StorageService extends Service {

    Handler uiHandler;

    public static final String ALBUM_ITEM = "ALBUM_ITEM";
    public static final String ALBUM_PATH = "ALBUM_PATH";
    public static final String BROADCAST_ACTION = "com.example.android.threadsample.BROADCAST";
    public static final String DOWNLOAD_STATUS = "com.example.android.threadsample.STATUS";

    public enum StorageStatus {
        downloading,
        failedStoring,
        failedDownloading,
        finished
    }

    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            //stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        Looper serviceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //notifies any internal listeners of the failed download
    void sendFailureBroadcast(AlbumItem failedItem) {
        Intent failIntent = new Intent(BROADCAST_ACTION);
        //notify that it was a storage fail
        failIntent.putExtra(DOWNLOAD_STATUS, StorageStatus.failedStoring);
        //return item to tell application which item it was that failed
        failIntent.putExtra(ALBUM_ITEM, failedItem.toJSONString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(failIntent);
    }

    void sendSuccessBroadcast(AlbumIndex.LocalAlbumItem item) {

    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    class DownloadAlbum {

        AlbumItem item;
        String albumPath;

        public DownloadAlbum(AlbumItem item, String albumPath) {
            this.item = item;
            this.albumPath = albumPath;
        }

        public void download() {
            AlbumPagesModuleInterface albumInterface = SiteFactory.getGalleryPagesInterface(item);

            File albumDir = new File(albumPath);
            if(albumDir.exists())
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendFailureBroadcast(item);
                    }
                });
            else {
                albumDir.mkdir();

                for(int i = 0; i < albumInterface.getPageCount(); i++) {
                    albumInterface.getImage(i, new AlbumPagesModuleInterface.AlbumImageCreatedCallback() {
                        @Override
                        public void ImageBitmapCreated(Bitmap bitmap) {

                        }

                        @Override
                        public void ImageURLCreated(String imageURL) {

                        }

                        @Override
                        public void ImageCreationFailed() {

                        }
                    });
                }
            }


        }
    }
}
