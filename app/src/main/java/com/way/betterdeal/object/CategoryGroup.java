package com.way.betterdeal.object;

import java.util.ArrayList;

public class CategoryGroup {
	
	public String groupName;
	public ArrayList<CategoryCell> cells;
	public CategoryGroup(String gName,String[] cellNames){
		groupName=gName;
		cells=new ArrayList<CategoryCell>();
		for(int i=0;i<cellNames.length;i++){
			cells.add(new CategoryCell(cellNames[i]));
		}
	}

}
