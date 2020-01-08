/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.acrylicgoat.bcponline.beans.Category;
import com.acrylicgoat.bcponline.fragments.*;
import com.acrylicgoat.bcponline.util.ContentCache;
import com.acrylicgoat.bcponline.util.CPUtil;
import com.acrylicgoat.bcponline.R;

/**
 * @author ed woodward
 *
 */
public class SublistActivity extends AppCompatActivity implements CPListFragment.OnDDGSelectedListener
{
    private Category category;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        Display d = getWindowManager().getDefaultDisplay();
        if(CPUtil.isTabletDevice(d, this))
        {
            setContentView(R.layout.list_tablet_fragment);
        }
        else
        {
            setContentView(R.layout.list_fragment);
        }
        
        Intent intent = getIntent();
        category = (Category)intent.getSerializableExtra(getString(R.string.category));
        if(category==null && savedInstanceState != null)
        {
            //Log.d("ViewLenses.onCreate()", "getting saved data");
            category = (Category)savedInstanceState.getSerializable(getString(R.string.display));
        }
        
        
        //Log.d("SublistActivity", "category = " + category);
        actionBar.setTitle(category.getName());
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(android.R.color.transparent);
        ContentCache.setObject(getString(R.string.display), category);

        
    }
    
    @Override
    public void onDDGSelected(final String tutUrl) 
    {
        final CPViewFragment viewer = (CPViewFragment) getFragmentManager().findFragmentById(R.id.view_fragment);
        
        ContentCache.setObject(getString(R.string.url), tutUrl);
        ContentCache.setObject("webName", category.getName());

        if (viewer == null || !viewer.isInLayout()) 
        {
            Intent showContent = new Intent(getApplicationContext(),CPViewActivity.class);
            showContent.setData(Uri.parse(tutUrl));
            startActivity(showContent);
        } 
        else 
        {
            runOnUiThread(new Runnable(){
                public void run()
                {
                    viewer.updateUrl(tutUrl); 
                }
            });
            
        }
        
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
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                break;
            default:
                break;
        }
        return true;
    }
    
    @Override
    protected void onResume() 
    {
        super.onResume();
        Category category = (Category)ContentCache.getObject(getString(R.string.display));
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(category.getName());
    }
    
    @Override
    protected void onPause() 
    {
        super.onPause();
        ContentCache.setObject(getString(R.string.display), category);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        ContentCache.setObject(getString(R.string.display), category);
        outState.putSerializable(getString(R.string.display), category);
    }
    
    
}
