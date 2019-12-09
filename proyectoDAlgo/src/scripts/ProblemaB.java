package scripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemaB {
	
	public void full(int[] A, int[] B){
		//		QuickSort ob = new QuickSort(); 
		//        ob.sort(arreglo, 0, arreglo.length-1); 
		int[][] adyacencias = new int[A.length][B.length];
      
      for(int i=0;i<A.length;i++){
    	  for(int j=0;j<B.length;j++){
    		  if(i==j){
    			  adyacencias[i][j]=-1;
    		  }
    		  else if(A[i]<=A[j] && B[i]>B[j]){
    			  adyacencias[i][j]=1;
    		  }
    		  else
    			  adyacencias[i][j]=0;
    	  }
      }

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProblemaB yo = new ProblemaB();
		int[] A = {601,600,50,100,110,600,800,600,200};
		int[] B = {65,105,100,200,150,100,70,60,95};
		yo.full(A, B);
	}

}
