import java.io.*;
import java.util.*;
import java.lang.*;

public class pruebas{
	public static void main(String args[]){
		String a = "www.google.com";
		String b="www.google.es";

		System.out.println(hash(a));
		System.out.println(hash(b));

		try{
			File fich = new File("000.txt");
			FileReader fx = new FileReader(fich);		//Se cuentan las líneas del fichero a dividir
			LineNumberReader lnr = new LineNumberReader(fx);
			int countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();

			System.out.println(countL);
		}catch(IOException e){
			e.printStackTrace();
		}


	}

	public static int hash(String txt){
		int n = txt.length();
		int h = 0;
		for(int i = 0; i < n; i++){h=31*h+txt.charAt(i);}
		return h;
	}

	//Concepto: valor absoluto del resultado y hacer totalbits%resultado y ese es el bit que se pone a 1(posicion)
}