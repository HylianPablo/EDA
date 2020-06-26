import java.util.*;			
import java.io.*;
import java.nio.charset.*;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Collection;
import java.lang.*;

/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

public class ReadFichBin{

	public static byte contenido[];

	public static void main(String args[]) throws IOException{

		String fileName = "998.txt";

		try { //Lectura del fichero con el array de bytes
			java.io.File fichero = new java.io.File(fileName); 
			FileInputStream ficheroStream = new FileInputStream(fichero);
			contenido = new byte[(int)fichero.length()]; 
			ficheroStream.read(contenido);

			//objInput.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} 
		

		/*for(int i=0; i<contenido.length; i++){

			System.out.println(contenido[i]);

		}*/

		BitSet bitset = fromByteArray(contenido);

	}

	public static BitSet fromByteArray(byte[] bytes) {
    BitSet bits = new BitSet();
    for (int i = 0; i < bytes.length * 8; i++) {
      if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
        bits.set(i);
      }
    }
    return bits;
  }

}





