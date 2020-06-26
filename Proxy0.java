import java.util.*;
import java.io.*;

public class Proxy0{

	public static double memIni;
	public static Runtime rt;

	public static double usoMemoria(){
		rt.gc();
		return rt.totalMemory() - rt.freeMemory() - memIni;
	}

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.println("Introduzca el fichero a revisar");
		String fichOK = in.nextLine();

		divide(fichOK);
	}

	public static void divide(String fichOK){
		rt = Runtime.getRuntime();				//Cambiar la comprobación de memoria
		rt.gc();
		memIni = rt.totalMemory() - rt.freeMemory();

		double mem = usoMemoria(); //Esta parte no la tengo muy clara, porque obtenemos la memoria consumida por la aplicación, no el tamaño del subfichero.
		System.out.println(mem);


		if(mem<=1047000){
			System.out.println("Es menor");
			//Es correcto, se llamaría a la función comprobar
			comprobar(fichOK);

		}else{
			System.out.println("Error, demasiado grande");
			//Es erróneo, se llama a la función dividir
			split(fichOK);
		}
	}
	public static void split(String fichOK){
		try{
			Scanner scan = new Scanner(new File(fichOK));
			int count=0;
			while(scan.hasNextLine()){
				scan.nextLine();
				count++;
			}

			System.out.println("Lineas del fichero: " + count);
			double DivLineas = count/2;

			double temp = count/DivLineas;
			int temp1 = (int) temp;

			int NumArchivos=0;

			if(temp1 == temp){
				NumArchivos = temp1;
			}else{
				NumArchivos= temp1 + 1;
			}
			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			for(int j = 1; j<=NumArchivos; j++){
				FileWriter fw = new FileWriter("/home/pablo/EDA/"+ j + ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
				BufferedWriter out = new BufferedWriter(fw);

				for(int i = 1; i <=DivLineas; i++){
					strLine = br.readLine();
					if(strLine != null){
						out.write(strLine);
						if( i != DivLineas){
							out.newLine();
						}
					}
				}
				out.close();
			}
			in.close();
			for(int k=0;k<NumArchivos;k++){
				divide("/home/pablo/EDA"+k+".txt");

			}
		}catch(IOException e){
			System.out.println("Error");
		}
	}
	public static void comprobar(String fichOK){
		try{
			Scanner in = new Scanner(System.in);
			System.out.println("Introduzca el fichero de URLs malignas");
			String fichBAD = in.nextLine();

			Scanner scan1 = new Scanner(new File(fichOK));
			Scanner scan2 = new Scanner(new File(fichBAD));

			FileInputStream fis1 = new FileInputStream(fichOK);
			DataInputStream in1 = new DataInputStream(fis1);

			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String strLine1;

			FileInputStream fis2 = new FileInputStream(fichBAD);
			DataInputStream in2 = new DataInputStream(fis2);

			BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
			String strLine2;

			ArrayList <String> caja1 = new ArrayList <String>();
			while(scan1.hasNextLine()){
				strLine1 = br1.readLine();
				caja1.add(strLine1);
				scan1.nextLine();
			}
			ArrayList <String> caja2 = new ArrayList <String>();  //ArrayList no es un array, es un objeto que simula un array infinito
			while(scan2.hasNextLine()){
				strLine2 = br2.readLine();
				caja2.add(strLine2);
				scan2.nextLine();
			}

			for(int i=0;i<caja1.size();i++){
				String url1= caja1.get(i);
				for(int j=0;j<caja2.size();j++){
					String url2=caja2.get(j);
					if(url1.equals(url2)){
						System.out.println("Detectada URL dañina: "+url1);
					}
				}
			}
		}catch(IOException e){
			System.out.println("Error");
		}
	}
}