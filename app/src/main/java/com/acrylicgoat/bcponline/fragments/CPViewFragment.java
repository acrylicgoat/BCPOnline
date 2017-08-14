/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.fragments;

import com.acrylicgoat.bcponline.util.ContentCache;
import com.acrylicgoat.bcponline.util.CPUtil;
import com.acrylicgoat.bcponline.R;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;

/**
 * @author Ed Woodward
 *
 */
public class CPViewFragment extends Fragment
{
    private WebView viewer = null;
    boolean flag = false;
    Float progress = 0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
    {
        if(savedInstanceState != null) 
        {
            Float f = savedInstanceState.getFloat(getString(R.string.progress), 0);
            if(f != null)
            {
                progress = f;
            }
        }
        viewer = (WebView) inflater.inflate(R.layout.web_view, container, false);
        viewer.getSettings().setSupportZoom(true);
        viewer.getSettings().setJavaScriptEnabled(true);
        viewer.getSettings().setBuiltInZoomControls(true);
        viewer.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

        if(CPUtil.isTabletDevice(getActivity().getWindowManager().getDefaultDisplay(), getActivity()))
        {
            //Log.d("CRViewFragment", "is tablet");
            viewer.getSettings().setUseWideViewPort(true);
            viewer.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        }
        else
        {
            viewer.setInitialScale(245);
        }
       
        viewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            if (url.contains("#") && flag == false)
            {
                //Log.d("CRViewFragment", "Reloading URL");
                viewer.loadUrl(url);
                flag = true;

            }
            else
            {
                flag = false;
            }
            }

        });
        viewer.getSettings().setDefaultFontSize(15);

        return viewer;
    }
    
    public void updateUrl(final String newUrl) 
    {
        Log.d("viewer","newURL: " + newUrl);
        if (viewer != null) 
        {
            ContentCache.setObject(getString(R.string.url), newUrl);

            viewer.loadUrl(newUrl); 
        }
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        progress = calculateProgression(viewer);
        ContentCache.setObject(getString(R.string.progress), progress);
        
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) 
    {
        progress = calculateProgression(viewer);
        outState.putFloat(getString(R.string.progress), progress);
        ContentCache.setObject(getString(R.string.progress), progress);
        //Log.d("CPVwFrag.onSvInstncStte()", "progress = " + progress);
    }
    
    private float calculateProgression(WebView content) 
    {
        float positionTopView = content.getTop();
        float contentHeight = content.getContentHeight();
        float currentScrollPosition = content.getScrollY();
        return (currentScrollPosition - positionTopView) / contentHeight;
    }


    

}
