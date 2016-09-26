package com.way.betterdeal.object;

import java.util.ArrayList;

public class CategoryCell {
	public String title;
	public String picUrl;
	public ArrayList<CategoryCell> childCells;
	public CategoryCell(String tt){
		title=tt;
		childCells=new ArrayList<CategoryCell>();
	}
}
