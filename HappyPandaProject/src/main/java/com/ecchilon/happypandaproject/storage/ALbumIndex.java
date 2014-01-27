package com.ecchilon.happypandaproject.storage;

import android.content.Context;
import android.os.Environment;

import com.ecchilon.happypandaproject.AlbumItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

    private static final String LIBRARY_DIR = File.separator + "HappyPanda"+ File.separator + "Library";
    private static final String INDEX_FILE_NAME = "index.xml";

    Document albumIndex;

    List<NodeAlbumItem> albums;

    private AlbumIndex() {
        albums = new ArrayList<NodeAlbumItem>();

        if(!StorageService.isExternalStorageWritable())
            throw new IllegalStateException("Can't access external storage");
        File newFile = new File(Environment.getExternalStorageDirectory(), LIBRARY_DIR + File.separator + INDEX_FILE_NAME);
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

            //read all the album nodes in the in the file
            NodeList albumNodes = albumIndex.getElementsByTagName("Album");

            boolean docChanged =false;
            for(int i = 0; i < albumNodes.getLength(); i++) {
                if(albumNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
                    NodeAlbumItem item = parseAlbum((Element)albumNodes.item(i));
                    if(item != null)
                        albums.add(item);
                    else
                    //if it's null, the album directory couldn't be found, and was removed
                    //automatically, so the changes need to be recorded to the index
                        docChanged = true;
                }
            }

            if(docChanged)
                writeChanges();

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

    private NodeAlbumItem parseAlbum(Element albumNode) {
        //get simple attributes
        NodeList attr = albumNode.getChildNodes();
        String albumDirName = attr.item(0).getTextContent();
        String albumName = attr.item(1).getTextContent();
        String albumURL = attr.item(2).getTextContent();
        String thumbURL = attr.item(3).getTextContent();

        //albumDirName is just the name, so add LIBRARY_DIR and external directory.
        File albumDir = new File(Environment.getExternalStorageDirectory(), LIBRARY_DIR + File.separator + albumDirName);
        //remove album node from index if the file doesn't exist any more
        if(!albumDir.exists() || !albumDir.isDirectory()) {
            albumIndex.removeChild(albumNode);
            return null;
        }
        else {
            //convert file to string since BitmapFactory uses either those or streams
            File[] images = albumDir.listFiles();
            String[] imagePaths = new String[images.length];
            for(int i = 0; i < images.length; i++)
                imagePaths[i] = images[i].getPath();

            LocalAlbumItem albumItem = new LocalAlbumItem(albumName, thumbURL, albumURL, imagePaths);

            return new NodeAlbumItem(albumItem, albumNode, albumDir);
        }
    }

    public List<LocalAlbumItem> getStoredAlbums() {
        List<LocalAlbumItem> localAlbums = new ArrayList<LocalAlbumItem>();

        for(NodeAlbumItem album : albums) {
            localAlbums.add(album.getGalleryItem());
        }

        return  localAlbums;
    }

    public boolean removeItemFromStorage(LocalAlbumItem item){
        boolean foundAndRemoved = false;
        for(NodeAlbumItem albumItem : albums){
            if(albumItem.getGalleryItem() == item){
                foundAndRemoved = deleteDirectory(albumItem.getFolder());
                break;
            }
        }

        if(foundAndRemoved)
            writeChanges();

        return foundAndRemoved;
    }

    public void addItemToStorage(AlbumItem item, Context context){


        //TODO call storage first, execute index after storage
        Element album = albumIndex.createElement("Album");

        Element url = albumIndex.createElement("GalleryURL");
        url.setTextContent(item.getGalleryUrl());

        album.appendChild(url);

        writeChanges();
    }

    private void writeChanges(){

    }

    private static boolean deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
        }
        return(directory.delete());
    }

    public class LocalAlbumItem extends AlbumItem {

        String[] mImagePaths;

        public String[] getImagePaths() { return mImagePaths; }

        public LocalAlbumItem(String mTitle, String mThumbUrl, String mAlbumUrl, String[] imagePaths) {
            super(mTitle, mThumbUrl, mAlbumUrl);

            mImagePaths = imagePaths;
        }
    }

    class NodeAlbumItem {

        LocalAlbumItem mItem;
        Node mIndexNode;
        File mFolder;

        public LocalAlbumItem getGalleryItem() { return mItem; }
        public Node getNode() { return  mIndexNode; }
        public File getFolder() { return mFolder; }

        public NodeAlbumItem(LocalAlbumItem item, Node node, File folder){
            mItem = item;
            mIndexNode = node;
            mFolder = folder;
        }
    }
}
