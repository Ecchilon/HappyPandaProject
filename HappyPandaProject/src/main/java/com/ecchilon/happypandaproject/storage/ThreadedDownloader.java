package com.ecchilon.happypandaproject.storage;

import com.ecchilon.happypandaproject.imageviewer.IMangaItem;

/**
 * Created by Alex on 2/9/14.
 */
public class ThreadedDownloader extends DownloadTaskHandler {
	DownloadService mService;

	public ThreadedDownloader(DownloadService service) {
		mService = service;
	}

	@Override
	public void startTask(IMangaItem task) {

	}

	@Override
	public void stopTask(IMangaItem task) {

	}
}
