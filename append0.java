//Permite escribir sobre ficheros, como bytes, utilizando FOS

//TO-DO Optimizar en que fichero va la url negativa

import java.io.*;
import java.util.*;

public class append0{
	public static void main (String args[]){
		File fich = new File ("ejemplo.txt");

		Scanner teclado = new Scanner(System.in);
		System.out.println("Introduzca una nueva URL maligna: ");
		String add =  teclado.nextLine();
		while(add.length()!=0){
			add= add + "\n";
			byte b [] = add.getBytes();
			System.out.println("Introduzca una nueva URL maligna: ");
			add = teclado.nextLine();


			try(FileOutputStream fos = new FileOutputStream(fich, true)){
				fos.write (b);
			}
			catch (IOException e){
				System.out.println("Error");
				e.printStackTrace();
			}
		}
		System.out.println("Proceso terminado.");
	}
}