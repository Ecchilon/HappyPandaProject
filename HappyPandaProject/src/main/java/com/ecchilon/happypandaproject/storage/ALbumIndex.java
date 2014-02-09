package com.ecchilon.happypandaproject.storage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.ecchilon.happypandaproject.AlbumItem;
import com.ecchilon.happypandaproject.favorites.FavoritesDatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to interface with the storage index. Index manages removal and addition of albums.
 * Created by Alex on 1/26/14.
 */
public class AlbumIndex {
    private static AlbumIndex mInstance;

    public synchronized static AlbumIndex getInstance() {
        if(mInstance == null) {
            mInstance = new AlbumIndex();

        }

        return mInstance;
    }

    private static final String LIBRARY_DIR = File.separator + "HappyPanda"+ File.separator + "Library";
    private static final String INDEX_FILE_NAME = "index.json";

    List<NodeAlbumItem> albums;

    Gson gson;

    private AlbumIndex() {
        gson = new Gson();
        albums = new ArrayList<NodeAlbumItem>();

        File indexFile = getIndexFile();
        if(!indexFile.exists()) {
            try {
                //construct folder if needed.
                indexFile.getParentFile().mkdirs();
                //create index file
                indexFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new AsyncTask<File, Void, Void>() {
            @Override
            protected Void doInBackground(File... files) {
                if(!parseIndex(files[0]))
                    cancel(true);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)  {
                super.onPostExecute(aVoid);
                if(isCancelled())
                    throw new IllegalArgumentException("Who messed with my XML?!");
            }
        }.execute(indexFile);
    }

    private File getIndexFile(){
        return new File(Environment.getExternalStorageDirectory(), LIBRARY_DIR + File.separator + INDEX_FILE_NAME);
    }


    private boolean parseIndex(File index) {
        synchronized (albums) {
            FileReader reader = null;
            try {
                reader = new FileReader(index);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            List<LocalAlbumItem> items = gson.fromJson(reader, new TypeToken<List<LocalAlbumItem>>(){}.getType());

            boolean docChanged =false;
            for(int i = 0; i < albums.size(); i++) {
                NodeAlbumItem item = parseAlbum(items.get(i));
                if(item != null)
                    albums.add(item);
                else
                    //if it's null, the albums were changed, so at the end, we write new index
                    docChanged = true;
            }

            if(docChanged)
                writeChangesToIndex();
        }

        return true;
    }

    private NodeAlbumItem parseAlbum(LocalAlbumItem item) {
        //albumDirName is just the name, so add LIBRARY_DIR and external directory.
        File albumDir = new File(Environment.getExternalStorageDirectory(), LIBRARY_DIR + File.separator + item.getAlbumDirectoryName());
        //remove album node from index if the file doesn't exist any more
        if(!albumDir.exists() || !albumDir.isDirectory()) {
            return null;
        }
        else {
            //convert file to string since BitmapFactory uses either those or streams
            File[] images = albumDir.listFiles();
            String[] imagePaths = new String[images.length];
            for(int i = 0; i < images.length; i++)
                imagePaths[i] = images[i].getPath();

            boolean isFavorite =FavoritesDatabaseHelper.getInstance().getFavorite(item.getAlbumUrl()) != null;
            item.setIsFavorite(isFavorite);

            return new NodeAlbumItem(item, albumDir);
        }
    }

    public interface AlbumsCreatedCallback {
        public void albumsCreated(List<LocalAlbumItem> albumItemList);
        public void albumCreationFailed();
    }

    public synchronized void getStoredAlbums(final AlbumsCreatedCallback callback) {
        List<LocalAlbumItem> localAlbums = new ArrayList<LocalAlbumItem>();

        for(NodeAlbumItem album : albums) {
            localAlbums.add(album.getGalleryItem());
        }

        callback.albumsCreated(localAlbums);
    }

    public interface AlbumRemovedCallback {
        public void AlbumRemoved(boolean success);
    }

    public synchronized void removeItemFromStorage(final LocalAlbumItem item,final AlbumRemovedCallback callback){
        new AsyncTask<Void, Void, Void>(){
            boolean foundAndRemoved = false;
            @Override
            protected Void doInBackground(Void... voids) {
                boolean foundAndRemoved = false;
                for(NodeAlbumItem albumItem : albums){
                    if(albumItem.getGalleryItem() == item){
                        foundAndRemoved = deleteDirectory(albumItem.getFolder());
                        break;
                    }
                }

                if(foundAndRemoved)
                    writeChangesToIndex();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.AlbumRemoved(foundAndRemoved);

            }
        }.execute();
    }

    public interface AlbumStoredCallback {
        public void albumStored(boolean success);
        public void storageProgress(float storage);
    }

    public synchronized void addItemToStorage(AlbumItem item, Context context, AlbumStoredCallback callback){



        //TODO call storage first, execute index after storage
        writeChangesToIndex();
    }

    private synchronized boolean writeChangesToIndex() {
        return false;
    }

    private synchronized static boolean deleteDirectory(File directory) {
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

    public static class LocalAlbumItem extends AlbumItem {


        String mAlbumDirectoryName;
        String[] mImagePaths;

        public String getAlbumDirectoryName() {
            return mAlbumDirectoryName;
        }
        public String[] getImagePaths() { return mImagePaths; }

        public LocalAlbumItem(String mTitle, String mThumbUrl, String mGalleryUrl, boolean mIsStored, boolean mIsFavorite, String[] mImagePaths) {
            super(mTitle, mThumbUrl, mGalleryUrl, mIsStored, mIsFavorite);
            this.mImagePaths = mImagePaths;
        }

        @Override
        public String toJSONString() {
            Gson gson = new Gson();
            return gson.toJson(this, LocalAlbumItem.class);
        }

        public static LocalAlbumItem fromJSONString(String jsonString){
            Gson gson = new Gson();
            return gson.fromJson(jsonString, LocalAlbumItem.class);
        }
    }

    class NodeAlbumItem {

        LocalAlbumItem mItem;
        File mFolder;

        public LocalAlbumItem getGalleryItem() { return mItem; }
        public File getFolder() { return mFolder; }

        public NodeAlbumItem(LocalAlbumItem item, File folder){
            mItem = item;
            mFolder = folder;
        }
    }
}
