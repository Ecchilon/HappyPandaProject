package com.ecchilon.happypandaproject.storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.ecchilon.happypandaproject.sites.GalleryPagesModuleInterface;

/**
 * Created by Alex on 1/26/14.
 */
public class StoredGalleryPagesModuleInterface implements GalleryPagesModuleInterface {

    private String[] mImageFiles;

    public StoredGalleryPagesModuleInterface(String[] imageFiles) {
        mImageFiles = imageFiles;
    }

    @Override
    public int getPageCount() {
        return mImageFiles.length;
    }

    @Override
    public void getImage(int index, final GalleryImageCreatedCallback listener) {
        if(index > (mImageFiles.length-1))
        {
            listener.ImageCreationFailed();
        }
        else {
            //Runs an async task that loads the bitmap
            new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... strings) {
                    return BitmapFactory.decodeFile(strings[0]);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    if(bitmap == null)
                        listener.ImageCreationFailed();
                    else
                        listener.ImageBitmapCreated(bitmap);
                }
            }.execute(mImageFiles[index]);
        }
    }
}
