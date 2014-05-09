package com.ecchilon.happypandaproject.storage;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import com.ecchilon.happypandaproject.gson.GsonMangaItem;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;
import com.ecchilon.happypandaproject.sites.GalleryPagesModuleInterface;

/**
 * Created by Alex on 1/26/14.
 */
public class StorageService extends IntentService {

    public static final String GALLERY_ITEM = "GALLERY_ITEM";
    public static final String BROADCAST_ACTION = "com.example.android.threadsample.BROADCAST";
    public static final String DOWNLOAD_STATUS = "com.example.android.threadsample.STATUS";

    public enum StorageStatus {
        downloading,
        failedStoring,
        failedDownloading,
        finished
    }

    public StorageService() {
        super("HappyPandaStorageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
	    IMangaItem item = GsonMangaItem.getItem(intent.getStringExtra(GALLERY_ITEM));

	    if(!isExternalStorageWritable()) {
            Intent failIntent = new Intent(BROADCAST_ACTION);
            //notify that it was a storage fail
            failIntent.putExtra(DOWNLOAD_STATUS, StorageStatus.failedStoring);
            //return item to tell application which item it was that failed
		    failIntent.putExtra(GALLERY_ITEM, GsonMangaItem.getJson(item));
		    LocalBroadcastManager.getInstance(this).sendBroadcast(failIntent);
        }
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
		        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

	public static GalleryPagesModuleInterface getGallery(IMangaItem item) {

		return new GalleryPagesModuleInterface() {
            @Override
            public int getPageCount() {
                return 0;
            }

            @Override
            public void getImage(int index, GalleryImageCreatedCallback listener) {

            }
        };
    }
}
