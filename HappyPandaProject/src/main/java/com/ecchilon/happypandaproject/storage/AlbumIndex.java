package com.ecchilon.happypandaproject.storage;

import android.os.Environment;

import com.ecchilon.happypandaproject.imageviewer.ImageViewerItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Class used to interface with the storage index. Index manages removal and addition of albums.
 * Created by Alex on 1/26/14.
 */
public class AlbumIndex {
    private static AlbumIndex mInstance;

    public static AlbumIndex getInstance() {
        if(mInstance == null)
            mInstance = new AlbumIndex();

        return mInstance;
    }

    private static final String ALBUM_DIR = File.separator + "HappyPanda"+ File.separator + "Library";
    private static final String INDEX_FILE_NAME = "index.xml";

    private Document albumIndex;

    private AlbumIndex() {
        if(!StorageService.isExternalStorageWritable())
            throw new IllegalStateException("Can't access external storage");
        File newFile = new File(Environment.getExternalStorageDirectory(), ALBUM_DIR + File.separator + INDEX_FILE_NAME);
        if(!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                return;
            }
        }

        if(!parseIndex(newFile))
            throw new IllegalArgumentException("Who messed with my XML?!");
    }

    public boolean parseIndex(File index) {

        try {
            albumIndex = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(index);
            albumIndex.getDocumentElement().normalize();
            return true;

        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LocalImageViewerItem> getStoredAlbums() {
        List<LocalImageViewerItem> localAlbums = new ArrayList<LocalImageViewerItem>();

        NodeList albumList = albumIndex.getElementsByTagName("Album");

        for(int i = 0; i < albumList.getLength(); i++) {

        }

        return  localAlbums;
    }

    public void removeItemFromStorage(LocalImageViewerItem item){


        WriteChanges();
    }

    public void addItemToStorage(ImageViewerItem item){
        //TODO call storage first, execute index after storage
        Element album = albumIndex.createElement("Album");

        Element url = albumIndex.createElement("GalleryURL");
        url.setTextContent(item.getGalleryUrl());

        album.appendChild(url);

        WriteChanges();
    }

    private void WriteChanges(){

    }

    public class LocalImageViewerItem extends ImageViewerItem {

        String[] mImagePaths;

        public String[] getImagePaths() { return mImagePaths; }

        public LocalImageViewerItem(String mTitle, String mThumbUrl, String mGalleryUrl, String[] imagePaths) {
            super(mTitle, mThumbUrl, mGalleryUrl);

            mImagePaths = imagePaths;
        }
    }
}
