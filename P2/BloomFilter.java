import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Collection;
import java.io.*;
import java.util.*;
import java.lang.*;

public class BloomFilter<E> {

    private final BitSet bitSet;
    private final int hashFunctionCount;
    private final MessageDigest md5Digest;

    public BloomFilter(int bitSetSize, int expectedNumberOfElements) throws NoSuchAlgorithmException {
        bitSet = new BitSet(bitSetSize);
        hashFunctionCount =  (int) Math.round((bitSetSize / (double)expectedNumberOfElements) * Math.log(2.0));
        md5Digest = java.security.MessageDigest.getInstance("MD5");
    }

    private int[] createHashes(byte[] data) {
        int[] result = new int[hashFunctionCount];

        int k = 0;
        byte salt = 0;
        while (k < hashFunctionCount) {
            byte[] digest;
            synchronized (md5Digest) {
                md5Digest.update(salt);
                salt++;
                digest = md5Digest.digest(data);                
            }
            for (int i = 0; i < digest.length/4 && k < hashFunctionCount; i++) {
                int h = 0;
                // 4 bits are consumed for a single hash.
                for (int j = (i*4); j < (i*4)+4; j++) {
                    h <<= 8;
                    h |= ((int) digest[j]) & 0xFF;
                }
                result[k] = h;
                k++;
            }
        }
        return result;
    }

    public static int hash(String txt){
		int n = txt.length();
		int h = 0;
		for(int i = 0; i < n; i++){h=31*h+txt.charAt(i);}
		return h;
	}

    private int getBitIndex(int hash) {
        return Math.abs(hash % bitSet.size());
    }

    public void addAll(Collection<? extends E> c) {
        for (E element : c)
        	add(element);
    }

    public void add(E element) {
    	add(element.toString().getBytes());
    }

    private void add(byte[] bytes) {
      int[] hashes = createHashes(bytes);
      for (int hash : hashes)
        bitSet.set(getBitIndex(hash), true);
    }

    public boolean containsAll(Collection<? extends E> c) {
    	for (E element : c)
        	if (!contains(element))
            	return false;
        return true;
    }

     public boolean contains(E element) {
     	return contains(element.toString().getBytes());
    }

     private boolean contains(byte[] bytes) {
        for (int hash : createHashes(bytes)) {
            if (!bitSet.get(getBitIndex(hash))) {
                return false;
            }
        }
        return true;
    }

     public void clear() {
        bitSet.clear();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        BloomFilter<String> bloomFilter = new BloomFilter<String>(712000, 15165);


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
				bloomFilter.add(strLine);
				scan1.nextLine();
			}

		}catch(IOException e){
			e.printStackTrace();
		}

        System.out.println(bloomFilter.contains("http://www.FZ.g0v/hJTx7/al3/UI"));
        System.out.println(bloomFilter.contains("www.google.com"));
        System.out.println(bloomFilter.contains("http://www.MGWialEUdP.0rg"));
        System.out.println(bloomFilter.contains("ftp.uwu.org"));
    }
}