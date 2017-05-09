/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.acrylicgoat.bcponline.R;
import com.acrylicgoat.bcponline.beans.Category;

public class ListAdapter extends ArrayAdapter<Category>
{
    /** Current context */
    private Context context;
    /** List of Content objects to display*/
    private ArrayList<Category> contentList;

    
    public ListAdapter(Context context,ArrayList <Category> contentList)
    {
        super(context, android.R.layout.simple_list_item_1, contentList);
        this.context = context;
        this.contentList = contentList;
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        CategoryHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_item, null);
            holder = new CategoryHolder(v);
            v.setTag(holder);
        }
        else
        {
            holder= (CategoryHolder)v.getTag();
            if(holder == null)
            {
                holder = new CategoryHolder(v);
                v.setTag(holder);
            }
        }

        Category d = contentList.get(position);
        if(d != null)
        {
            //Log.d("LandingListAdapter", "title = " + c.title);
            TextView name = holder.nameView;
            if(name != null)
            {
                holder.nameView.setText(d.getName().trim());
            }

        }
        else
        {
            //Log.d("LandingListAdapter", "Content is null");
        }

        return v;
    }

    public class CategoryHolder
    {
        TextView nameView;

        public CategoryHolder (View base)
        {
            nameView = (TextView)base.findViewById(R.id.itemName);
        }

    }

}
