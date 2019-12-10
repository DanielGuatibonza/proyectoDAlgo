package scripts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemaB {

	public String full(int[] A, int[] B){
//		QuickSort ob = new QuickSort(); 
//        ob.sort(arreglo, 0, arreglo.length-1); 
      int[][] adyacencias = new int[A.length][B.length];
      Graph g = new Graph();
      for(int e=0;e<A.length;e++){
    	  Node actual = new Node(e+1);
    	  g.addNode(actual);
      }
      
      for(int i=0;i<A.length;i++){
    	  for(int j=0;j<B.length;j++){
    		  if(i==j){
    			  adyacencias[i][j]=-1;
    		  }
    		  else if(A[i]<=A[j] && B[i]>B[j]){
    			  adyacencias[i][j]=1;
    			  Node actual = g.getNode(j+1);
    			  g.getNode(i+1).addNeighbor(actual);
    		  }
    		  else
    			  adyacencias[i][j]=0;
    	  }
      }
      
      String mayor = "";
      for(int e=0;e<A.length;e++){
    	  String actualE = busqueda(g,e+1);
    	  if(actualE.split(":").length>mayor.split(":").length){
    		  mayor=actualE;
    	  }
      }

	  String respuesta ="*";
	  if(!mayor.equals(""))
		  respuesta=mayor.replace(":", " ");
	  return respuesta;

	}
	
	public String busqueda(Graph g, int index){
		String mayor = index+"";
		for(Node node: g.getNode(index).getNeighbors()){
			String este = index+":"+busqueda(g, node.getId());
			if(este.split(":").length>mayor.split(":").length){
				mayor = este;
			}
		}
		return mayor;
	}
	
	public class Graph {
	    private List<Node> nodes;

	    public Graph() {
	        this.nodes = new ArrayList<>();
	    }

	    public Graph(List<Node> nodes) {
	        this.nodes = nodes;
	    }

	    public void addNode(Node e) {
	        this.nodes.add(e);
	    }

	    public List<Node> getNodes() {
	        return nodes;
	    }

	    public Node getNode(int searchId) {
	        for (Node node:this.getNodes()) {
	            if (node.getId() == searchId) {
	                return node;
	            }
	        }
	        return null;
	    }

	    public int getSize() {
	        return this.nodes.size();
	    }

	}
	
	public class Node {
	    private int id;
	    private List<Node> neighbors;

	    public Node(int id) {
	        this.id = id;
	        this.neighbors = new ArrayList<Node>();
	    }

	    public void addNeighbor(Node e) {
	        this.neighbors.add(e);
	    }

	    public int getId() {
	        return id;
	    }

	    public List<Node> getNeighbors() {
	        return neighbors;
	    }

	}
	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ProblemaB yo = new ProblemaB();
		int[] A = null;
		int[] B = null;
 		try(InputStreamReader isr=new InputStreamReader(System.in); BufferedReader br = new BufferedReader(isr)){
 			String linea = br.readLine();

 			while(linea!=null && linea.length()>0 && !"0".equals(linea)){
 				int cantidad = Integer.parseInt(linea);
 				A = new int[cantidad];
 				B = new int[cantidad];
 				for(int e=0;e<cantidad;e++){
 					linea = br.readLine();
 					A[e] = Integer.parseInt(linea.split(" ")[1]);
 					B[e] = Integer.parseInt(linea.split(" ")[2]);
 				}
 				String res = yo.full(A, B); 
 				if(res.equals("*"))
 					System.out.println("0");
 				else
 				System.out.println(res.split(" ").length);
 				System.out.println(res);
 				linea=br.readLine();
 			}
 		}

	}

}