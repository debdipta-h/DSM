package com.metricstream.domain;

import java.util.ArrayList;

import gnu.trove.list.array.TIntArrayList;


public class CompactObject {
private ArrayList<ObjectItem> items;
private TIntArrayList coupleditems;

public CompactObject(){}
public CompactObject(ArrayList<ObjectItem> items,TIntArrayList coupleditems){
	setCoupleditems(coupleditems);
	setItems(items);
}
public TIntArrayList getCoupleditems() {
	return coupleditems;
}
private void setCoupleditems(TIntArrayList coupleditems) {
	this.coupleditems = coupleditems;
}
public ArrayList<ObjectItem> getItems() {
	return items;
}
private void setItems(ArrayList<ObjectItem> items) {
	this.items = items;
}

}
