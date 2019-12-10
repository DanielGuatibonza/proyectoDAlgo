package scripts;

//Se importan las clases de librería a utilizar
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Programa para determinar si un punto está afuera, dentro o en la frontera de un polígono
 * @author Daniel Mateo Guatibonza Solano - Juan David Diaz Cristancho 
 */
public class ProblemaC 
{
	/**
	 * Método principal de ejecución
	 * @param args: Arreglo de cadenas de caracteres asociado a los parámetros dados en la ejecución
	 * @throws Exception: Si ocurre un error al leer el archivo de entrada
	 */
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
				// Determinación de la ubicación del punto y escritura en el archivo de salida
				System.out.println(instanciaC.dentro_o_fuera(poligono, puntoInteres));
				linea = br.readLine();
			}
		}
	}
	
	/**
	 * Método para calcular si un punto está dentro, fuera o en la frontera de un polígono
	 * @param poligono: Poligono sobre el que se desea identificar el punto
	 * @param puntoInteres: Punto sobre el que se quiere determinar su ubicación en el polígono
	 * @return 0 si el punto está en la frontera, 1 si está adentro, -1 si esta fuera
	 */
	public int dentro_o_fuera(Poligono poligono, Punto puntoInteres)
	{
		int respuesta = -2;
		// Se revisa si el punto esta en la frontera del polígono
		for(int i=0; i<poligono.lados.length && respuesta == -2; i++)
		{
			int yMayor = Math.max(poligono.lados[i].punto1.y, poligono.lados[i].punto2.y);
			int yMenor = Math.min(poligono.lados[i].punto1.y, poligono.lados[i].punto2.y);
			// Caso en el que el lado del poligono es vertical
			if(poligono.lados[i].xConstante)
			{
				if(poligono.lados[i].punto1.x == puntoInteres.x && yMenor<=puntoInteres.y && puntoInteres.y<=yMayor)
				{
					respuesta = 0;
				}
			}
			// Caso en el que el lado del polígono es horizontal
			else if(poligono.lados[i].yConstante)
			{
				int xMayor = Math.max(poligono.lados[i].punto1.x, poligono.lados[i].punto2.x);
				int xMenor = Math.min(poligono.lados[i].punto1.x, poligono.lados[i].punto2.x);	
				if(poligono.lados[i].punto1.y == puntoInteres.y && xMenor<=puntoInteres.x && puntoInteres.x<=xMayor)
				{
					respuesta = 0;
				}
			}
			// Caso en el que el lado del polígono tiene pendiente distinta de 0
			else
			{
				float yEstimado = poligono.lados[i].darY(puntoInteres.x);
				if(yEstimado == puntoInteres.y)
				{
					if(yMenor<=yEstimado && yEstimado<=yMayor)
					{
						respuesta = 0;
					}
				}
			}
		}
		// Si el punto no está en la frontera, se evalua si está adentro o afuera
		if(respuesta == -2)
		{
			// Se identifican las lineas del poligono que están a la derecha del punto de interés
			boolean[] estanDerecha = new boolean[poligono.lados.length];
			for(int i=0; i<estanDerecha.length; i++)
			{
				if(poligono.lados[i].punto1.x > puntoInteres.x || poligono.lados[i].punto2.x > puntoInteres.x)
				{
					estanDerecha[i] = true;
				}
			}
			// Se evalua el número de cortes de la proyección horizontal del punto de interés sobre los lados
			// del polígono que están a la derecha
			int cruces = 0; int limite = estanDerecha.length;
			for(int i=0; i<limite; i++)
			{
				// Se evaluan solo las lineas a la derecha que no son horizontales
				if(estanDerecha[i] && !poligono.lados[i].yConstante)
				{
					int xMayor = Math.max(poligono.lados[i].punto1.x, poligono.lados[i].punto2.x);
					int xMenor = Math.min(poligono.lados[i].punto1.x, poligono.lados[i].punto2.x);			
					float xEstimado = poligono.lados[i].darX(puntoInteres.y);
					// Caso en el que la proyección del punto se encuentra con el primer vértice agregado
					if(xEstimado == poligono.lados[i].punto1.x && i==0)
					{
						if((poligono.lados[limite-1].punto1.y > puntoInteres.y && poligono.lados[0].punto2.y < puntoInteres.y) || 
								(poligono.lados[limite-1].punto1.y < puntoInteres.y && poligono.lados[0].punto2.y > puntoInteres.y))
						{
							cruces++;
						}
						limite--;
					}
					// Caso en el que la proyección del punto se encuentra con un vértice distinto al primero
					else if(xEstimado == poligono.lados[i].punto2.x)
					{
						// Se evalua si al cruzar por el vértice la proyección cruza o no el polígono
						if(i!=estanDerecha.length-1)
						{
							if((poligono.lados[i].punto1.y > puntoInteres.y && poligono.lados[i+1].punto2.y < puntoInteres.y) || 
									(poligono.lados[i].punto1.y < puntoInteres.y && poligono.lados[i+1].punto2.y > puntoInteres.y))
							{
								cruces++;
							}
							i++;
						}
						else
						{
							if((poligono.lados[i].punto1.y > puntoInteres.y && poligono.lados[0].punto2.y < puntoInteres.y) || 
									(poligono.lados[i].punto1.y < puntoInteres.y && poligono.lados[0].punto2.y > puntoInteres.y))
							{
								cruces++;
							}
						}
					}
					// Caso en el que la proyección del punto se encuentra con un lado del polígono
					else if(xEstimado > puntoInteres.x && xMenor<=xEstimado && xEstimado<=xMayor)
					{
						cruces++;
					}
				}
			}
			// Si el número de cortes es par, el punto está fuera del polígono
			if(cruces%2 == 0)
			{
				respuesta = -1;
			}
			// Si el número de cortes es impar, el punto está dentro del polígono
			else
			{
				respuesta = 1;
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
	public float pendiente;
	public float intercepto;
	public boolean xConstante;
	public boolean yConstante;
	
	// Método contructor
	public Linea(Punto p1, Punto p2)
	{
		punto1 = p1;
		punto2 = p2;
		if(p1.x == p2.x)
		{
			xConstante = true;
		}
		else if(p1.y == p2.y)
		{
			yConstante = true;
		}
		if(!xConstante && !yConstante)
		{
			pendiente = ((float)p1.y - (float)p2.y)/((float)p1.x - (float)p2.x);
			intercepto = p1.y - pendiente*p1.x;
		}
	}
	
	public float darY(int x)
	{
		float resultado = -1;
		if(yConstante)
		{
			resultado = punto1.y;
		}
		else if(!xConstante)
		{
			resultado = pendiente*x + intercepto;
		}
		return resultado;
	}
	
	public float darX(int y)
	{
		float resultado = -1;
		if(xConstante)
		{
			resultado = punto1.x;
		}
		else if(!yConstante)
		{
			resultado = (y-intercepto)/pendiente;
		}
		return resultado;
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
