package com.ecchilon.happypandaproject.storage;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;

/**
 * Created by Alex on 2/9/14.
 */
public class ThreadedDownloader implements DownloadService.DownloadTaskHandler {
    DownloadService mService;

    public ThreadedDownloader(DownloadService service){
        mService = service;
    }

    @Override
    public void startTask(ImageViewerItem task) {

    }

    @Override
    public void stopTask(ImageViewerItem task) {

    }
}
