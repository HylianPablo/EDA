import java.util.*;
import java.io.*;

public class Proxy3{

	public static double memIni;
	public static Runtime rt;

	static byte[] subfich;
	static byte[] fichPrin;
	static int[] indURL;
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
		for(int i=0;i<subfich.length();i++){
			if(subfich[i]==10){n++;}  //10 en ascii separador de cadena
		}
		indURL=null;
		indURL=new int[n];
		int k=0;
		for(int i=0;i<subfich.length();i++){
			if(subfich[i]==10){indURL[k++]=i;}
		}
	}

	static void creaIndices1(){   //Crea array de urls
		int n=0;
		for(int i=0;i<fichPrin.length();i++){
			if(fichPrin[i]==10){n++;}  //10 en ascii separador de cadena
		}
		indURL1=null;
		indURL1=new int[n];
		int k=0;
		for(int i=0;i<fichPrin.length();i++){
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


			/*Scanner scan1 = new Scanner(new File(fichOK));

			FileInputStream fis1 = new FileInputStream(fichOK);
			DataInputStream in1 = new DataInputStream(fis1);

			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String strLine1;

			ArrayList <String> caja1 = new ArrayList <String>();
			while(scan1.hasNextLine()){
				strLine1 = br1.readLine();
				caja1.add(strLine1);
				scan1.nextLine();
			}*/
			
			for (int k=1;k<=1000;k++){
				String model = ("/home/pablo/EDA/fichEDA/"+ k + ".txt");
				//leeSubfichero(model);

				File fichBAD = new File(model);
				if(fichBAD.exists()){

					leeSubfichero(model);
					creaIndices();

					for(int i=0;i<indURL1.length();i++){ //Fichero a introducir
						String url1= accesoURL1(i);


						for(int j=0;j<indURL.length();j++){
							String url2=accesoURL(j);
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
			}
			double mem = usoMemoria(); 
			System.out.println("Uso de memoria: "+mem+" bytes."); 
			System.out.println("Fin del programa.");
		}catch(IOException e){
			System.out.println("Error");
		}
	}
}