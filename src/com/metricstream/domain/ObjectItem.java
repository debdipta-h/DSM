package com.metricstream.domain;

public class ObjectItem{
	private int objectone;
	private int objecttwo;
	private String relation;
	public ObjectItem(){}
	public ObjectItem(Integer objectOne,Integer objectTwo,String Relation){
		setObjectone(objectOne);
		setObjecttwo(objectTwo);
		setRelation(Relation);
	}
	public int getObjectone() {
		return objectone;
	}
	private void setObjectone(int objectone) {
		this.objectone = objectone;
	}
	public int getObjecttwo() {
		return objecttwo;
	}
	private void setObjecttwo(int objecttwo) {
		this.objecttwo = objecttwo;
	}
	public String getRelation() {
		return relation;
	}
	private void setRelation(String relation) {
		this.relation = relation;
	}
	
}
