/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.activity;

import com.acrylicgoat.bcponline.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * @author ed woodward
 *
 */
public class PrayerListActivity extends AppCompatActivity
{
    ActionBar aBar;
    private EditText note;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prayerlist_activity);
        aBar = getSupportActionBar();
        aBar.setTitle(getString(R.string.app_name));
        aBar.setIcon(android.R.color.transparent);
        note = (EditText) findViewById(R.id.editNotes);
        note.setText(getNote());
       
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.prayerlist_activity, menu);
        
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.clear();
        getMenuInflater().inflate(R.menu.prayerlist_activity, menu);
        
        return true;
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        if(item.getItemId() == R.id.save)
        {
            saveNote();
            
        }
        return true;
    }
    
    private void saveNote()
    {
        SharedPreferences prefs = this.getSharedPreferences("com.acrylicgoat.bcponline", Context.MODE_PRIVATE);
        prefs.edit().putString("prayerlist", note.getText().toString()).commit();
        
    }
    
    private String getNote()
    {
        SharedPreferences prefs = this.getSharedPreferences("com.acrylicgoat.bcponline", Context.MODE_PRIVATE);
        return prefs.getString("prayerlist", "");
        
    }
        

}
