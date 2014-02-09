package com.ecchilon.happypandaproject.favorites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ecchilon.happypandaproject.AlbumItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 1/25/14.
 */
public class FavoritesDatabaseHelper extends SQLiteOpenHelper {

    static FavoritesDatabaseHelper instance;

    public static FavoritesDatabaseHelper getInstance() {
        if(instance == null) throw new IllegalStateException("FavoritesDatabase should be instantiated!");
        return instance;
    }

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "FavoritesManager";
    private static final String TABLE_FAVORITES = "Favorites";

    private static final String KEY_TITLE = "title";
    private static final String KEY_THUMB = "thumb";
    private static final String KEY_URL = "url";

    public FavoritesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);

        if(instance != null)
            instance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createContactsTable = "CREATE TABLE " + TABLE_FAVORITES + "(" + KEY_TITLE + " TEXT,"
                + KEY_THUMB + " TEXT," + KEY_URL + " TEXT PRIMARY KEY" + ")";

        sqLiteDatabase.execSQL(createContactsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);

        onCreate(sqLiteDatabase);
    }

    public void addFavorite(AlbumItem item) {
        //if it's already there, no need to
        if(getFavorite(item.getAlbumUrl()) != null) return;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_THUMB, item.getThumbUrl());
        values.put(KEY_URL, item.getAlbumUrl());

        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public AlbumItem getFavorite(String url) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{KEY_URL}, KEY_URL + "=?", new String[]{url}, null, null, null, null);
        if(cursor == null)
            return null;
        else {
            cursor.moveToFirst();
            String albumUrl =cursor.getString(3);
            AlbumItem item = new AlbumItem(cursor.getString(1), cursor.getString(2), albumUrl, true, StorageController.isStored(albumUrl));

            return item;
        }
    }

    public List<AlbumItem> getAllFavorites() {
        List<AlbumItem> favoriteList = new ArrayList<AlbumItem>();

        String selectQuery = "SELECT * FROM " + TABLE_FAVORITES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                String albumUrl =cursor.getString(3);
                AlbumItem item = new AlbumItem(cursor.getString(1), cursor.getString(2), albumUrl, true, StorageController.isStored(albumUrl));

                favoriteList.add(item);
            }while(cursor.moveToNext());
        }

        return favoriteList;
    }

    public long getFavoriteCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FAVORITES, null);
        return cursor.getLong(0);
    }

    public void deleteFavorite(AlbumItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_URL + "=?", new String[] {item.getAlbumUrl()});
        db.close();
    }
}
