//TERMINADO CASI
//AÑADIR LO DE HUGO, REDUCIR EL TOPE, QUITAR COSAS QUE NO SE USEN Y PONERLO COMO CVACA

import java.util.*;			
import java.io.*;
import java.nio.charset.*;

/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

public class HHugo {

	public static int TAM_MAX=806000;
	public static byte[] subfich;
	public static int TAM_ACT;
	public static int[] indURL;
	public static int NUM_URL;
	public static long memIni;
	public static Runtime rt;

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

		BufferedReader brcv = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Fichero de simulacion: ");
		String fichOK = brcv.readLine();
		System.out.println(" ");
			
		initGesMem();
		//long iniTime = System.currentTimeMillis();

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
					
					String ultima = accesoURL(countLU-2);
					aL.add(ultima);
					
				}
			}else{
				break;
			}
		}

		long memMax =0;
		long memAct=0;
		double tiempoT= 0.0;
		int numAccesos=0;
		int numURLProc=0;

		
		String url1;

		try(BufferedReader fich = new BufferedReader(new FileReader(fichOK),400)){	//TRY
			while(fich.ready()){
			//for(int i =0; i<grande.length();i++){
				url1=fich.readLine();
				numURLProc++;
				if(url1!=null){
					
					int centro1, inf1=0, sup1=aL.size()-1;
					boolean encontrado1 = false;
					
					while(inf1<sup1 & encontrado1 == false) {
						centro1=(sup1+inf1)/2;
						String nombre3 = String.format("%03d",centro1);
						numOper++;
						int countLF = 0;
						if(((url1.compareTo(aL.get(centro1)))>=0) && ((url1.compareTo(aL.get(centro1+1)))<0)) {
							encontrado1= true;
							String fichero = (nombre3) + ".txt";
							File archivo = new File(fichero);
							leeSubfichero(fichero);
							creaIndices();

							FileReader fx2 = new FileReader(archivo);
							LineNumberReader lnr2 = new LineNumberReader(fx2);
							countLF=0;
							while(lnr2.readLine ()!= null){
								countLF ++;
							}
							if (countLF>16001){ //Mirar si sobra
								countLF=16001;
							}
							lnr2.close();
							numAccesos++;

							int centro, inf=0, sup=countLF-1;
							boolean encontrado = false;
							while(inf<=sup & encontrado == false) {
								centro=(sup+inf)/2;
								String url2= accesoURL(centro);
								if(url1.equals(url2)) {
									encontrado = true;
									System.out.println("Detectada url dañina: "+url1);
									memAct = usoMemoria();
									if(memAct>memMax){
										memMax=memAct;

									} 
									System.out.println(" ");
								}else if(url1.compareTo(url2)<0 ){
									sup=centro-1;
								}else {
									inf=centro+1;
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
			//in.close();

			System.out.printf("Accesos a subficheros: %d%n",numAccesos);
			System.out.printf("Accesos por URL: %.2f%n",numAccesos*1.0/numURLProc);
			System.out.printf("Comparaciones por URL: %,.1f%n", (1.0*numOper)/numURLProc);
			System.out.printf("Máximo uso de memoria: %,d bytes%n",memMax);
		}
	}
	
	
}