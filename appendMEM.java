//Permite escribir sobre ficheros, como bytes, utilizando FOS

import java.io.*;
import java.util.*;



public class appendMEM{

	public static long memIni;
	public static Runtime rt;

	public static long usoMemoria(){
		rt.gc();
		return rt.totalMemory() - rt.freeMemory() - memIni;
	}

	public static void main (String args[]){
		File fich = new File ("ejemplo.txt");

		rt = Runtime.getRuntime();
		rt.gc();
		memIni = rt.totalMemory() - rt.freeMemory();

		Scanner teclado = new Scanner(System.in);
		String add =  teclado.nextLine();
		add = add + "\n";
		byte b [] = add.getBytes();

		try(FileOutputStream fos = new FileOutputStream(fich, true)){
			fos.write(b);
		}
		catch (IOException e){
			System.out.println("Error");
			e.printStackTrace();
		}

		long data = usoMemoria();
		System.out.println(data);
	}
}