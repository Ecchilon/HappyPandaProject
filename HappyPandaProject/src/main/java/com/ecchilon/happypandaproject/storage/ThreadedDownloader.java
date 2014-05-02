package com.ecchilon.happypandaproject.storage;

import com.ecchilon.happypandaproject.GalleryItem;

/**
 * Created by Alex on 2/9/14.
 */
public class ThreadedDownloader implements DownloadService.DownloadTaskHandler {
    DownloadService mService;

    public ThreadedDownloader(DownloadService service){
        mService = service;
    }

    @Override
    public void startTask(GalleryItem task) {

    }

    @Override
    public void stopTask(GalleryItem task) {

    }
}
