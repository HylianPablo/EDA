import java.util.*;			//3s/fich y 2 mb
import java.io.*;
import java.nio.charset.*;

public class ProxyM{

	public static double memIni;
	public static Runtime rt;

	static byte[] subfich;
	static int[] indURL;

	static String[]sol;
	static String[]sol1;

	static byte[] fichPrin;
	static int[] indURL1;

	public static double usoMemoria(){
		rt.gc();
		return rt.totalMemory() - rt.freeMemory() - memIni;
	}

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.println("Introduzca el fichero a revisar:");
		String fichOK = in.nextLine();
		System.out.println(" ");

		comprobar(fichOK);
	}

	static void leeSubfichero(String nomfich) throws IOException{ //Lee subficheros
		File fich = new File(nomfich);
		int tam = (int) fich.length();
		subfich = null;
		subfich  = new byte[tam];
		try(FileInputStream fis = new FileInputStream(fich)){
			fis.read(subfich);
		}

	}

	static void leeSubfichero1(String nomfich) throws IOException{ //Lee subficheros
		File fich = new File(nomfich);
		int tam = (int) fich.length();
		fichPrin = null;
		fichPrin  = new byte[tam];
		try(FileInputStream fis = new FileInputStream(fich)){
			fis.read(fichPrin);
		}

	}

	static void creaIndices(){   //Crea array de urls
		int n=0;
		for(int i=0;i<subfich.length;i++){
			if(subfich[i]==10){n++;}  //10 en ascii separador de cadena
		}
		indURL=null;
		indURL=new int[n];
		int k=0;
		for(int i=0;i<subfich.length;i++){
			if(subfich[i]==10){indURL[k++]=i;}
		}
	}

	static void generate1(){
		sol1 = new String[indURL1.length];
		for(int i=0;i<indURL1.length;i++){
			sol1[i]=accesoURL1(i);
		}
	}

	static void creaIndices1(){   //Crea array de urls
		int n=0;
		for(int i=0;i<fichPrin.length;i++){
			if(fichPrin[i]==10){n++;}  //10 en ascii separador de cadena
		}
		indURL1=null;
		indURL1=new int[n];
		int k=0;
		for(int i=0;i<fichPrin.length;i++){
			if(fichPrin[i]==10){indURL1[k++]=i;}
		}
	}

	static String accesoURL(int i){ //Accede a posicion de arrays de url
		int a = i == 0 ? 0 : indURL[i-1] + 1;
		int b = indURL[i]-1;
		return new String (subfich,a,b-a+1,StandardCharsets.US_ASCII);
	}

	static String accesoURL1(int i){ //Accede a posicion de arrays de url
		int a = i == 0 ? 0 : indURL1[i-1] + 1;
		int b = indURL1[i]-1;
		return new String (fichPrin,a,b-a+1,StandardCharsets.US_ASCII);
	}
	
	public static void comprobar(String fichOK){
		try{

			rt = Runtime.getRuntime();				//Cambiar la comprobación de memoria
			rt.gc();
			memIni = rt.totalMemory() - rt.freeMemory(); //¿Por qué sale memoria negativa?
			long iniTime = System.currentTimeMillis();


			leeSubfichero1(fichOK);
			creaIndices1();
			generate1();
			
			for (int k=1;k<=1000;k++){
				String model = ("/home/pablo/EDA/fichEDA/"+ k + ".txt");
				File fichBAD = new File(model);
				if(fichBAD.exists()){
					leeSubfichero(model);
					creaIndices();
				
					for(int i=0;i<indURL1.length;i++){     //EN ESTE BUCLE TARDA MUCHO
						//System.out.println(indURL1.length);
						
						String url1= sol1[i];
					
						//System.out.println(url1);
						for(int j=0;j<indURL.length;j++){
							String url2= accesoURL(j);
							
							//System.out.println(url2);
							if(url1.equals(url2)){
								System.out.println("Detectada URL dañina: "+url1);
								long finTime = System.currentTimeMillis();
								double tiempo =(double) (finTime - iniTime)/1000;
								System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
								double mem = usoMemoria(); 
								System.out.println("Uso de memoria: "+mem+" bytes."); 
								System.out.println(" ");
								
								
							}
						}
					}
				}else{
					break;
			
				}
				System.out.println("Lectura del fichero terminada.");
				long finTime = System.currentTimeMillis();
				double tiempo =(double) (finTime - iniTime)/1000;
				System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
			}
			double mem = usoMemoria(); 
			System.out.println("Uso de memoria: "+mem+" bytes."); 
			System.out.println("Fin del programa.");
		}catch(IOException e){
			System.out.println("Error");
		}
	}
}