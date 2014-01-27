package com.ecchilon.happypandaproject.sites.test;

import com.ecchilon.happypandaproject.sites.AlbumPagesModuleInterface;

/**
 * Created by Alex on 1/23/14.
 */
public class DummyImageModuleInterface implements AlbumPagesModuleInterface {

    String[] urls = new String[] {
        "http://i.imgur.com/NIup5WR.jpg",
        "http://cdn1.spiegel.de/images/image-587844-galleryV9-ctub.jpg",
        "http://i.imgur.com/7gwSBUD.jpg",
        "http://i.imgur.com/NJ7L8BA.jpg",
        "http://cdn.awwni.me/n0br.jpg",
        "http://i.imgur.com/dcwjawN.jpg"
    };

    @Override
    public int getPageCount() {
        return urls.length;
    }

    @Override
    public void getImage(int index, AlbumImageCreatedCallback listener) {
        if(index < urls.length)
            listener.ImageURLCreated(urls[index]);
        else
            listener.ImageCreationFailed();
    }
}
