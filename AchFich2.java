//Permite escribir sobre ficheros, como bytes, utilizando FOS

//TO-DO Optimizar en que fichero va la url negativa
// ¿Y si justo está lleno el n-ésimo fichero?

import java.io.*;
import java.util.*;
import java.nio.charset.*;

public class AchFich2{

	static byte[] subfich;
	static int[] indURL;
	static String[]sol;


	static void leeSubfichero(String nomfich) throws IOException{ //Lee subficheros
		File fich = new File(nomfich);
		int tam = (int) fich.length();
		subfich = null;
		subfich  = new byte[tam];
		try(FileInputStream fis = new FileInputStream(fich)){
			fis.read(subfich);
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

	static String accesoURL(int i){ //Accede a posicion de arrays de url
		int a = i == 0 ? 0 : indURL[i-1] + 1;
		int b = indURL[i]-1;
		return new String (subfich,a,b-a+1,StandardCharsets.US_ASCII);
	}
	static void generate(){
		sol = new String[indURL.length];
		for(int i=0;i<indURL.length;i++){
			sol[i]=accesoURL(i);
		}
	}


	public static void main (String args[]){

	

		long tope = 1035000; //Tamaño provisional
		long topeL= tope/50; //Maximo de lineas que puede tener un fichero

		Scanner teclado = new Scanner(System.in);
		System.out.println("Introduzca una nueva URL maligna: ");
		String add =  teclado.nextLine();
		//add = add + "\n";
		//byte b [] = add.getBytes();



		try{

			int totArch=0;
			for(int q=1;q<=1000;q++){
				String fichArr = "/home/pablo/EDA/fichEDA/"+ q + ".txt";
				File archArr = new File(fichArr);
				if(archArr.exists()){
					totArch++;
				}else{
					break;
				}

			}

			ArrayList <String> aL = new ArrayList <String>();  //Arraylist de primer elemento de cada fichero
			for(int h =1;h<=totArch;h++){
				String fichArr = "/home/pablo/EDA/fichEDA/"+ h + ".txt";
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

					//File ult = new File("/home/pablo/EDA/fichEDA/"+(h+1)+".txt");
					//boolean b = ult.exists();
					if(h==totArch){
						FileReader fx0 = new FileReader(archArr);
						LineNumberReader lnr0 = new LineNumberReader(fx0);
						int countLU=0;
						while(lnr0.readLine ()!= null){
							countLU ++;
						}
						lnr0.close();

						String ult0 = "/home/pablo/EDA/fichEDA/"+96+".txt";
						leeSubfichero(ult0);
						creaIndices();
						generate();

						String ultima = sol[countLU-2];
						aL.add(ultima);
						System.out.println(ultima);
					
					}
				}
		
			}

			System.out.println(aL.get(aL.size()-1));
			int flag=0;
			for(int j=0;j<(aL.size())-1;j++){
				if((((add.compareTo(aL.get(j)))>=0) && ((add.compareTo(aL.get(j+1)))<0))){  //HAY QUE AÑADIR LA ULTIMA DIRECCION DEL FICHERO, SI ES MAYOR QUE LA ULTIMA, VA AL ULTIMO
					String fichero = "/home/pablo/EDA/fichEDA/"+ (j+1) + ".txt";
					File archivo = new File(fichero);
					
					FileWriter fw = new FileWriter(fichero); 
					BufferedWriter out = new BufferedWriter(fw);
					out.write(add);

					flag = j+1;
					out.close();
					break;
				}
				/*else if(add.compareTo(aL.get(aL.size()-1))>=0){
					String fichero = "/home/pablo/EDA/fichEDA/"+ (aL.size()) + ".txt";   //REVISAR, ESTO ESTÁ MAL
					File archivo = new File(fichero);

					FileWriter fw = new FileWriter(fichero); 
					BufferedWriter out = new BufferedWriter(fw);
					out.write(add);

					out.close();

				}*/

			}

			String fichero = "/home/pablo/EDA/fichEDA/"+ flag + ".txt";
			File archivo = new File(fichero);
			

			FileReader fx = new FileReader(archivo);
			LineNumberReader lnr = new LineNumberReader(fx);
			int countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();

			Scanner scan1 = new Scanner(new File(fichero));
			FileInputStream fis = new FileInputStream(archivo);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			String strLine;

			String caja1[] = new String[countL];
			for(int i=0;i<countL;i++){
				strLine = br1.readLine();
				caja1[i]=strLine;
				scan1.nextLine();
			}

			sort(caja1,0,caja1.length-1);

			FileWriter fw2 = new FileWriter("/home/pablo/EDA/fichEDA/flag.txt",false); 
			BufferedWriter out2 = new BufferedWriter(fw2);

			for(int w=0;w<countL;w++){
				strLine = br1.readLine();
				if(strLine != null){
					out2.write(strLine);
					out2.newLine();
				}
			}
			out2.close();


		}
		catch (IOException e){
			System.out.println("Error");
			e.printStackTrace();
		}
		
		
		//System.out.println("Proceso terminado.");
	}



	public static void merge(String [] caja1, int l, int m, int r){
		int n1 = m-l+1;
		int n2 = r-m;

		String L[] = new String[n1];
		String R[]= new String [n2];

		for(int i=0;i<n1;++i){
			L[i]=caja1[l+i];
		}
		for(int j=0;j<n2;++j){
			R[j]=caja1[m+1+j];
		}

		int i=0, j=0;

		int k=l;

		while(i<n1 && j <n2){
			if(L[i].compareTo(R[j])<=0){
				caja1[k]=L[i];
				i++;
			}
			else{
				caja1[k]=R[j];
				j++;
			}
			k++;
		}

		while(i<n1){
			caja1[k] = L[i];
			i++;
			k++;
		}
		while(j<n2){
			caja1[k]=R[j];
			j++;
			k++;
		}
	}

	public static void sort(String [] caja1, int l, int r){
		if(l<r){
			int m=(r+l)/2;

			sort(caja1,l,m);
			sort(caja1,m+1,r);

			merge(caja1,l,m,r);
		}
	}
}