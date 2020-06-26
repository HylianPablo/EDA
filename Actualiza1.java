//Implementación sobre como leer ficheros y como pegar el mismo contenido

import java.io.*;
import java.util.*;
public class Actualiza1{

	public static byte[] subfich;
	public static void leeSubfichero(String nomfich) throws IOException{

		File fich = new File(nomfich);
		int tam = (int) fich.length();
		subfich= null;
		subfich = new byte[tam];

		try (FileInputStream fis = new FileInputStream(fich)){
			fis.read(subfich);

		}
		
		try (FileOutputStream fos = new FileOutputStream (fich,true)){
			fos.write(subfich);
		}
	}

	public static void main (String args[]) throws IOException{
		File fich = new File ("ejemploo.txt");
		leeSubfichero("ejemploo.txt");
	}

}