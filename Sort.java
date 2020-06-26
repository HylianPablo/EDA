//usar .stream()
import java.io.*;
import java.util.*;
public class Sort{

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
			int countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();
			System.out.println("Número de líneas del fichero: "+countL);

			Scanner scan1 = new Scanner(new File(datt));
			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			String strLine;

			//ArrayList <String> caja1 = new ArrayList <String>();
			String caja1[] = new String[countL];
			for(int i=0;i<countL;i++){
				strLine = br1.readLine();
				caja1[i]=strLine;
				scan1.nextLine();
			}

			//HASTA AQUÍ TARDA UNOS 5 SEGUNDOS

			//long tam = caja1.size();
			//System.out.println(tam);
			String referencia = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

			int xx=0;

			Quiksort(caja1);
			for(int i=0;i<countL;i++){
				System.out.println(caja1[i]);
			}

		}catch(IOException e){
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	public static int partition(String[] caja1,int x1,int x2){
		String piv = caja1[x1];
		while(x1<x2){
			String v1;
			String v2;
			while((v1=caja1[x1]).compareTo(piv)<0){
				x1++;
			}
			while((v2=caja1[x2]).compareTo(piv)>0){
				x2--;
			}
			caja1[x1]=v2;
			caja1[x2]=v1;
		}
		return x1;
	}

	public static void Quiksort(String[] caja1,int x1,int x2){
		if(x1>=x2){
			//System.out.println("Valores erróneos.");
			return;
		}
		int piv = partition(caja1,x1,x2);
		Quiksort(caja1,x1,piv);
		Quiksort(caja1,piv+1,x2);
	}

	public static void Quiksort(String[] caja1){
		Quiksort(caja1,0,caja1.length-1);
	}

















	
}



