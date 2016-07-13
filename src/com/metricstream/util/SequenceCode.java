package com.metricstream.util;

public enum SequenceCode {
	Coupled('C'),Parallel('P'),Sequential('S');
	private final char Code;
    SequenceCode(char Code){
		this.Code=Code;	
	}
	
	char getCode(){
		return Code;
	}

}
