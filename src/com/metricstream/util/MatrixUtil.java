package com.metricstream.util;
/**
 * Author-Debdipta Halder
 *  
 *Generation of reachability matrix   
 *manipulation of reachability matrix to get the permuted upper triangular matrix
 *
 **/

import java.util.ArrayList; 
import java.util.LinkedList;
import java.util.Map;
//import java.util.List;
//import java.util.Map;
import java.util.Queue;
import com.metricstream.domain.CompactObject;
import com.metricstream.domain.ObjectItem;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TIntHashSet;

public class MatrixUtil {
	
	private  THashMap<Integer,TIntArrayList> reachabilityMatrix;
	private  THashMap<Integer,TIntArrayList> antecedentMatrix;
	private  TIntArrayList PermutedRows;
	
	public MatrixUtil(THashMap<Integer,ArrayList<Integer>> adjacency){
		getReachabilityMatrix(adjacency);
		getAntecedentMatrix(reachabilityMatrix);
		getPermutedRows(reachabilityMatrix,antecedentMatrix);
	}
	
	//generation of the reachability matrix from a given adjacency matrix.
	private void getReachabilityMatrix(THashMap<Integer,ArrayList<Integer>> nodes){
		if(nodes.isEmpty()){
			System.err.println("The matrix is empty");
			System.exit(1);
		}
		int curr_node;
		THashMap<Integer,TIntArrayList> reachability=new THashMap<Integer,TIntArrayList>();
		Queue<Integer> reachables=new LinkedList<Integer>();
		for(int node:nodes.keySet()){
			TIntArrayList reach=new TIntArrayList();
			reachables.add(node);
			while(reachables.size()>0){
				curr_node=reachables.remove();
				if(!reach.contains(curr_node)){
					reach.add(curr_node);
					for(Integer r_node:nodes.get(curr_node))
						reachables.add(r_node);
				}
				
			  }
			reachability.put(node,reach);
			
			}
//		System.out.println("Created the reachability matrix");
		this.reachabilityMatrix=reachability;
//		System.out.println(reachability+"\n");
		}
	
	
	//finds transpose of a given matrix.
	private void getAntecedentMatrix(THashMap<Integer,TIntArrayList> input_matrix){
	    int column; 
		THashMap<Integer,TIntArrayList>transpose_matrix=new THashMap<Integer,TIntArrayList>();
		for(Map.Entry<Integer,TIntArrayList> matrix_row:input_matrix.entrySet()){
			Integer row=matrix_row.getKey();
			TIntHashSet columnset=new TIntHashSet(matrix_row.getValue());
			TIntIterator iterations=columnset.iterator();
			while(iterations.hasNext()){
				column=iterations.next();
				if(!transpose_matrix.containsKey(column))
					transpose_matrix.put(column,new TIntArrayList());
				transpose_matrix.get(column).add(row);
			}	
		 }
//		System.out.println("Created the antecedent matrix");
		this.antecedentMatrix=transpose_matrix;
//		System.out.println(transpose_matrix+"\n");
	}
	
	
	private boolean isFreeObject(TIntHashSet reachableset,TIntHashSet antecedentset,int index){
		TIntHashSet test=new TIntHashSet();
		test.add(index);
		if(reachableset.equals(test)&& antecedentset.equals(test))
			return true;
		else 
			return false;
	}
		
	
//   permute the matrix
	public void getPermutedRows(THashMap<Integer,TIntArrayList>reachability,THashMap<Integer,TIntArrayList>antecedents){
		int index;
	  TIntArrayList permuted_rows=new TIntArrayList();
	  THashMap<Integer,TIntArrayList> reachables=new THashMap<Integer,TIntArrayList>();
	  reachables.putAll(reachability);
//	  boolean flag;
 	  TIntHashSet values;
 	 TIntHashSet indices=new TIntHashSet(reachables.keySet());
//	  TIntHashSet independents=new TIntHashSet();
	  while(!indices.isEmpty()){
//		  System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||");
		  TIntIterator iter=indices.iterator();
		  TIntArrayList x_rows=new TIntArrayList(permuted_rows);
		  
		  while(iter.hasNext()){
			  index=iter.next();
    	   values=new TIntHashSet(reachables.get(index));
    	   values.removeAll(x_rows);
    	  TIntHashSet reachableset=new TIntHashSet(reachables.get(index));
    	  TIntHashSet antecedentset=new TIntHashSet(antecedents.get(index));
    	  if(reachableset.size()==1 && antecedentset.size()==1){
    		  if(isFreeObject(reachableset,antecedentset,index)){
    			 permuted_rows.insert(0,index);
//    			 independents.add(index);
    		   }
    		  }
    	  reachableset.removeAll(x_rows);
    	  antecedentset.removeAll(x_rows);
    	  reachableset.retainAll(antecedentset);
    	  if(reachableset.equals(values)){
    	     permuted_rows.add(index);
//    	     independents.add(index);
    	  }  
		 }
		indices.removeAll(permuted_rows); 
	  }	  
//	  System.out.println("Created the permuted rows");
      this.PermutedRows=permuted_rows;
//      System.out.println(permuted_rows);
	}	  
	
	
	
// get the sequence categorization
	public CompactObject categorize(){
	    TIntArrayList coupled=new TIntArrayList();
	    ArrayList<ObjectItem> relations=new ArrayList<ObjectItem>();
	    int val1,val2;
		for(int i=0;i<PermutedRows.size()-1;i++){
			val1=PermutedRows.get(i);
			val2=PermutedRows.get(i+1);
			TIntArrayList list1=reachabilityMatrix.get(val1);
			TIntArrayList list2=reachabilityMatrix.get(val2);
			if(list1.contains(val2)&& list2.contains(val1)){
				coupled.add(val1);
				coupled.add(val2);
				relations.add(new ObjectItem(val1,val2,SequenceCode.Coupled.toString()));
				i++;
				
			}
			else if(list2.contains(val1)&&!list1.contains(val2)){
				relations.add(new ObjectItem(val1,val2,SequenceCode.Sequential.toString()));
			}
			else
				relations.add(new ObjectItem(val1,val2,SequenceCode.Parallel.toString()));
	     }
		System.out.println("Finished creating the sequence");
		return new CompactObject(relations,coupled);
	}
   	
}	
	


