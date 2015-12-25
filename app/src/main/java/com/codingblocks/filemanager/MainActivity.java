package com.codingblocks.filemanager;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    File currentFolder;
    FileArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String path = (b == null)? "/" : b.getString("folder", "/");
        currentFolder = new File(path);
        File[] files = currentFolder.listFiles();
        if (files == null)
            files = new File[0];
        adapter = new FileArrayAdapter(this, files);
        ListView lv = (ListView) findViewById(R.id.fileListView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_fav) {
            Intent i = new Intent();
            i.setClass(this, FavActivity.class);
            startActivity(i);
        } else if (id == R.id.user_dictionary) {

            ContentValues cv = new ContentValues();
            cv.put(UserDictionary.Words.WORD, "newword");
            cv.put(UserDictionary.Words.LOCALE, "en_us");
            getContentResolver().insert(UserDictionary.Words.CONTENT_URI, cv);
            //UserDictionary.Words.addWord(this, "newword", 10, "hey", Locale.CANADA_FRENCH);

            String[] projection =  {UserDictionary.Words.LOCALE, UserDictionary.Words.WORD};
            Cursor c = getContentResolver().query(UserDictionary.Words.CONTENT_URI, projection, null, null, null);
            String output = "";
            while (c.moveToNext()) {
                output += c.getString(c.getColumnIndex(UserDictionary.Words.WORD)) + " "
                        + c.getString(c.getColumnIndex(UserDictionary.Words.LOCALE));
                output += ',';
            }
            Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
//
//    public void addToFav(View v) {
//        File f = (File)v.getTag();
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File fileClicked = adapter.getItem(position);
        if (fileClicked.isDirectory()) {
            Intent i = new Intent();
            i.setClass(this, MainActivity.class);
            i.putExtra("folder", fileClicked.getAbsolutePath());
            startActivity(i);
            try {
                FileOutputStream fos = openFileOutput("clicked", Context.MODE_PRIVATE);
                fos.write("abcdgebubueubu".getBytes());
                fos.close();

                FileInputStream fis = openFileInput("clicked");
                Scanner s = new Scanner(fis);
                Log.i("fetched", s.nextLine());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Scanner s = new Scanner(fileClicked);
                String line = s.nextLine();
                Log.i("fetched", line);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
