//Permite escribir ficheros utilizando fileWriter

import java.io.*;
import java.util.*;

public class append{
	public static void main (String args[]){
		File fich = new File ("ejemplo.txt");

		Scanner teclado = new Scanner(System.in);
		String add = teclado.nextLine();

		try(Writer fileWriter = new FileWriter(fich, true)){
			fileWriter.write("\n"+ add);
		}
		catch (IOException e){
			System.out.println("Error");
			e.printStackTrace();
		}
	}
}