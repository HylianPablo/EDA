//TERMINADO CASI
//AÑADIR LO DE HUGO, REDUCIR EL TOPE, QUITAR COSAS QUE NO SE USEN Y PONERLO COMO CVACA

import java.util.*;			
import java.io.*;
import java.nio.charset.*;

/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

public class ProxyDEF{

	public static int TAM_MAX=840000;
	public static byte[] subfich;
	public static int TAM_ACT;
	public static int[] indURL;
	public static int NUM_URL;
	public static double memIni;
	public static Runtime rt;

	public static void initGesMem(){
		rt=Runtime.getRuntime();
		rt.gc();
		memIni=rt.totalMemory()-rt.freeMemory();
		subfich= new byte[TAM_MAX];
		TAM_ACT=0;
	}
	
	public static double usoMemoria(){
		rt.gc();
		return rt.totalMemory() - rt.freeMemory() - memIni;
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

	/*public static boolean leeSubfichero(String nomfich)throws IOException{
		File fich = new File(nomfich);
		if(!fich.exists()){return false;}
		try(FileInputStream fis = new FileInputStream(fich)){
			TAM_ACT=fis.read(subfich);
			rt.gc();
		}
		return true;
	}*/

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

	static String accesoURL(int i){ //Accede a posicion de arrays de url
		int a = i == 0 ? 0 : indURL[i-1] + 1;
		int b = indURL[i]-1;
		return new String (subfich,a,b-a+1,StandardCharsets.US_ASCII);
	}


	public static void main(String args[]){
	Scanner in = new Scanner(System.in);
	System.out.println("Introduzca el fichero a revisar:");
	String fichOK = in.nextLine();
	System.out.println(" ");

	comprobar(fichOK);
	}
	
	public static void comprobar(String fichOK){
		try{

			rt = Runtime.getRuntime();				//Cambiar la comprobación de memoria
			rt.gc();
			memIni = rt.totalMemory() - rt.freeMemory(); //¿Por qué sale memoria negativa?
			long iniTime = System.currentTimeMillis();

			int totArch=-1;
			for(int q=0;q<=1000;q++){
				String nombre1 = String.format("%03d",q);
				String fichArr = nombre1 + ".txt";
				File archArr = new File(fichArr);
				if(archArr.exists()){
					totArch++;
				}else{
					break;
				}

			}


			File grande = new File(fichOK);

			ArrayList <String> aL = new ArrayList <String>();  //Arraylist de primer elemento de cada fichero
			for(int h =0;h<=totArch;h++){
				String nombre2 = String.format("%03d",h);
				String fichArr = nombre2 + ".txt";
				File archArr = new File(fichArr);
				if(archArr.exists()){
					Scanner scan0 = new Scanner(new File(fichArr));
					FileInputStream fis0 = new FileInputStream(archArr);
					DataInputStream in0 = new DataInputStream(fis0);
					BufferedReader br0 = new BufferedReader(new InputStreamReader(in0));
					String url0;

					url0 = br0.readLine();
					aL.add(url0);
					br0.close();

					if(h==(totArch)){
						FileReader fx0 = new FileReader(archArr);
						LineNumberReader lnr0 = new LineNumberReader(fx0);
						int countLU=0;
						while(lnr0.readLine ()!= null){
							countLU ++;
						}
						lnr0.close();


						String ult0 = (nombre2)+".txt";
						leeSubfichero(ult0);
						creaIndices();
						//generate();

						String ultima = accesoURL(countLU-1);
						aL.add(ultima);
					
					}
				}else{
					break;
				}
			}

			double maxMem =0.0;
			double tiempoT= 0.0;
			int numAccesos=0;
			int numURL=0;

			Scanner scan1 = new Scanner(new File(fichOK));
			FileInputStream fis = new FileInputStream(grande);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			String url1;

			for(int i =0; i<grande.length();i++){
				url1=br1.readLine();
				numURL++;
				if(url1!=null){	
					for(int j=0;j<(aL.size())-1;j++){
						String nombre3 = String.format("%03d",j);
						if(((url1.compareTo(aL.get(j)))>=0) && ((url1.compareTo(aL.get(j+1)))<0)){
							String fichero = (nombre3) + ".txt";
							File archivo = new File(fichero);
							leeSubfichero(fichero);
							creaIndices();

							FileReader fx2 = new FileReader(archivo);
							LineNumberReader lnr2 = new LineNumberReader(fx2);
							int countLF=0;
							while(lnr2.readLine ()!= null){
								countLF ++;
							}
							lnr2.close();
							numAccesos++;

							for(int k=0;k<countLF-1;k++){
								String url2= accesoURL(k);
								if(url1.equals(url2)){
									System.out.println("Detectada url dañina: "+url1);
									long finTime = System.currentTimeMillis();
									double tiempo =(double) (finTime - iniTime)/1000;
									tiempoT+=tiempo;
									System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
									double mem = usoMemoria(); 
									System.out.println("Uso de memoria: "+mem+" bytes.");
									if(mem>maxMem){
										maxMem=mem;

									} 
									System.out.println(" ");
								}
							}
						}
					}
				}
			}
			in.close();

			System.out.printf("Accesos a subficheros: %d%n",numAccesos);
			System.out.printf("Accesos por URL: %.2f%n",numAccesos*1.0/numURL);
			System.out.println("Máximo uso de memoria: "+maxMem);
		}catch(IOException e){
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}