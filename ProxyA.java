import java.util.*;  //0.6 segundos cada fichero y ocupa 2mb en memoria
import java.io.*;

public class ProxyA{

	public static double memIni;
	public static Runtime rt;

	public static double usoMemoria(){
		rt.gc();
		return rt.totalMemory() - rt.freeMemory() - memIni;
	}

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		System.out.println("Introduzca el fichero a revisar:");
		String fichOK = in.nextLine();
		System.out.println(" ");

		comprobar(fichOK);
	}
	
	public static void comprobar(String fichOK){
		try{

			rt = Runtime.getRuntime();				//Cambiar la comprobación de memoria
			rt.gc();
			memIni = rt.totalMemory() - rt.freeMemory(); //¿Por qué sale memoria negativa?


			long iniTime = System.currentTimeMillis();
			Scanner scan1 = new Scanner(new File(fichOK));

			FileInputStream fis1 = new FileInputStream(fichOK);
			DataInputStream in1 = new DataInputStream(fis1);

			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String strLine1;

			ArrayList <String> caja1 = new ArrayList <String>();
			while(scan1.hasNextLine()){
				strLine1 = br1.readLine();
				caja1.add(strLine1);
				scan1.nextLine();
			}
			
			for (int k=1;k<=1000;k++){
				String model = ("/home/pablo/EDA/fichEDA/"+ k + ".txt");
				File fichBAD = new File(model);
				if(fichBAD.exists()){
					Scanner scan2 = new Scanner(new File(model));

					FileInputStream fis2 = new FileInputStream(model);
					DataInputStream in2 = new DataInputStream(fis2);

					BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
					String strLine2;

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
								long finTime = System.currentTimeMillis();
								double tiempo =(double) (finTime - iniTime)/1000;
								System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
								double mem = usoMemoria(); 
								System.out.println("Uso de memoria: "+mem+" bytes."); 
								System.out.println(" ");
								
								
							}
						}
					}
				}else{
					break;
				}
				System.out.println("Lectura del fichero terminada.");
				long finTime = System.currentTimeMillis();
				double tiempo =(double) (finTime - iniTime)/1000;
				System.out.println("Tiempo transcurrido: "+tiempo+" segundos.");
			}
			double mem = usoMemoria(); 
			System.out.println("Uso de memoria: "+mem+" bytes."); 
			System.out.println("Fin del programa.");
		}catch(IOException e){
			System.out.println("Error");
		}
	}
}