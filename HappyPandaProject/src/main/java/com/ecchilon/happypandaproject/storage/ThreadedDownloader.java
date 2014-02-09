package com.ecchilon.happypandaproject.storage;

import android.os.Message;
import android.os.Messenger;

import com.ecchilon.happypandaproject.AlbumItem;

/**
 * Created by Alex on 2/9/14.
 */
public class ThreadedDownloader implements DownloadService.DownloadTaskHandler {
    DownloadService mService;

    public ThreadedDownloader(DownloadService service){
        mService = service;
    }

    @Override
    public void startTask(AlbumItem task) {

    }

    @Override
    public void stopTask(AlbumItem task) {

    }
}
