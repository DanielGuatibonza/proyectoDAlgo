package scripts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proyecto Final Disenio y Analisis de Algoritmos
 * @author Daniel Mateo Guatibonza Solano 201611360 - Juan David Diaz Cristancho 201729408
 *
 */

/**
 * Clase de Problema B: se encarga de construir la solucion a los distintos escenarios de prueba bajo los cuales se puede probar
 * el correcto funcionamiento del Problema B
 *
 */
public class ProblemaB {

	public String full(int[] A, int[] B){ 
		//Adyacencias es una matriz de enteros la cual corresponde a las adyacencias existentes entre los datos
		//las adyacencias se reflejan la posibilidad de un dato de poder estar concatenado a otro respetando la condicion de
		//ser un conrta ejemplo
		
		int[][] adyacencias = new int[A.length][B.length];
		
		//La matriz de adyacencias se representa como un grafo
		Graph g = new Graph();
		
		//Se inicializan los nodos del grafo para luego poder establecer las conexiones entre ellos (arcos)
		for(int e=0;e<A.length;e++){
			Node actual = new Node(e+1);
			g.addNode(actual);
		}

		//Se inicializa la matriz de adyacencias al mismo tiempo que se incializa el grafo, para no realizar varios recorridos
		for(int i=0;i<A.length;i++){
			for(int j=0;j<B.length;j++){
				//Un dato no puede ser contra ejemplo con si mismo
				if(i==j){
					adyacencias[i][j]=-1;
				}
				//Si A aumenta o se mantiene y B disminuye entonces hay un arco que conecta estos dos datos, el cual se representa con un 1 
				// 0 si no existe un arco. -1 si es el mismo i j
				else if(A[i]<=A[j] && B[i]>B[j]){
					adyacencias[i][j]=1;
					//Se agregan los nodos hermanos
					Node actual = g.getNode(j+1);
					g.getNode(i+1).addNeighbor(actual);
				}
				else
					adyacencias[i][j]=0;
			}
		}

		//Se hace DFS sobre cada uno de los nodos y se construye una cadena donde cada numero es el id de un nodo que compone el camino
		//que comienza desde un e+1 nodo.
		String mayor = "";
		for(int e=0;e<A.length;e++){
			String actualE = busqueda(g,e+1);
			if(actualE.split(":").length>mayor.split(":").length){
				mayor=actualE;
			}
		}

		//Consideraciones para satisfacer los outputs
		String respuesta ="*";
		if(!mayor.equals(""))
			respuesta=mayor.replace(":", " ");
		return respuesta;

	}

	//Algoritmo sencillo de DFS que construye el string que corresponde al camino
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

	/**
	 * Implementacion de un grafo dirigido sin pesos
	 *
	 */
	public class Graph {
		
		/**
		 * Lista de Node que representa los vertices de un grafo
		 */
		private List<Node> nodes;

		/**
		 * Constructor de Graph
		 */
		public Graph() {
			this.nodes = new ArrayList<>();
		}

		/**
		 * Constructor de Graph
		 * @param nodes - Recibe una lista de nodos
		 */
		public Graph(List<Node> nodes) {
			this.nodes = nodes;
		}

		/**
		 * Agrega un nodo al grafo
		 * @param e - Nodo a agregar
		 */
		public void addNode(Node e) {
			this.nodes.add(e);
		}

		/**
		 * Retorna los nodos del grafo
		 * @return
		 */
		public List<Node> getNodes() {
			return nodes;
		}

		/**
		 * 
		 * @param searchId
		 * @return
		 */
		public Node getNode(int searchId) {
			for (Node node:this.getNodes()) {
				if (node.getId() == searchId) {
					return node;
				}
			}
			return null;
		}

		/**
		 * Retorna el tamanio del grafo, es decir, la cantidad de vertices que tiene
		 * @return
		 */
		public int getSize() {
			return this.nodes.size();
		}

	}

	/**
	 * Clase que representa un vertice del grafo
	 *
	 */
	public class Node {
		/**
		 * Identificador del nodo, tipo int
		 */
		private int id;
		
		/**
		 * Lista de Node que representa los hermanos de un Node
		 */
		private List<Node> neighbors;
		
		/**
		 * Metodo constructor de un Node
		 * @param id - Identificador del Node
		 */
		public Node(int id) {
			this.id = id;
			this.neighbors = new ArrayList<Node>();
		}

		/**
		 * Metodo que agrega una conexion a un Node, el cual es el reflejo de una adyacencia
		 * @param e
		 */
		public void addNeighbor(Node e) {
			this.neighbors.add(e);
		}

		/**
		 * Retorna el id de un Node
		 * @return
		 */
		public int getId() {
			return id;
		}

		/**
		 * Retorna la lista de hermanos o Node adyacentes a otro Node.
		 * @return
		 */
		public List<Node> getNeighbors() {
			return neighbors;
		}

	}


	/**
	 * Metodo principal de ejecucion
	 * @param args: Arreglo de cadenas de caracteres asociado a los parámetros dados en la ejecucion
	 * @throws Exception: Si ocurre un error al leer el archivo de entrada
	 */
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