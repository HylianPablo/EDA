package practica;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ActualizaFicheros {

	public static void main(String[] args) {
		System.out.println("Introduce por teclado la url que deseas que sea una de las pprohibidas: \n");
		Scanner entrada= new Scanner (System.in);
		String url_entrada= entrada.nextLine();
		System.out.println(url_entrada);
		int numero_fich=0;
	
		//Scanner lectura = null;
	//for(int i=0; i<=)	
		try {
			/*File dir = new File("");
			String[] numero_ficheros =dir.list();*/
			//ArrayList<String> nombreArrayList = new ArrayList<String>();
			ArrayList<String> almacena=new ArrayList<String> ();
			//FileWriter casa = new FileWriter("C://Users//Alberto//Desktop//CASA.txt");
		
			for (int i=1; i<= 100;i++) {
				
				almacena.clear();//vacio el arraylist si no es el fichero que necesito
				numero_fich=i;
				//BufferedWriter escribe = new BufferedWriter(new FileWriter(i+".txt",true));
				
				FileReader entrada2 = new FileReader(i+".txt");
				BufferedReader br=new BufferedReader(entrada2);
				String urlfichero;
				boolean condicion= false;
				while((urlfichero=br.readLine())!=null) {
				//	casa.write(str);
					almacena.add(urlfichero);
					int valor= url_entrada.compareTo(urlfichero);
					if(valor<0 && condicion==false) {
						almacena.add(url_entrada);
						condicion=true;
						//escribe.write(url_entrada);
						i=101; //condicion de salida para que acabe de leer fiheros puesto que ya hemos encontrado el que necesitabamos
					}
				}

				
				
			}
			Collections.sort(almacena);//ordena las urls
			introduce_subfichero(almacena, numero_fich);

		}catch(IOException e) {
			System.out.println("No encontrado");
		}
		
	}
	
	private static void introduce_subfichero(List almacena, int numero_fich) {
		long contador_urls=almacena.size();

		try {
			FileWriter escribe=new FileWriter("C://Users//Alberto//Desktop//prueba.txt");
			BufferedWriter br2 = new BufferedWriter(escribe);

			for(int  i =0;i<contador_urls; i++) {
				
				br2.write(String.valueOf(almacena.get(i)));
				br2.newLine();
			}
			
			
			
		}catch(IOException e) {
			System.out.println("No encontrado");
		}
		
	}

}
	
