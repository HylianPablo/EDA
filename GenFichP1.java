//usar .stream()
import java.io.*;
import java.util.*;
public class GenFichP{
	public static void main(String args[]){
		try{
			Scanner teclado = new Scanner (System.in);
			System.out.println("Introduzca la dirección del fichero a fragmentar: ");
			String datt = teclado.nextLine();

			File fichOK = new File(datt);
			

			//long tope = 100000;
			//Tamaño medio de las urls es de 50 caracteres

			FileReader fx = new FileReader(fichOK);
			LineNumberReader lnr = new LineNumberReader(fx);
			long countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();
			System.out.println("Número de líneas del fichero: "+countL);

			long lineasRestantes = countL;



			//long topeL= tope/50; //Maximo de lineas que puede tener un fichero

			//System.out.println(fichOK.length());

			//long NumArchivos = countL/topeL;
			//System.out.println("Se generarán un total de: "+NumArchivos);

			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			String bestString;
			int minL=0;

			int numFicheros=1;
			while(lineasRestantes!=0){
				FileWriter fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ numFicheros + ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
				File f1 = new File("/home/pablo/EDA/fichEDA/"+ numFicheros + ".txt");
				BufferedWriter out = new BufferedWriter(fw);
				while(f1.length()<1035000){
					bestString=best(minL,fichOK,datt);
					lineasRestantes--;
					if(bestString.length()>minL){
						minL=bestString.length();
					}
					if(bestString != null){
						out.write(bestString);
						if( f1.length()<1035000){
							out.newLine();
						}
					}
				}
				numFicheros++;
				out.close();
			}

			//out.close();
			//in.close();

		}catch(IOException e){
			System.out.println("Error");
		}
	}
	public static String best(int minL, File fichOK, String datt) {
		try{
			Scanner inS = new Scanner(datt);
			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine= " ";
			int chars = 0;
			int minLA =101; //Cota por arriba, minL es la cota por abajo
			while(inS.hasNextLine()){
				chars=0;
				while(inS.hasNextByte()){
					chars++;
				}
				if(chars>minL && chars<minLA){
					minLA=chars;
					strLine=br.readLine();
				}
			}
			in.close();
			return strLine;
		}catch(IOException e){
			System.out.println("Error 2");
		}
	}
}