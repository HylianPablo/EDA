import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Collection;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class BloomFilterCasero<E>{

	private static byte [] data=new byte[712000];
    private static int noHashes;
    //private static int hashMask;

	public BloomFilterCasero(int noHashes) {	//Tamaño mal hecho, revisar
    	this.noHashes = noHashes;
  	}

  	public void add(String s) {
    	for (int n = 0; n < noHashes; n++) {
      	int hc = hash(s);
      	int bitNo =  Math.abs(hc%(712000*8));// & this.hashMask;
      	setBit(data,bitNo);
    	}
  	}

  	public void setBit(byte[] v, int i){v[i/8] |= 1 << (i%8);}
  	public int getBit(byte[] v, int i){return (v[i/8] >> (i%8)) & 1;}

  	public byte[] returnBytes(){
  		return data;
  	}

  	public boolean contains(String s) {
    	for (int n = 0; n < noHashes; n++) {
     		int hc = hash(s);
      		int bitNo = Math.abs(hc%(712000*8));
      		if (getBit(data,bitNo)==0) return false;
    	}
    	return true;
  	}


	public static int hash(String txt){
		int n = txt.length();
		int h = 0;
		for(int i = 0; i < n; i++){h=31*h+txt.charAt(i);}
		return h;
	}




    public static void main(String[] args) {

        BloomFilterCasero<String> bloomFilter = new BloomFilterCasero<String>(4);

        try{
        	String datt="000.txt";

        	File fich = new File("000.txt");
			FileReader fx = new FileReader(fich);		//Se cuentan las líneas del fichero a dividir
			LineNumberReader lnr = new LineNumberReader(fx);
			int countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();

        	Scanner scan1 = new Scanner(new File(datt));
			FileInputStream fis = new FileInputStream(fich);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			String strLine;

			for(int i=0;i<countL;i++){
				strLine = br1.readLine();
				//int hash=hash(strLine);
				bloomFilter.add(strLine);
				scan1.nextLine();
			}

		}catch(IOException e){
			e.printStackTrace();
		}

		String s1="ftp://0.0rg/7CHeAG8sH_qvQ8IUB0JOlKcs/P";
		String s2="www.googleuwuoWo.com";
		String s3= "ftp://0.0rg/B/uvFmyUIsoX2IERl/ebuwRUJA1yQ2kKo_pS/gH/";
		String s4="www.uwu.com";

		printBits(bloomFilter.getBitSet());

		System.out.println(bloomFilter.contains(s1));
		System.out.println(bloomFilter.contains(s2));
		System.out.println(bloomFilter.contains(s3));
		System.out.println(bloomFilter.contains(s4));

    }
}