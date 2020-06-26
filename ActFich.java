//Permite escribir sobre ficheros, como bytes, utilizando FOS

//TO-DO Optimizar en que fichero va la url negativa
// ¿Y si justo está lleno el n-ésimo fichero?

import java.io.*;
import java.util.*;

public class ActFich{
	public static void main (String args[]){

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
				aL.add(url0);
				br0.close();
			}else{
				break;
			}
		}

		long tope = 1035000; //Tamaño provisional
		long topeL= tope/50; //Maximo de lineas que puede tener un fichero

		Scanner teclado = new Scanner(System.in);
		System.out.println("Introduzca una nueva URL maligna: ");
		String add =  teclado.nextLine();
		add = add + "\n";
		byte b [] = add.getBytes();



		try{
			for(int k=1;k<=1000;k++){
				String model = ("/home/pablo/EDA/fichEDA/"+k + ".txt");
				
				//System.out.println(model);
				File file = new File(model);
				if(file.exists()){
					FileReader fx = new FileReader(file);
					LineNumberReader lnr = new LineNumberReader(fx);
					long countL=0;
					while(lnr.readLine ()!= null){
						countL ++;
					}
					lnr.close();

					FileOutputStream fos = new FileOutputStream(model, true);
					if(countL<topeL){
						fos.write(b);
						System.out.println("La nueva URL se ha introducido en el fichero: "+k+".txt");
						break;
					}
				}
			}
		}
		catch (IOException e){
			System.out.println("Error");
			e.printStackTrace();
		}
		
		
		System.out.println("Proceso terminado.");
	}
}