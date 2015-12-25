package com.codingblocks.filemanager;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by nagarro on 17/10/15.
 */
public class FileContentProvider extends ContentProvider{

    FileManagerOpenHelper mHelper;
    private static final UriMatcher mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int FAV_TABLE_URI = 1;
    private static final int SINGLE_FAV_TABLE_URI = 2;
    static {
        mMatcher.addURI(FileContract.AUTHORITY, FileContract.FAV_TABLE, FAV_TABLE_URI);
        mMatcher.addURI(FileContract.AUTHORITY, FileContract.SINGLE_FAV_URI_STRING, SINGLE_FAV_TABLE_URI);
    }

    @Override
    public boolean onCreate() {
         mHelper = new FileManagerOpenHelper(getContext(), null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
       int match = mMatcher.match(uri);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor c;
        switch(match) {
            case FAV_TABLE_URI:
                c = db.query(FileManagerOpenHelper.FAV_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SINGLE_FAV_TABLE_URI:
                long id = ContentUris.parseId(uri);
                if (selection == null || selection.length() == 0) {
                    selection = FileContract._ID + " = " + id;
                } else {
                    selection += " and " + FileContract._ID + " = " + id;
                }
                c = db.query(FileManagerOpenHelper.FAV_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                c = null;
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        long id = db.insert(FileManagerOpenHelper.FAV_TABLE, null, values);
        return ContentUris.appendId(FileContract.CONTENT_URI.buildUpon(), id).build();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
