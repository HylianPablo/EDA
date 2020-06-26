//TERMINADO
import java.io.*;
import java.util.*;

/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

public class Merge{

	public static void main(String args[]){
		Scanner teclado = new Scanner (System.in);
		System.out.println("Introduzca la dirección del fichero a fragmentar: ");
		String datt = teclado.nextLine();

		genIni(datt);

	}
	public static void genIni(String datt){
		//long topeL;
		try{
			File fichOK = new File(datt);

			long tope = 700000;  //ESTÁ AL PUTO LÍMITE, BAJAR UN POCO
			//Tamaño medio de las urls es de 50 caracteres
			long topeL= tope/50; //Maximo de lineas que puede tener un fichero

			FileReader fx = new FileReader(fichOK);
			LineNumberReader lnr = new LineNumberReader(fx);
			int countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();
			System.out.println("Número de líneas del fichero a dividir: "+countL);
			long numArchivos = countL/topeL;
			//numArchivos++;
			//long numArchivos1= numArchivos+1;
			System.out.println("Se generarán un total de: "+(numArchivos)+" archivos.");

			

			Scanner scan1 = new Scanner(new File(datt));
			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			String strLine;

			
			String caja1[] = new String[countL];
			for(int i=0;i<countL;i++){
				strLine = br1.readLine();
				caja1[i]=strLine;
				scan1.nextLine();
			}

			long iniTime = System.currentTimeMillis();
			sort(caja1,0,caja1.length-1);
			long finTime = System.currentTimeMillis();
			double tiempo =(double) (finTime - iniTime)/1000;
			System.out.println("Tiempo transcurrido en ordenar "+countL+" de URLs: "+tiempo+" segundos.");


			FileWriter fw = new FileWriter("danger1.txt"); 
			BufferedWriter out = new BufferedWriter(fw);
			String s1;
			for(int k=0;k<caja1.length;k++){
				s1=caja1[k];
				out.write(s1);
				out.newLine();
			}

			out.close();
			in.close();


			File fichN = new File("danger1.txt");
			FileInputStream fis1 = new FileInputStream(fichN);
			DataInputStream in1 = new DataInputStream(fis1);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(in1));
			String strLine1;

			for(int j = 0; j<numArchivos; j++){
				String nombre = String.format("%03d",j);
				FileWriter fw1 = new FileWriter(nombre + ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
				BufferedWriter out1 = new BufferedWriter(fw1);

				for(int i = 0; i <topeL; i++){
					strLine1 = br2.readLine();
					if(strLine1 != null){
						out1.write(strLine1);
						if( i != topeL){
							out1.newLine();
						}
					}
				}
				out1.close();
			}
			in1.close();





		}catch(IOException e){
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

