import java.util.*;
import java.io.*;
public class comprobar{
	public static void main(String args[]){
		try{

			Scanner scan1 = new Scanner(new File("comp.txt"));
			Scanner scan2 = new Scanner(new File("bad.txt"));

			FileInputStream fis1 = new FileInputStream("comp.txt");
			DataInputStream in1 = new DataInputStream(fis1);

			BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
			String strLine1;

			FileInputStream fis2 = new FileInputStream("bad.txt");
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