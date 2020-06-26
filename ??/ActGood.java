//TERMINADO

import java.io.*;
import java.util.*;
import java.nio.charset.*;

/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

public class ActGood{

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

	

		long tope = 815000; //Tamaño provisional
		long topeL= tope/50; //Maximo de lineas que puede tener un fichero

		Scanner teclado = new Scanner(System.in);
		System.out.println("Introduzca una nueva URL maligna: ");
		String add =  teclado.nextLine();




		try{

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


					if(h==totArch){
						FileReader fx0 = new FileReader(archArr);
						LineNumberReader lnr0 = new LineNumberReader(fx0);
						int countLU=0;
						while(lnr0.readLine ()!= null){
							countLU ++;
						}
						lnr0.close();

						String ult0 = nombre2+".txt";
						leeSubfichero(ult0);
						creaIndices();
						generate();

						String ultima = sol[countLU-2];
						aL.add(ultima);
						
					
					}
					}else{
						break;
					}
			}
			int flag=0;
			for(int j=0;j<(aL.size())-1;j++){
				// mayor que el primero y menor que le ultimo
				if((j==0) && ((add.compareTo(aL.get(j))) <0)){
					String uno = "000";
					String fichero = uno + ".txt";
					File archivo = new File(fichero);
					
					BufferedWriter out = new BufferedWriter(new FileWriter(fichero,true));
					out.newLine();
					out.write(add);

					flag = 1;
					
					out.close();


				}else if(j==aL.size()-2 && add.compareTo(aL.get(aL.size()-1))>=0){
					String nombre3 = String.format("%03d",aL.size()-1);
					String fichero = nombre3 + ".txt";
					File archivo = new File(fichero);
					 
					BufferedWriter out = new BufferedWriter(new FileWriter(fichero,true));
					out.newLine();
					out.write(add);

					flag = j+1;
					
					out.close();
				}else if(((add.compareTo(aL.get(j)))>=0) && ((add.compareTo(aL.get(j+1)))<0)){
					String nombre4 = String.format("%03d",j);
					String fichero = nombre4 + ".txt";
					File archivo = new File(fichero);
					 
					BufferedWriter out = new BufferedWriter(new FileWriter(fichero,true));
					out.newLine();
					out.write(add);

					flag = j+1;
					out.close();
				}
			}

			String nombre5 = String.format("%03d",flag);

			String fichero = nombre5 + ".txt";
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

			archivo.delete();
			System.out.println("URL introducida en el fichero "+flag+".txt");

			sort(caja1,0,caja1.length-1);

			FileWriter fw2 = new FileWriter(nombre5+".txt"); 
			BufferedWriter out2 = new BufferedWriter(fw2);

			for(int w=0;w<caja1.length;w++){
				strLine = caja1[w];
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