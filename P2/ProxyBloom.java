import java.util.*;			
import java.io.*;
import java.nio.charset.*;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Collection;
import java.lang.*;

/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

public class ProxyBloom{

	//Variables globales

	public static int TAM_MAX=713000;
	public static byte[] subfich;
	public static int TAM_ACT;
	public static int[] indURL;
	public static int NUM_URL;
	public static long memIni;
	public static Runtime rt;


	//Funciones externas

	public static BitSet fromString(String binary) {
    	BitSet bitset = new BitSet(binary.length());
    	for (int i = 0; i < binary.length(); i++) {
        	if (binary.charAt(i) == '1') {
            	bitset.set(i);
        	}
    	}
    	return bitset;
	}

	public static void printBits(BitSet b) {
      for (int i = 0; i < b.size(); i++) {
         System.out.print(b.get(i) ? "1" : "0");
      }
      //System.out.println(b.size());
      System.out.println();
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

		int totArch=-1;						//Se lee el número de ficheros tipo XXX.txt
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

		FileReader fx = new FileReader(fichOK);		//Se cuentan las líneas del fichero a dividir
		LineNumberReader lnr = new LineNumberReader(fx);
		int countL=0;
		while(lnr.readLine ()!= null){
			countL ++;
		}
		lnr.close();

		BloomFilterCasero bloomFilter = new BloomFilterCasero(2);

		String bl="danger1.txt";
		File fbl=new File(bl);

		Scanner scanBl = new Scanner(new File(bl));
		FileInputStream fisBl = new FileInputStream(fbl);
		DataInputStream inBl = new DataInputStream(fisBl);
		BufferedReader brBl = new BufferedReader(new InputStreamReader(inBl));
		String strLineBl;

		for(int i=0;i<countL;i++){
				strLineBl = brBl.readLine();
				bloomFilter.add(strLineBl);
				scanBl.nextLine();
			}
		/*

		String fichBloom = "998.txt";
		File archBloom= new File(fichBloom);

		Scanner scanBloom = new Scanner(new File(fichBloom));
		FileInputStream fisBloom = new FileInputStream(archBloom);
		DataInputStream inBloom = new DataInputStream(fisBloom);
		BufferedReader brBloom = new BufferedReader(new InputStreamReader(inBloom));
		String urlBloom;

		urlBloom=brBloom.readLine();
		//System.out.println(urlBloom);
		BitSet dataBloom=fromString(urlBloom);

		BloomFilterCasero bloomFilter = new BloomFilterCasero(2);
		bloomFilter.setBitSet(dataBloom);
		//printBits(bloomFilter.getBitSet());

		//System.out.println(bloomFilter.contains("https://T6txzmdTaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaar_SjAf.com"));
		//System.out.println(bloomFilter.contains("http://KYiNQKblF2AeI.g0v/CI/9Yt_8tkW0gnt5o4"));	//A veces falla y da falsos positivos y negativos
		//System.out.println("flag");

		*/
		//#######################################################################################


		try(BufferedReader fich = new BufferedReader(new FileReader(fichOK),400)){	
			while(fich.ready()){
				String url1=fich.readLine();
				numURLProc++;
				if(url1!=null){
					
					int centro1, inf1=0, sup1=aL.size()-2;
					boolean encontrado1 = false;
					
					while(inf1<=sup1 & encontrado1 == false) {  //Para acceder a ficheros utilizamos el algoritmo de búsqueda binaria
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
							
							lnr2.close();
							numAccesos++;	//Podemos mantener la busqueda binaria en los subficheros, pero no en las urls


							//Metemos un for para cada uno de los elementos del fichero
							//Si es falso en el BF, pasa
							//Si es correcto, puede estar
							//Si puede estar, se hace compareTo


							int centro, inf=0, sup=countLF-1;
							boolean encontrado = false;
							while(inf<=sup & encontrado == false) {
								centro=(sup+inf)/2;
								String url2= accesoURL(centro);
								if(bloomFilter.contains(url1)==false){
									System.out.println("flag1");
									if(url1.compareTo(url2)<0){
										sup=centro-1;
									}else{
										inf=centro+1;
									}
								}else{
									System.out.println("flag2");
									if(url1.equals(url2)){

										encontrado = true;
										System.out.println("Detectada url dañina: "+url1);
										long finTime = System.currentTimeMillis();
										double tiempo =(double) (finTime - iniTime)/1000;
										System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
										memAct = usoMemoria();
										if(memAct>memMax){
											memMax=memAct;
										} 
										System.out.println(" ");
									}
								}//else if(url1.compareTo(url2)<0 ){
									//sup=centro-1;
								//}else {
								//	inf=centro+1;
								//}
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

class BloomFilterCasero<E>{

	private static BitSet data;
    private static int noHashes;
    private static int hashMask;

	public BloomFilterCasero(int noHashes) {	//Tamaño mal hecho, revisar
    	int bitsRequired = 712000*8;
    	int logBits = 4;
    	while ((1 << logBits) < bitsRequired)
      		logBits++;
    	this.data = new BitSet(1 << logBits);
    	this.noHashes = noHashes;
    	this.hashMask = (1 << logBits) - 1;
  	}

  	public void add(String s) {
    	for (int n = 0; n < noHashes; n++) {
      	int hc = hash(s);
      	int bitNo =  Math.abs(hc%(712000*8));// & this.hashMask;
      	data.set(bitNo);
    	}
  	}

  	public void setBitSet(BitSet b){
  		data=b;
  	}

  	public BitSet getBitSet(){
  		return data;
  }

  	public boolean contains(String s) {
    	for (int n = 0; n < noHashes; n++) {
     		int hc = hash(s);
      		int bitNo = Math.abs(hc%(712000*8));
      		if (!data.get(bitNo)) return false;
    	}
    	return true;
  	}

	public static int hash(String txt){
		int n = txt.length();
		int h = 0;
		for(int i = 0; i < n; i++){h=31*h+txt.charAt(i);}
		return h;
	}
}
