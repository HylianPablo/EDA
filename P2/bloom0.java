import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Collection;

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

        BloomFilter<String> bloomFilter = new BloomFilter<String>(10, 2);
        bloomFilter.add("sachin");
        bloomFilter.add("tendulkar");

        System.out.println(bloomFilter.contains("sachin"));
        System.out.println(bloomFilter.contains("tendulkar"));

        System.out.println(bloomFilter.contains("rahul"));
        System.out.println(bloomFilter.contains("dravid"));
    }
}