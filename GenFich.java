//usar .stream()
import java.io.*;
import java.util.*;
public class GenFich{
	public static void main(String args[]){
		try{
			Scanner teclado = new Scanner (System.in);
			System.out.println("Introduzca la dirección del fichero a fragmentar: ");
			String datt = teclado.nextLine();

			File fichOK = new File(datt);

			long tope = 100000;
			//Tamaño medio de las urls es de 50 caracteres

			FileReader fx = new FileReader(fichOK);
			LineNumberReader lnr = new LineNumberReader(fx);
			long countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();
			System.out.println("Número de líneas del fichero: "+countL);

			long topeL= tope/50; //Maximo de lineas que puede tener un fichero

			//System.out.println(fichOK.length());

			long NumArchivos = countL/topeL;
			System.out.println("Se generarán un total de: "+NumArchivos);

			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			for(int j = 1; j<=NumArchivos; j++){
				FileWriter fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ j + ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
				BufferedWriter out = new BufferedWriter(fw);

				for(int i = 1; i <=topeL; i++){
					strLine = br.readLine();
					if(strLine != null){
						out.write(strLine);
						if( i != topeL){
							out.newLine();
						}
					}
				}
				out.close();
			}
			in.close();
		}catch(IOException e){
			System.out.println("Error");
		}
	}
}