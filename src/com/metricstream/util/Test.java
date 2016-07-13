package com.metricstream.util;

import java.text.DecimalFormat;  
import java.text.NumberFormat;
import java.util.*;
import com.metricstream.domain.CompactObject;
import com.metricstream.domain.ObjectItem;
import com.metricstream.util.fileUtil;

import gnu.trove.map.hash.THashMap;

public class Test {
	public static void main(String args[]){
	  THashMap<Integer,String> objects=new THashMap<Integer,String>();
	  objects.put(0,"SYSTEMI_Intra_Module_Issue_Trigger_Status_Report");
	  objects.put(1,"MS_ISM_Triggered_issues");
	  objects.put(2,"MS_ISM_Triggered_Issues_Response");
	  objects.put(3,"MS_ISM_TRIGGER_ISSUE_DRILL_RPT");
//	  objects.put(4,"MS_ISM_AUDIT_TRAIL_INFOLET");
//	  objects.put(5,"MS_ISM_Action_List_Report_DrillDown");
//	  objects.put(6,"MS_ISM_001_Issue_List_Report");
//	  objects.put(7,"auditx");
//	  objects.put(8,"audituy");
//	  int j = 0;
      THashMap<Integer,ArrayList<Integer>> adjacency=new THashMap<Integer,ArrayList<Integer>>();
      adjacency.put(0,new ArrayList<Integer>(Arrays.asList(1,2)));
      adjacency.put(1,new ArrayList<Integer>());
      adjacency.put(2,new ArrayList<Integer>(Arrays.asList(3)));
      adjacency.put(3,new ArrayList<Integer>());
//      adjacency.put(4,new ArrayList<Integer>(Arrays.asList(1,3)));
//      adjacency.put(5,new ArrayList<Integer>(Arrays.asList(6)));
//      adjacency.put(6,new ArrayList<Integer>(Arrays.asList(5)));
//      adjacency.put(7,new ArrayList<Integer>(Arrays.asList(6)));
//      adjacency.put(8,new ArrayList<Integer>(Arrays.asList(7)));
  /*for(int i=7;i<=10;i++){
    	  objects.put(i,String.format("Audit%d",i));
    	  adjacency.put(i,new ArrayList<Integer>(Arrays.asList(i-1)));
    	      	  
  }*/
      
     long start=System.currentTimeMillis();
      System.out.println("Starting the process");
      MatrixUtil util_ins=new MatrixUtil(adjacency);
//      System.out.println("finished creating the matrices");
     
      CompactObject objectlist=new CompactObject();
//      Object[]arr=adjacency.keySet().toArray();
//      TIntHashSet max=new TIntHashSet((Arrays.copyOf(arr,adjacency.keySet().size())));
     
      objectlist=util_ins.categorize();
      fileUtil newins=new fileUtil();
      ArrayList<ObjectItem> object=new ArrayList<ObjectItem>();
      object=objectlist.getItems();
      try{
       newins.generateXml(object, objects);
      }catch(Exception e){
    	  e.printStackTrace();
      }
      for(ObjectItem ob:object){
    	  System.out.println(objects.get(ob.getObjectone())+"|"+objects.get(ob.getObjecttwo())+"|"+ob.getRelation());
//      System.out.println(ob.getObjectone()+"|"+ob.getObjecttwo()+"|"+ob.getRelation());
      }
    long end=System.currentTimeMillis();
      NumberFormat formatter=new DecimalFormat("#0.00000");
      System.out.println("Duration:"+formatter.format((end-start)/1000d)+"Seconds");
      int mb=1024*1024;
      Runtime runtime=Runtime.getRuntime();
      System.out.println("Used memory:"+(runtime.maxMemory()-runtime.freeMemory())/mb);
      
	}
}
	
	
	
	
	
	


