import java.util.*;			//7 segundos por fichero y ocupa 0.9 mb en memoria
import java.io.*;
import java.nio.charset.*;

public class ProxyV1{

	public static double memIni;
	public static Runtime rt;

	static byte[] subfich;
	static int[] indURL;

	static String[]sol;

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
	static void generate(){
		sol = new String[indURL.length];
		for(int i=0;i<indURL.length;i++){
			sol[i]=accesoURL(i);
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

			File grande = new File(fichOK);


			//leeSubfichero1(fichOK);
			//creaIndices1();
			

			ArrayList <String> aL = new ArrayList <String>();  //Arraylist de primer elemento de cada fichero
			for(int h =1;h<=1000;h++){
				String fichArr = "/home/pablo/EDA/fichEDA/"+ h + ".txt";
				File archArr = new File(fichArr);
				if(archArr.exists()){
					Scanner scan0 = new Scanner(new File(fichArr));
					FileInputStream fis0 = new FileInputStream(archArr);
					DataInputStream in0 = new DataInputStream(fis0);
					BufferedReader br0 = new BufferedReader(new InputStreamReader(in0));
					String url0;

					url0 = br0.readLine();
					aR.add(url0);
				}else{
					break;
				}
			}

			Scanner scan1 = new Scanner(new File(fichOK));
			FileInputStream fis = new FileInputStream(grande);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			String url1;

			for(int i =0; i<grande.length();i++){

				url1=br1.readLine();
				scan1.nextLine();
				System.out.println(url1);
				for(int j =1; j<=1000;j++){
					String fichero = "/home/pablo/EDA/fichEDA/"+ j + ".txt";
					File archivo = new File(fichero);
					if(archivo.exists()){
						leeSubfichero(fichero);
						creaIndices();
						generate();
						//System.out.println("2");
						//Luego comprobar usando generate()
						FileReader fx = new FileReader(archivo);
						LineNumberReader lnr = new LineNumberReader(fx);
						int countF=0;
						while(lnr.readLine ()!= null){
							countF ++;
						}
						lnr.close();
						//System.out.println(sol[0]);
						//System.out.println(sol[sol.length-1]);
						if(((url1.compareTo(sol[0]))>=0) && ((url1.compareTo(sol[sol.length-1]))<=0)){
							//System.out.println("3");
							for(int k=0;k<fichero.length();k++){
								String url2= accesoURL(k);
								if(url1.equals(url2)){
									System.out.println("Detectada url dañina: "+url1);
									long finTime = System.currentTimeMillis();
									double tiempo =(double) (finTime - iniTime)/1000;
									System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
									double mem = usoMemoria(); 
									System.out.println("Uso de memoria: "+mem+" bytes."); 
									System.out.println(" ");
									break;
								}
							}
						}

					}else{
						break;
					}
				}
			}
			
			/*for (int k=1;k<=1000;k++){
				String model = ("/home/pablo/EDA/fichEDA/"+ k + ".txt");
				File fichBAD = new File(model);
				if(fichBAD.exists()){
					leeSubfichero(model);
					creaIndices();
					generate();
				
					for(int i=0;i<indURL1.length;i++){     
						String url1= accesoURL1(i);
						for(int j=0;j<indURL.length;j++){
							String url2= sol[j];
							if(url1.equals(url2)){
								System.out.println("Detectada URL dañina: "+url1);
								long finTime = System.currentTimeMillis();
								double tiempo =(double) (finTime - iniTime)/1000;
								System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
								double mem = usoMemoria(); 
								System.out.println("Uso de memoria: "+mem+" bytes."); 
								System.out.println(" ");
								break;
								
								
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
			System.out.println("Fin del programa.");*/
		}catch(IOException e){
			System.out.println("Error");
		}
	}
}