//usar .stream()
import java.io.*;
import java.util.*;
public class GenF{

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

			for(int j = 1001; j<=numArchivos; j++){
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
 
			for (int h=1;h<topeL;h++){
				int alfa=comprobar();
				newFich(h,alfa,numArchivos);
				System.out.println("flag");
			}


		}catch(IOException e){
			System.out.println("Error en genIni");
			e.printStackTrace();
		}
	}

	public static int comprobar(){
		int j=0;
		for(int i=1;i<=1000;i++){
			String s= "/home/pablo/EDA/fichEDA/"+ i + ".txt";
			File fich= new File(s);
			if(fich.exists()){
				j++;
			}else{
				break;
			}
		}
		if(j==0){		//SI SE QUITA ESTA ULTIMA PARTE FUNCIONA REPITIENDO TODO
			j++;
		}
		return j;
	}


	public static void newFich(int ini, int alfa, long numArchivos){
		//alfa indica por que archivo se comienza a escribir
		try{
			long tope = 1035000; //Tamaño provisional
			long topeL= tope/50; //Maximo de lineas que puede tener un fichero
			String strLineP;
			String strLine=". ";

			String datt1= "/home/pablo/EDA/fichEDA/"+alfa+".txt";
			File fichL= new File(datt1);

			
			for(long j=1001;j<numArchivos;j++){
				long minLA = 1000; //Cota por arriba
				String datt = "/home/pablo/EDA/fichEDA/"+ j + ".txt";
				File fichOK = new File(datt);
				FileInputStream fis = new FileInputStream(fichOK);
				DataInputStream in = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				FileWriter fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ alfa+ ".txt"); //PARA QUE NO SE SOBREESCRIBA, INTENTAR USAR UN FOS COMO EN ACTFICH
				BufferedWriter out = new BufferedWriter(fw);

				if(fichOK.exists()){
					for(long i=ini;i<topeL;i++){      //EL INI EN PRINCIPIO DA IGUAL, HAY QUE QUITARLO
						long chars ;

						strLineP=br.readLine();
						chars = strLineP.length();
						if(chars<minLA && strLineP!=null){	//Saca la línea más corta del fichero
							minLA=chars;
							strLine=strLineP;
						}
					}
					
					if(fichL.length()<tope){
						if(strLine!=null){
							out.write(strLine);  //Debería escribirla en el fichero
							//SE DEBE BORRAR ESTA LINEA DESPUES DE ESCRIBIRLA
							out.newLine();
							out.flush();
							
						}	
					}else{						//Cuando se llene el fichero, abrir otro y escribirla ahí
						out.close();
						alfa++;
						datt1= "/home/pablo/EDA/fichEDA/"+ alfa + ".txt";
						fichL = new File(datt);
						fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ alfa+ ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
						out = new BufferedWriter(fw);
						out.write(strLine);
						System.out.println("flag2");
						out.newLine();
						out.flush();
					}
					
				}else{
					out.close();
					break;
				}
				in.close();
			
			}
		}
		catch(IOException e){
			System.out.println("Error en newFich");
			e.printStackTrace();
		}
	}
}