
	/*try(Stream<String> stream = Files.lines(Paths.get(datt))){
							stream.filter(line->!line.trim().equals(strLine));
						}catch(IOException e){
							System.out.println("Error");
						}*/



public static void newFich(int ini, int alfa){
	int alfa=0; //Tiene que llegar como argumento y devolverse para el siguiente
	try{
		long tope = 1035000; //Tamaño provisional
		long topeL= tope/50; //Maximo de lineas que puede tener un fichero

		long NumArchivos = countL/topeL;
		NumArchivos= NumArchivos+1000;

		

		String datt= "/home/pablo/EDA/fichEDA/"+ alfa + ".txt";
		File fichOK = new File(datt);

		FileWriter fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ alfa+ ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
		BufferedWriter out = new BufferedWriter(fw);

		//int j=1000;j<NumArchivos;j++

		for(int j=1000;j<NumArchivos;j++){
			for(int i=ini;i<topeL;i++){

				int chars ;
				int minLA =101; //Cota por arriba, minL es la cota por abajo

				FileInputStream fis = new FileInputStream(fichOK);
				DataInputStream in = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;

				strLineP=br.readLine();
				chars = strLineP.length();
				if(chars<minLA){
					minLA=chars;
					strLine=strLineP;
				}

			}
			if(fichOK.length()<tope){
				out.write(strLine);

				try(Stream<String> stream = Files.lines(Paths.get(datt))){
					stream.filter(line->!line.trim().equals(strLine));
				}catch(IOException e){
					System.out.println("Error");
				}

			}else{
				alfa++;
				fw = new FileWriter("/home/pablo/EDA/fichEDA/"+ alfa+ ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
				out = new BufferedWriter(fw);
				datt= "/home/pablo/EDA/fichEDA/"+ alfa + ".txt";
				fichOK = new File(datt);
			}
			
		}
	}
	catch(IOException e){
		System.out.println("Error");
	}finally{

	}
	return alfa;

}