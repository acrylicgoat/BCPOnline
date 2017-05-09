/**
 * Copyright (c) 2014 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.util;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.view.Display;

import com.acrylicgoat.bcponline.R;

/**
 * @author Ed Woodward
 *
 */
public class CPUtil
{
    public static boolean isTabletDevice(Display d, Activity context)
    {

        Point p = new Point();
        d.getSize(p);

        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        if(isTablet && p.x > 1200)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    

    public static String buildLectionaryURL(Locale locale)
    {
        StringBuilder sb = new StringBuilder();
        Calendar now = Calendar.getInstance(locale);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) + 1;
        int year = now.get(Calendar.YEAR);
        sb.append("http://prayer.forwardmovement.org/the_daily_readings.php?d=");
        sb.append( day + "&m=");
        sb.append( month + "&y=" + year);
        
        return sb.toString();
    }

    public static void openPlayStore(Context c)
    {
        c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.acrylicgoat.bcponline")));

    }

}
