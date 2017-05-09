/**
* Copyright (c) 2015 Acrylic Goat Software
*
* This software is subject to the provisions of the GNU Lesser General
* Public License Version 3 (LGPL).  See LICENSE.txt for details.
*/
package com.acrylicgoat.bcponline.beans;


import java.io.Serializable;

/**
 * Author: Ed Woodward
 */
public class Category implements Serializable
{
    private String name;
    private String url;

    public Category(String name,String url)
    {
        this.name = name;
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


}
