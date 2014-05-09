package com.ecchilon.happypandaproject.storage;

import android.os.Binder;
import com.ecchilon.happypandaproject.imageviewer.IMangaItem;

/**
 * Created by Alex on 9-5-2014.
 */
public abstract class DownloadTaskHandler extends Binder {
	public abstract void startTask(IMangaItem task);

	public abstract void stopTask(IMangaItem task);
}
