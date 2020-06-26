package primerapracticaeda;

class pruebas{
	
	
	public static void main(String []args){
		 int[]vector ={1,4,7,8,9,14,23,47,56,60,61,63,65,66,68,69,70,73,76,77,79,80,82};
		 int valorBuscado = 70;
		 System.out.println(busquedaBinaria(vector,valorBuscado));
	 }

	public static int busquedaBinaria(int  vector[], int dato){
		int n = vector.length;
		int centro,inf=0,sup=n-1;
		while(inf<=sup){
			centro=(sup+inf)/2;
			if(vector[centro]==dato) {
				return centro;
			}else if(dato < vector [centro] ){
				sup=centro-1;
			}else {
				inf=centro+1;
			}
		}
		return -1;
		}
	 
}
		
		
	
	

