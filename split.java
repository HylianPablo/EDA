import java.util.*;
import java.io.*;

public class split{
	public static void main(String args[]){
		try{

			File fich =  new File ("divide.txt");
			double DivLineas = 1.0; // Cuantas lineas tiene cada subfichero

			Scanner scan = new Scanner(new File("divide.txt"));

			int count=0;
			while(scan.hasNextLine()){
				scan.nextLine();
				count++;
			}

			System.out.println("Lineas del fichero: " + count);

			double temp = count/DivLineas;
			int temp1 = (int) temp;

			int NumArchivos=0;

			if(temp1 == temp){
				NumArchivos = temp1;
			}else{
				NumArchivos= temp1 + 1;
			}

			System.out.println("Numero de archivos generados: "+ NumArchivos);

			FileInputStream fis = new FileInputStream("divide.txt");
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

		}
		catch(IOException e){
			System.out.println("Error");
		}
	}
}