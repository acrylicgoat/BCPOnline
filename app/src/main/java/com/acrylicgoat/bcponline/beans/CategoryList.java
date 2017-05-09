/**
 * Copyright (c) 2017 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.bcponline.beans;

import java.util.ArrayList;

/**
 * author: Ed Woodward
 */

public class CategoryList
{
    ArrayList<Category> categoryList;

    public ArrayList<Category> getCategoryList()
    {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList)
    {
        this.categoryList = categoryList;
    }
}
