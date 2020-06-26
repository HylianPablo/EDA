/**
*@author Martínez López, Pablo
*@author Prieto Tárrega, Hugo
*/

package eda.practica2;

public class XBloomFilter<E>{

	private static byte [] data=new byte[712000];
  private static int noHashes;
    
	public XBloomFilter(int noHashes) {	
    	this.noHashes = noHashes;
  	}

  public void add(String s) {
    int hc;
    for (int n = 0; n < noHashes; n++) {
      if(n%2==0){
        hc = hashC(s);
      }else{
        hc=hashJ(s);
      }
      int bitNo =  Math.abs(hc%(712000*8));
      setBit(data,bitNo);
    }
  }


  public void setBit(byte[] v, int i){v[i/8] |= 1 << (i%8);}
  public int getBit(byte[] v, int i){return (v[i/8] >> (i%8)) & 1;}

  public byte[] returnBytes(){
  	return data;
  }

  public void setData(byte[] datos){
  	data=datos;
  }

  public boolean contains(String s) {
    int hc;
    for (int n = 0; n < noHashes; n++) {
      if(n%2==0){
     	  hc = hashC(s);
      }else{
        hc = hashJ(s);
      }
      int bitNo = Math.abs(hc%(712000*8));
      if (getBit(data,bitNo)==0) return false;
    }
    return true;
  }
  
	public static int hashJ(String txt){
		int n = txt.length();
		int h = 0;
		for(int i = 0; i < n; i++){h=31*h+txt.charAt(i);}
		return h;
	}

  public static int hashC(String txt){
    int n = txt.length();
    int h1= 5381;
    for(int i=0;i<n;i+=2){h1=(33*h1)^txt.charAt(i);}
    int h2=5381;
    for(int i=1;i<n;i+=2){h2=(33*h2)^txt.charAt(i);}
    return h1+(h2*1566083941);
  }
}