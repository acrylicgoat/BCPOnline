/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.acrylicgoat.bcponline.adapters.LandscapeListAdapter;
import com.acrylicgoat.bcponline.adapters.ListAdapter;
import com.acrylicgoat.bcponline.beans.Category;
import com.acrylicgoat.bcponline.beans.CategoryList;
import com.acrylicgoat.bcponline.util.ContentCache;
import com.acrylicgoat.bcponline.util.CPUtil;
import com.acrylicgoat.bcponline.R;
import com.google.gson.Gson;

import android.app.ActionBar;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * @author Ed Woodward
 *
 */
public class CPListFragment extends ListFragment
{
    private OnDDGSelectedListener ddgSelectedListener;
    ListAdapter adapter;
    LandscapeListAdapter landAdapter;
    String category;
    HashMap<String, String> map;
    private int curPosition = -1;
    boolean dualFragments = false;
    ActionBar aBar;
    FragmentActivity sherActivity;
    View prevView;
    private ArrayList<Category> contentList;
    

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) 
    {

        v.setSelected(true);
        curPosition = position;
        prevView = v;
        Category item = (Category)l.getItemAtPosition(position);
        selectPosition();
        if(dualFragments)
        {
            if(aBar != null)
            {
                aBar.setSubtitle(item.getName());
            }
            landAdapter.notifyDataSetChanged();
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
        
        String url = item.getUrl();
        ddgSelectedListener.onDDGSelected(url);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //get category
        Intent intent = getActivity().getIntent();
        Category category = (Category)intent.getSerializableExtra(getString(R.string.category));
        if(category != null)
        {
            contentList = getCategory(category.getName());
        }
        else
        {
            contentList = new ArrayList<>();
        }


        if (savedInstanceState != null)
        {
            curPosition = savedInstanceState.getInt(getString(R.string.list_position));
        }

    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) 
    {
        super.onActivityCreated(savedInstanceState);
        ListView lv = getListView();
        lv.setCacheColorHint(Color.TRANSPARENT);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        if (CPUtil.isTabletDevice(getActivity().getWindowManager().getDefaultDisplay(), getActivity()))
        {
            Category item = contentList.get(0);
            ddgSelectedListener.onDDGSelected(item.getUrl());
            dualFragments = true;
        }
        aBar = sherActivity.getActionBar();
        
        
        if(dualFragments)
        {
            landAdapter = new LandscapeListAdapter(getActivity().getApplicationContext(), contentList);
            setListAdapter(landAdapter);
            
        }
        else
        {
            adapter = new ListAdapter(getActivity().getApplicationContext(), contentList);
            setListAdapter(adapter);
            lv.setBackgroundResource(R.color.color_list_background);
        }
        
    }
    
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        ContentCache.setObject(getString(R.string.category), category);
        ContentCache.setObject(getString(R.string.category_list), contentList);
        ContentCache.setObject(getString(R.string.category_map), map);
        outState.putInt(getString(R.string.list_position), curPosition);
        
    }
    
    public void onPause()
    {
        super.onPause();
        ContentCache.setObject(getString(R.string.category), category);
        ContentCache.setObject(getString(R.string.category_list), contentList);
        ContentCache.setObject(getString(R.string.category_map), map);
        
    }
    
    public interface OnDDGSelectedListener 
    {
        public void onDDGSelected(String tutUrl);
    }

    @Override
    public void onAttach(Activity activity) 
    {
        super.onAttach(activity);
        sherActivity = (FragmentActivity)activity;
        try 
        {
            ddgSelectedListener = (OnDDGSelectedListener) activity;
        } 
        catch (ClassCastException e) 
        {
            throw new ClassCastException(activity.toString() + " must implement OnDDGSelectedListener");
        }
    }

    private ArrayList<Category> getCategory(String category)
    {
        CategoryList cl = new CategoryList();

        if(category.equals(getString(R.string.collects)))
        {
            cl = readJson("collects.json");
        }
        else if(category.equals(getString(R.string.daily_office)))
        {
            cl = readJson("dailyOffice.json");
        }
        else if(category.equals(getString(R.string.eucharist)))
        {
            cl = readJson("eucharist.json");
        }
        else if(category.equals(getString(R.string.prayers)))
        {
            cl = readJson("prayers.json");
        }
        else if(category.equals(getString(R.string.psalter)))
        {
            cl = readJson("psalter.json");
        }
        else if(category.equals(getString(R.string.thanksgivings)))
        {
            cl = readJson("thanksgivings.json");
        }
        else if(category.equals(getString(R.string.eucharist)))
        {
            cl = readJson("eucharist.json");
        }
        return cl.getCategoryList();
    }

    private CategoryList readJson(String fileName)
    {
        AssetManager assets = getActivity().getAssets();
        CategoryList catList = new CategoryList();

        Gson gson = new Gson();

        try
        {
            InputStream is = assets.open(fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            catList = gson.fromJson(bf,CategoryList.class);
        }
        catch(IOException ioe)
        {
            Log.d("json", "Some problem: " + ioe.toString());
        }

        return catList;

    }
    
    public void selectPosition() 
    {
        ListView lv = getListView();
        if (dualFragments)
        {
            //Log.d("DDGListFragment","using dualFragments" );
            //Log.d("DDGListFragment","position = " + curPosition);

            lv.setItemChecked(curPosition, true);
        }
        else
        {
            lv.setItemChecked(curPosition, false);
        }

    }

}
