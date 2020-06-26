import java.io.*;
import java.util.*;
public class Actualiza0{

	public static byte subfich;

	public static void main(String args[]){

		Scanner entrada;
		PrintWriter resultado;
		File fich;
		int i =0;

		try{
			entrada = new Scanner(new File("ejemplo.txt"));

		}
		catch (FileNotFoundException e){
			System.out.println("Error de lectura");
			return;
			 }

		fich = new File("ejemplo.txt");

		while (entrada.hasNext("\n")){
			i++;
		}
			System.out.println(i);
		
		int size = (int) fich.length();

		try{
			resultado = new PrintWriter("ejemploo.txt");
		}
		catch(IOException e){
			System.out.println("No puede abrirse el fichero");
			entrada.close();
			return;
		}

		Scanner teclado = new Scanner(System.in);
		String nuevaURL = teclado.nextLine();

		/*while (entrada.hasNextLine()){
			String linea = entrada.nextLine();
			resultado.println(linea);
		}*/

		resultado.println(nuevaURL);
		entrada.close();
		resultado.close();
	}

}