/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */

package com.acrylicgoat.bcponline.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.acrylicgoat.bcponline.adapters.MainRecyclerViewAdapter;
import com.acrylicgoat.bcponline.beans.Category;
import com.acrylicgoat.bcponline.beans.CategoryList;
import com.acrylicgoat.bcponline.util.CPUtil;
import com.acrylicgoat.bcponline.R;
import com.google.gson.Gson;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
{
    
    ArrayList<String> initialList;
    RecyclerView recyclerView;
    MainRecyclerViewAdapter adapter;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);
        
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MainRecyclerViewAdapter(getContent(), R.layout.card, this);
        recyclerView.setAdapter(adapter);
       
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setIcon(android.R.color.transparent);
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
            default:
                break;
        }
        
        return true;
    }

    private ArrayList<Category> getContent()
    {
        return readJson().getCategoryList();
    }

    private CategoryList readJson()
    {
        AssetManager assets = getAssets();
        CategoryList catList = new CategoryList();

        Gson gson = new Gson();

        try
        {
            InputStream is = assets.open("landingList.json");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            catList = gson.fromJson(bf,CategoryList.class);
        }
        catch(IOException ioe)
        {
            Log.d("json", "Some problem: " + ioe.toString());
        }

        return catList;

    }
    

    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.initial_list), initialList);
        
    }

}
