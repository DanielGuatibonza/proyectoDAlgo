package scripts;

public class ProblemaB {

	public void full(int[] A, int[] B){
		String[] arreglo = estructurar(A, B);
		QuickSort ob = new QuickSort(); 
        ob.sort(arreglo, 0, arreglo.length-1); 
      
	}
	
	public String[] estructurar(int[] A, int[] B){
		String[] salida = new String[A.length];
		for(int e=0;e<A.length;e++){
			salida[e]=A[e]+":"+B[e];
		}
		return salida;
	}
	
	class QuickSort 
	{ 
	    /* This function takes last element as pivot, 
	       places the pivot element at its correct 
	       position in sorted array, and places all 
	       smaller (smaller than pivot) to left of 
	       pivot and all greater elements to right 
	       of pivot */
	    int partition(String arr[], int low, int high) 
	    { 
	        int pivot = Integer.parseInt(arr[high].split(":")[0]);  
	        int i = (low-1); // index of smaller element 
	        for (int j=low; j<high; j++) 
	        { 
	            // If current element is smaller than or 
	            // equal to pivot 
	            if (Integer.parseInt(arr[j].split(":")[0]) <= pivot) 
	            { 
	                i++; 
	  
	                // swap arr[i] and arr[j] 
	                String temp = arr[i]; 
	                arr[i] = arr[j]; 
	                arr[j] = temp; 
	            } 
	        } 
	  
	        // swap arr[i+1] and arr[high] (or pivot) 
	        String temp = arr[i+1]; 
	        arr[i+1] = arr[high]; 
	        arr[high] = temp; 
	  
	        return i+1; 
	    } 
	  
	  
	    /* The main function that implements QuickSort() 
	      arr[] --> Array to be sorted, 
	      low  --> Starting index, 
	      high  --> Ending index */
	    void sort(String arr[], int low, int high) 
	    { 
	        if (low < high) 
	        { 
	            /* pi is partitioning index, arr[pi] is  
	              now at right place */
	            int pi = partition(arr, low, high); 
	  
	            // Recursively sort elements before 
	            // partition and after partition 
	            sort(arr, low, pi-1); 
	            sort(arr, pi+1, high); 
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
