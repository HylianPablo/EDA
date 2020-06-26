
/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

//package aaaaaaaaaa;

import java.io.*;
import java.util.*;
import java.lang.*;
//import eda.practica2.XBloomFilter;

public class GenFich{

	public static int TAM_MAX = 712000; 

	public static void main(String args[]){
		
		try{
			Scanner teclado = new Scanner (System.in);
			System.out.println("Introduzca la dirección del fichero a fragmentar: ");
			String datt = teclado.nextLine();

			File fichOK = new File(datt);

			FileReader fx = new FileReader(fichOK);		//Se cuentan las líneas del fichero a dividir
			LineNumberReader lnr = new LineNumberReader(fx);
			int countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();
			
			Scanner scan1 = new Scanner(new File(datt));
			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int totalTam=0;			
			String caja1[] = new String[countL];	//Se leen las líneas del fichero a dividir y se almacenan en un array
			for(int i=0;i<countL;i++){
				strLine = br1.readLine();
				caja1[i]=strLine;
				totalTam+=strLine.length();
				scan1.nextLine();
			}

			sort(caja1,0,caja1.length-1);		//Se ordena dicho array
			System.out.println("Se ha ordenado el fichero. Se procede a subdividir.");

			//ZONA DE BLOOMFILTER
			XXBloomFilter<String> bloomFilter = new XXBloomFilter<String>(2);

			FileWriter fw = new FileWriter(datt);  	
			BufferedWriter out = new BufferedWriter(fw);
			String s1;
			for(int k=0;k<caja1.length;k++){
				s1=caja1[k];
				bloomFilter.add(s1);
				out.write(s1);
				out.newLine();
			}

			out.close();
			in.close();

			//Se imprime el Bloom en fichero
			String nombreB = String.format("%03d",998);
	  		File fileB= new File(nombreB);
			FileOutputStream fosB = new FileOutputStream(nombreB+".txt");
			byte [] bytearray=bloomFilter.returnBytes();
			fosB.write(bytearray);
      		fosB.close();


			int flagB=0;

			File fichN = new File(datt);	
			FileInputStream fis1 = new FileInputStream(fichN);
			DataInputStream in1 = new DataInputStream(fis1);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(in1));
			String strLine1;

			

			//Creacion de subficheros
			int j=0;
			int totArch=-1;

			int contadorTotal=0;

			while(contadorTotal<totalTam){
				String nombre = String.format("%03d",j);
				totArch++;
				FileWriter fw1 = new FileWriter(nombre + ".txt"); 
				BufferedWriter out1 = new BufferedWriter(fw1);
				int contador =0;

				while(contador<TAM_MAX && flagB!=1){
					strLine1 = br2.readLine();
					
					if(strLine1 != null){
						contador+=1;
						contadorTotal+=1;
						contador+=strLine1.length();
						contadorTotal+=strLine1.length();
						out1.write(strLine1);
						out1.write(10);
						
					}else{
						flagB=1;
					}
				}
				out1.close();
				j++;
			}
			in1.close();

			/////////////////////////////////////////////
			//		Creacion del catalogo			   //
			////////////////////////////////////////////
			String nombre = String.format("%03d",999);
			FileWriter fwcat = new FileWriter(nombre + ".txt"); 
			BufferedWriter outcat = new BufferedWriter(fwcat);

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
					outcat.write(url0);
					outcat.newLine();
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
			
						Scanner scanF0 = new Scanner(new File(fichArr));
						FileInputStream fisF0 = new FileInputStream(archArr);
						DataInputStream inF0 = new DataInputStream(fisF0);
						BufferedReader brF0 = new BufferedReader(new InputStreamReader(inF0));
						String urlF0;

						urlF0=brF0.readLine();
						String urlF1=null;
						while((urlF0=brF0.readLine())!=null){
							urlF1=urlF0;
						}
						outcat.write(urlF1);
						brF0.close();
					}
				}else{
					break;
				}
			}
			outcat.close();			


		}catch(IOException e){
			System.out.println("Error al leer el fichero.");
			e.printStackTrace();
		}
	}

	//Implementación del algoritmo de ordenación. En este caso utilizamos el algoritmo de fusión.

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

