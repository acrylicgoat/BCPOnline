/**
 * Copyright (c) 2017 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acrylicgoat.bcponline.R;
import com.acrylicgoat.bcponline.activity.SublistActivity;
import com.acrylicgoat.bcponline.beans.Category;

import java.util.ArrayList;

/**
 * author: Ed Woodward
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>
{
    private int rowLayout;
    private Context context;
    private ArrayList<Category> contentList;

    public MainRecyclerViewAdapter(ArrayList<Category> content, int rowLayout, Context context)
    {
        contentList = content;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v,contentList);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        Category category = contentList.get(i);
        //Log.d("rv","title: " + about.getTitle() + " details: "+ about.getDetails() + " logo: "+ about.getLogo());
        String name = category.getName();
        viewHolder.name.setText(name);
        if(name.equals(context.getString(R.string.daily_office)))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_account_balance_white_24dp);
        }
        else if(name.equals(context.getString(R.string.collects)))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_collections_white_24dp);
        }
        else if(name.equals(context.getString(R.string.eucharist)))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_blur_circular_white_24dp);
        }
        else if(name.equals(context.getString(R.string.prayers)))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_wifi_tethering_white_24dp);
        }
        else if(name.equals(context.getString(R.string.psalter)))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_library_music_white_24dp);
        }
        else if(name.equals(context.getString(R.string.thanksgivings)))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_list_white_24dp);
        }
        else if(name.equals(context.getString(R.string.pastoral)))
        {
            viewHolder.logo.setImageResource(R.drawable.outline_emoji_people_white_24);
        }

    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView name;
        private TextView url;
        private ImageView logo;
        ArrayList<Category> contentList;

        public ViewHolder(View itemView, ArrayList<Category> contentList)
        {
            super(itemView);
            this.contentList = contentList;

            name = (TextView) itemView.findViewById(R.id.name);
            logo = (ImageView) itemView.findViewById(R.id.logoView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            Category cat = contentList.get(position);
            Intent listIntent = new Intent(v.getContext(),SublistActivity.class);
            listIntent.putExtra(v.getContext().getString(R.string.category),cat);
            v.getContext().startActivity(listIntent);

        }
    }
}
