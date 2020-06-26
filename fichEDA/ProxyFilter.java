import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ProxyFilter {
	// 			-- VARIABLES GLOBALES --
	public static int TAM_MAX =600000; // Tamano maximo de los subficheros(bytes)
	public static byte[] subfich; // datos del subfichero(var. global)
	public static int TAM_ACT; // Tamaño subfichero actual
	public static int[] indURL;//Indices de las URLs en "subfich"
	public static int NUM_URL; // Numero Urls en "subfich"
	public static Runtime rt;
	public static long memIni;

	// 			-- FUNCIONES GLOBALES --
	public static void initGesMem(){
		rt = Runtime.getRuntime();
		rt.gc();
		memIni = rt.totalMemory()-rt.freeMemory();

		// El array de bytes se crea con tamaño finito
		//ocupando su tamaño hasta TAM_ACT
		subfich = new byte[TAM_MAX];
		TAM_ACT = 0;
	}

	public static long usoMemoria(){
		rt.gc();
		return rt.totalMemory()- rt.freeMemory()- memIni;
	}

	public static boolean leeSubfichero(String nomfich) throws IOException{
		File fich = new File(nomfich);
		if(!fich.exists()){return false;}
		try( FileInputStream fis = new FileInputStream(fich)){
			TAM_ACT =  fis.read(subfich); //Lectura de todo el fichero
			rt.gc();
		}
		return true;
	}
	public static void creaIndices (){
	//1.ContarelnumerodeURLs
		NUM_URL = 0;
		for(int i = 0; i < TAM_ACT; i++){
			if(subfich[i] == 10){NUM_URL++;}
		}
	//2.Almacenar posiciónde separadores 
		if(indURL == null || indURL.length<NUM_URL){
		indURL = null;
		indURL = new int[NUM_URL];
		}
		int k = 0;
		for(int i = 0; i < TAM_ACT; i++){
			if(subfich[i] == 10){ indURL[k++] = i; }
		}
	}

	static String accesoURL (int i){
		int a = i == 0 ? 0:indURL[i-1] + 1;
		int b=indURL[i] - 1;
		return new String(subfich,a,b-a+1,StandardCharsets.US_ASCII);
	}

	// contador del numero de operacion entre URLs
	public static long numOper = 0;
	/**
	*
	*
	**/
	public static boolean comprobarIndice(String url, String indice){
		return false;
	}
	public static void main(String args) throws IOException{
		initGesMem(); // Inicializar gestion de memoria
		int numURLProc = 0; // numero de URls procesadas
		long numAccesos = 0; // Numero total de accesos a subficheros
		long memMax = 0, memAct = 0; // Memoria usada
		/**
		*
		*
		**/
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Fichero de simulacion: ");
		String nomfich = br.readLine();

		
		// Procesar URLs una a una
		try (BufferedReader fich = new BufferedReader(new FileReader(nomfich),400)){
			while(fich.ready()){
				String url = fich.readLine();
				numURLProc++;
				/**
				* Algoritmo
				*
				**/
				// .. debeis comprobar la memoria e incrementar el contador de llamadas:
				
				numAccesos++;
				memAct = usoMemoria();
				if (memAct>memMax){memMax = memAct;}
				
			}
		}
		System.out.printf("Accesos a subficheros: %d%n", numAccesos);
		System.out.printf("Accesos por URL: %.2f%n",(1.0 * numAccesos) / numURLProc);
		System.out.printf("Comparaciones por URL: %,.1f%n",(1.0*numOper)/numURLProc);
		System.out.printf("Maximo uso de memoria: %,dbytes%n",memMax);
	}
}