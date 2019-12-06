package scripts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Programa para determinar si un punto está afuera, dentro o en la frontera de un polígono
 * @author Daniel Mateo Guatibonza Solano - Juan David Diaz Cristancho 
 */
public class ProblemaC 
{

	public static void main(String[] args) throws Exception
	{
		ProblemaC instanciaC = new ProblemaC();
		try(InputStreamReader isr= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr)) 
		{ 
			String linea = br.readLine();	
			while(linea!=null && linea.length()>0 && !"0 0 0 0".equals(linea)) 
			{
				// Lectura de parámetros del problema actual
				String[] datosParametros = linea.split(" ");
				int[] parametros = Arrays.stream(datosParametros).mapToInt(f->Integer.parseInt(f)).toArray();
				Punto puntoInteres = new Punto(parametros[2],parametros[3]);
				// Lectura de puntos del poligono
				Poligono poligono = new Poligono(parametros[1]);
				linea = br.readLine();
				String[] datosPuntos = linea.split(" ");
				int[] puntos = Arrays.stream(datosPuntos).mapToInt(f->Integer.parseInt(f)).toArray();
				for(int i=0; i<puntos.length; i=i+2)
				{
					poligono.insertarPunto(puntos[i], puntos[i+1]);
				}
				System.out.println(instanciaC.dentro_o_fuera(poligono, puntoInteres));
			}
		}
	}
	
	public int dentro_o_fuera(Poligono poligono, Punto puntoInteres)
	{
		int respuesta = -2;
		// Se revisa si el punto esta en la frontera del polígono
		for(int i=0; i<poligono.lados.length && respuesta == -2; i++)
		{
			Punto yMayor = null;
			Punto yMenor = null;
			if(poligono.lados[i].punto1.y > poligono.lados[i].punto2.y)
			{
				yMayor = poligono.lados[i].punto1;
				yMenor = poligono.lados[i].punto2;
			}
			else
			{
				yMenor = poligono.lados[i].punto1;
				yMayor = poligono.lados[i].punto2;
			}
			double yEstimado = poligono.lados[i].pendiente * puntoInteres.x + poligono.lados[i].intercepto;
			if(yEstimado == puntoInteres.y)
			{
				
			}
		}
		// Si el punto no está en la frontera, se evalua si está adentro o afuera
		if(respuesta == -2)
		{
			// Se revisan las lineas del poligono que están a la derecha del punto de interés
			boolean[] estanDerecha = new boolean[poligono.lados.length];
			for(int i=0; i<estanDerecha.length; i++)
			{
				if(poligono.lados[i].punto1.x > puntoInteres.x || poligono.lados[i].punto2.x > puntoInteres.x)
				{
					estanDerecha[i] = true;
				}
			}
			// Sobre las lineas a la derecha del punto de interés, se revisa si la proyección horizontal
			// del punto cruza con algún vértice
			boolean[] cruzaPorPunto = new boolean[estanDerecha.length];
			for(int i=0; i<estanDerecha.length; i++)
			{
				if((i!= 0 && estanDerecha[i] && estanDerecha[i-1]) || (i==0 && estanDerecha[0] && estanDerecha[estanDerecha.length-1]))
				{
					if(poligono.vertices[i].x == puntoInteres.x)
					{
						cruzaPorPunto[i] = true;
					}
				}
				
			}
		}
		return respuesta;
	}

}

class Punto
{
	// Atributos
	public int x;
	public int y;
	
	// Método contructor
	public Punto(int pX, int pY)
	{
		x = pX;
		y = pY;
	}
}

class Linea
{
	// Atributos
	public Punto punto1;
	public Punto punto2;
	public double pendiente;
	public double intercepto;
	
	// Método contructor
	public Linea(Punto p1, Punto p2)
	{
		punto1 = p1;
		punto2 = p2;
		pendiente = (p1.y - p2.y)/(p1.x - p2.x);
		intercepto = p1.y - pendiente*p1.x;
	}
}

class Poligono
{
	// Atributos
	public Punto[] vertices;
	public Linea[] lados;
	private int actual;
	
	// Método contructor
	public Poligono(int N)
	{
		vertices = new Punto[N];
		lados = new Linea[N];
		actual = 0;
	}
	
	// Método para insertar un nuevo punto al poligono
	public void insertarPunto(int pX, int pY)
	{
		vertices[actual] = new Punto(pX,pY);
		if(actual != 0)
		{
			lados[actual-1] = new Linea(vertices[actual-1],vertices[actual]);
		}
		if(vertices[vertices.length-1] != null)
		{
			lados[lados.length-1] = new Linea(vertices[vertices.length-1],vertices[0]);
		}
		actual++;
	}
}
