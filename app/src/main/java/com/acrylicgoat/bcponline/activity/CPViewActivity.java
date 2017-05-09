/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.activity;

import com.acrylicgoat.bcponline.fragments.CPViewFragment;
import com.acrylicgoat.bcponline.util.ContentCache;
import com.acrylicgoat.bcponline.util.CPUtil;
import com.acrylicgoat.bcponline.R;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
//import android.util.Log;

/**
 * @author Ed Woodward
 *
 */
public class CPViewActivity extends AppCompatActivity
{
    String url;
    CPViewFragment viewer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_fragment);

        Intent launchingIntent = getIntent();
        url = launchingIntent.getData().toString();
        ContentCache.setObject(getString(R.string.url), url);

        viewer = (CPViewFragment)getFragmentManager().findFragmentById(R.id.view_fragment);
        
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                viewer.updateUrl(url); 
            }
        });

        String category = (String)ContentCache.getObject("webName");

        ActionBar actionBar = getSupportActionBar();
        //Log.d("DDGViewActivity", "category = " + category);
        actionBar.setTitle(category);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(android.R.color.transparent);
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     * added to handle orientation change.  Not sure why this is needed, but it is.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {        
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        switch (itemId)
        {
            case R.id.prayerlist:
                Intent prayerIntent = new Intent(getApplicationContext(), PrayerListActivity.class);
                startActivity(prayerIntent);
                break;
            case R.id.bcp:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bcponline.org/"));
                startActivity(browserIntent);
                break;
            case R.id.lectionary:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(CPUtil.buildLectionaryURL(getResources().getConfiguration().locale)));
                startActivity(i);
                break;
            case R.id.rate:
                CPUtil.openPlayStore(this);
                break;
            case android.R.id.home:
                Intent mainIntent = new Intent(getApplicationContext(), SublistActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(mainIntent);
                break;
            default:
                break;
        }
        
        return true;
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        ContentCache.setObject(getString(R.string.url), url);
    }
    
    
}
