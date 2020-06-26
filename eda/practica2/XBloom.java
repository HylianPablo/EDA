/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/


package eda.practica2;

import java.util.*;			
import java.io.*;
import java.nio.charset.*;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Collection;
import java.lang.*;

public class XBloom{

	//Variables globales

	public static int TAM_MAX=712100;
	public static byte[] subfich;
	public static int TAM_ACT;
	public static int[] indURL;
	public static int NUM_URL;
	public static long memIni;
	public static Runtime rt;

	public static byte contenido[];


	//Funciones externas

	public static BitSet fromByteArray(byte[] bytes) {
    	BitSet bits = new BitSet();
    	for (int i = 0; i < bytes.length * 8; i++) {
      		if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
        		bits.set(i);
      		}
    	}
    	return bits;
    }

	public static void initGesMem(){
		rt=Runtime.getRuntime();
		rt.gc();
		memIni=rt.totalMemory()-rt.freeMemory();
		subfich= new byte[TAM_MAX];
		TAM_ACT=0;
	}
	
	public static long usoMemoria(){
		rt.gc();
		return rt.totalMemory() - rt.freeMemory() - memIni;
	}

	public static boolean leeSubfichero(String nomfich)throws IOException{
		File fich = new File(nomfich);
		if(!fich.exists()){return false;}
		try(FileInputStream fis = new FileInputStream(fich)){
			TAM_ACT=fis.read(subfich);
			rt.gc();
		}
		return true;
	}

	public static void creaIndices(){   //Crea array de urls
		NUM_URL=0;
		for(int i=0;i<TAM_ACT;i++){
			if(subfich[i]==10){NUM_URL++;}  //10 en ascii separador de cadena
		}
		if(indURL==null || indURL.length < NUM_URL){
			indURL=null;
			indURL= new int[NUM_URL];
		}
		int k=0;
		for(int i=0;i<TAM_ACT;i++){
			if(subfich[i]==10){indURL[k++]=i;}
		}
		
	}

	public static String accesoURL(int i){ //Accede a posicion de arrays de url
		int a = i == 0 ? 0 : indURL[i-1] + 1;
		int b = indURL[i]-1;
		return new String (subfich,a,b-a+1,StandardCharsets.US_ASCII);
	}

	public static long numOper = 0;
	
	public static void main (String args[]) throws IOException{

		BufferedReader brcv = new BufferedReader(new InputStreamReader(System.in));		//Lectura del fichero
		System.out.print("Fichero de simulacion: ");
		String fichOK = brcv.readLine();
		System.out.println(" ");
			
		initGesMem();

		long memMax =0, memAct=0;
		int numAccesos=0;
		int numURLProc=0;

		long iniTime = System.currentTimeMillis();

		int totArch=-1;			//Se lee el número de ficheros tipo XXX.txt
		for(int q=0;q<=997;q++){
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

		//####################     Carga el catálogo ######################

		ArrayList <String> aL = new ArrayList <String>();  //Arraylist de primer elemento de cada fichero y el último elemento de el último fichero.

		String fichCat = "999.txt";
		File archCat = new File(fichCat);
		
		FileReader fx0 = new FileReader(archCat);
		LineNumberReader lnr0 = new LineNumberReader(fx0);
		int countCat=0;
		while(lnr0.readLine ()!= null){
			countCat ++;
		}
		lnr0.close();

		Scanner scan0 = new Scanner(new File(fichCat));
		FileInputStream fis0 = new FileInputStream(archCat);
		DataInputStream in0 = new DataInputStream(fis0);
		BufferedReader br0 = new BufferedReader(new InputStreamReader(in0));
		String url0;

		for(int h=0;h<countCat;h++){
			url0 = br0.readLine();
			aL.add(url0);
			scan0.nextLine();
		}

		br0.close();

		//########################	Carga el BloomFilter	######################################

		String fileName = "998.txt";
		XBloomFilter bloomFilter = new XBloomFilter(2);
		bloomFilter.setData(subfich);

		//#######################################################################################


		try(BufferedReader fich = new BufferedReader(new FileReader(fichOK),400)){
			boolean flagBloomRead=false;
			leeSubfichero(fileName);	
			while(fich.ready()){
				String url1=fich.readLine();
				numURLProc++;
				if(flagBloomRead){
					leeSubfichero(fileName);
					flagBloomRead=false;
				}
				if(url1!=null&&bloomFilter.contains(url1)){ //Se comprueba en este punto si puede estar
					flagBloomRead=true;
					
					int centro1, inf1=0, sup1=aL.size()-2;
					boolean encontrado1 = false;
					
					while(inf1<=sup1 & encontrado1 == false) {  //Para acceder a ficheros utilizamos el algoritmo de búsqueda binaria
						centro1=(sup1+inf1)/2;
						String nombre3 = String.format("%03d",centro1);
						
						int countLF = 0;
						if( (((url1.compareTo(aL.get(centro1)))>=0) && ((url1.compareTo(aL.get(centro1+1)))<0)) || url1.compareTo(aL.get(aL.size()-1))==0 ) {
							encontrado1= true;
							String fichero = (nombre3) + ".txt";
							File archivo = new File(fichero);
							leeSubfichero(fichero);
							creaIndices();
							numAccesos++;

							FileReader fx2 = new FileReader(archivo);
							LineNumberReader lnr2 = new LineNumberReader(fx2);
							countLF=0;
							while(lnr2.readLine ()!= null){
								countLF ++;
							}
							
							lnr2.close();
							boolean flagURL=true;

							for(int w=0;w<countLF;w++){
								
								numOper++;
								String url2 = accesoURL(w);
								if(flagURL&&url1.compareTo(url2)==0){
									flagURL=false;
									System.out.println("Detectada url dañina: "+url1);
									long finTime = System.currentTimeMillis();
									double tiempo =(double) (finTime - iniTime)/1000;
									System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
									memAct = usoMemoria();
									if(memAct>memMax){
										memMax=memAct;
									} 
									System.out.println(" ");
									w=countLF;
								}
								if(!flagURL){
									w=countLF;
								}
							}

						}else if(url1.compareTo(aL.get(centro1))<0){
							sup1=centro1-1;
						}else {
							inf1=centro1+1;
						}
					}
				}
			}
			long finTime = System.currentTimeMillis();
			double tiempo =(double) (finTime - iniTime)/1000;
			System.out.println("Tiempo total transcurrido: "+tiempo+" segundos.");

			System.out.printf("Accesos a subficheros: %d%n",numAccesos);
			System.out.printf("Accesos por URL: %.2f%n",numAccesos*1.0/numURLProc);
			System.out.printf("Comparaciones por URL: %,.1f%n", (1.0*numOper)/numURLProc);
			System.out.printf("Máximo uso de memoria: %,d bytes%n",memMax);
		}
	}
}