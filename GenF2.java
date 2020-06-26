//usar .stream()
import java.io.*;
import java.util.*;
public class GenF2{

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

			long tope = 1035000;
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

			long numArchivos = countL/topeL;
			
			System.out.println("Se generarán un total de: "+numArchivos);
			numArchivos= numArchivos+1000;

			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int j=1;

			String sfile="/home/pablo/EDA/fichEDA/"+ j + ".txt";
			File fichL = new File("/home/pablo/EDA/fichEDA/"+ j + ".txt");
			FileWriter fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ j + ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
			BufferedWriter out = new BufferedWriter(fw);

			for(long i = 1; i <=1000; i++){
				strLine = br.readLine();
				if(strLine.length()==i &&strLine.length()<200){
					out.write(strLine);
					out.newLine();
				}

				if(fichL.length()<tope){
						if(strLine!=null){
							//out.write(strLine);  //Debería escribirla en el fichero
							//SE DEBE BORRAR ESTA LINEA DESPUES DE ESCRIBIRLA
							replaceString(strLine,sfile);
							//out.newLine();
							out.flush();
							
						}	
				}else{						//Cuando se llene el fichero, abrir otro y escribirla ahí
					out.close();
					j++;
					sfile= "/home/pablo/EDA/fichEDA/"+ j + ".txt";
					fichL = new File(sfile);
					fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ j+ ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
					out = new BufferedWriter(fw);
					out.write(strLine);
					replaceString(strLine,sfile);
					System.out.println("flag2");
					out.newLine();
					out.flush();
				}
				//System.out.println("flag");
			}

		}catch(IOException e){
			System.out.println("Error en genIni");
			e.printStackTrace();
		}
	}


	

	public static void replaceString(String replace, String fich){
		try{
			BufferedReader file = new BufferedReader(new FileReader(fich));
			String line;
			StringBuffer inputBuffer = new StringBuffer();

			while((line=file.readLine())!=null){
				inputBuffer.append(line);
				inputBuffer.append("\n");
			}
			String inputStr = inputBuffer.toString();
			file.close();

			inputStr=inputStr.replace(replace,"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

			/*if(Integer.parseInt(type)==0){
				inputStr=inputStr.replace(replaceWith+"1",replaceWith+"0");
			}
			else if(Integer.parseInt(type)==1){
				inputStr=inputStr.replace(replaceWith+"0",replaceWith+"1");
			}*/

			FileOutputStream fileOut = new FileOutputStream(fich);
			fileOut.write(inputStr.getBytes());
			fileOut.close();
		}
		catch(IOException e){
			System.out.println("Error en replaceString");
			e.printStackTrace();
		}
	}
}