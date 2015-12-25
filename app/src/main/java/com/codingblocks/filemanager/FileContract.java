package com.codingblocks.filemanager;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nagarro on 17/10/15.
 */
public class FileContract implements BaseColumns{
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.codingblocks.filemanager/";

    public static final String BASE_URI_STRING = SCHEME + AUTHORITY;
    public static final Uri BASE_URI = Uri.parse(BASE_URI_STRING);

    public static final String FAV_TABLE = "favs";
    public static final String FAV_URI_STRING = BASE_URI_STRING + FAV_TABLE;
    public static final String SINGLE_FAV_URI_STRING =  FAV_TABLE + "/#";
   // public static final Uri SINGLE_CONTENT_URI = Uri.p
    public static final Uri CONTENT_URI = Uri.parse(FAV_URI_STRING);

    public static final String FAV_TABLE_FILE_PATH = "file_path";
    public static final String FAV_TABLE_CREATION_TIME = "created_at";

}
