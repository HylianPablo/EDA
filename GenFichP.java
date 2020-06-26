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
			
			FileReader fx = new FileReader(fichOK);
			LineNumberReader lnr = new LineNumberReader(fx);
			long countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();
			System.out.println("Número de líneas del fichero: "+countL);

			long lineasRestantes = countL;

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
				System.out.println("flag3");
				numFicheros++;
				//out.close();
			}
			//out.close();
			//in.close();

		}catch(IOException e){
			System.out.println("Error");
		}
	}
	public static String best(int minL, File fichOK, String datt) {
		String strLine=" ";
		try{
			Scanner inS = new Scanner(datt);
			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//System.out.println("flag1");

			String strLineP= " ";
			int chars ;
			int minLA =101; //Cota por arriba, minL es la cota por abajo

			FileReader fx = new FileReader(fichOK);
			LineNumberReader lnr = new LineNumberReader(fx);
			long countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();

			for(int k=0;k<countL;k++){
				//chars=0;
				strLineP=br.readLine();
				//System.out.println("flag2");
				chars = strLineP.length();
				if(chars>minL && chars<minLA){
					minLA=chars;
					strLine=strLineP;
				}
			}
			in.close();
			//return strLine;
		}
		catch(IOException e){
			System.out.println("Error 2");
		}finally{
			
		}
		return strLine;
	}
}