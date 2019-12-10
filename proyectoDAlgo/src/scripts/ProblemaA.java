package scripts;

// Se importan las clases de librería a utilizar
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Programa para calcular el tamaño del subarreglo recurrente más largo
 * @author Daniel Mateo Guatibonza Solano - Juan David Diaz Cristancho 
 */
public class ProblemaA 
{
	/**
	 * Método principal de ejecución
	 * @param args: Arreglo de cadenas de caracteres asociado a los parámetros dados en la ejecución
	 * @throws Exception: Si ocurre un error al leer el archivo de entrada
	 */
	public static void main(String[] args) throws Exception
	{
		ProblemaA instanciaA = new ProblemaA();
		try(InputStreamReader isr= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr)) 
		{ 
			String linea = br.readLine();	
			while(linea!=null && linea.length()>0 && !"0".equals(linea)) 
			{
				// Lectura del tamaño del arreglo de entrada
				int N = Integer.parseInt(linea);
				// Lectura del arreglo de entrada
				linea = br.readLine();
				String[] datosPuntos = linea.split(" ");
				int[] numeros = Arrays.stream(datosPuntos).mapToInt(f->Integer.parseInt(f)).toArray();
				// Cálculo del tamaño del subarreglo recurrente más largo y escritura en el archivo de salida
				System.out.println(instanciaA.subarregloRecurrenteMasLargo(N, numeros));
				linea = br.readLine();
			}
		}
	}

	/**
	 * Método para calcular el tamaño del subarreglo recurrente más largo
	 * @param N: Tamaño del arreglo de entrada
	 * @param numeros: Arreglo de números sobre el que se desea calcular el subarreglo recurrente más largo
	 * @return El tamaño del subarreglo recurrente más largo del arreglo numeros
	 */
	public int subarregloRecurrenteMasLargo(int N, int[] numeros)
	{
		// El algoritmo evalua la posibilidad de que un número sea el inicio de un subarreglo recurrente,
		// calcula el tamaño del subarreglo recurrente obtenido a partir de ese número y actualiza el subarreglo
		// más largo obtenido hasta ahora si es conveniente
		int[] SARML = new int[0];							// Variable usada para almacenar el SubArreglo Recurrente Más Largo
		boolean noExisteMasLargo = false;
		for(int i=0; i<N && !noExisteMasLargo; i++)
		{
			boolean noExisteMasLargoActual = false;
			for(int j=i+1; j<N && !noExisteMasLargoActual; j++)
			{
				if(numeros[i]==numeros[j])
				{
					int[] SARA = new int[0];				// Variable usada para almacenar el SubArreglo Recurrente Actual
					boolean sigueIgual = true;
					int k = 0;
					for(; k<N-j && sigueIgual; k++)
					{
						if(numeros[i+k] != numeros[j+k])
						{
							sigueIgual = false;
							k--;
						}
					}
					SARA = Arrays.copyOfRange(numeros, j, j+k);
					if(SARA.length > SARML.length)
					{
						SARML = SARA;
					}
				}
				if(SARML.length >= N-j-1)
				{
					noExisteMasLargoActual = true;
				}
			}
			if(SARML.length >= N-i-1)
			{
				noExisteMasLargo = true;
			}
		}
		return SARML.length;
	}
}
