public static void split(String fichOK){
		try{
			/*Scanner scan = new Scanner(new File(fichOK));
			int count=0;
			while(scan.hasNextLine()){
				scan.nextLine();
				count++;
			}*/

			long tope = 1047000;

			File file = new File(fichOK);
			FileReader fx = new FileReader(file);
			LineNumberReader lnr = new LineNumberReader(fx);
			long countL=0;
			while(lnr.readLine ()!= null){
				countL ++;
			}
			lnr.close();
			System.out.println("Número de líneas: "+countL);

			long topeL= tope/countL;

;			File fichTAM = new File(fichOK);
			System.out.println(fichTAM.length());

			long NumArchivos = fichTAM.length()/tope;
			System.out.println(NumArchivos);


			/*System.out.println("Lineas del fichero: " + count);
			double DivLineas = count/2;

			double temp = count/DivLineas;
			int temp1 = (int) temp;

			int NumArchivos=0;

			if(temp1 == temp){
				NumArchivos = temp1;
			}else{
				NumArchivos= temp1 + 1;
			}*/
			FileInputStream fis = new FileInputStream(fichOK);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			for(int j = 1; j<=NumArchivos; j++){
				FileWriter fw = new FileWriter("/home/pablo/EDA/"+ j + ".txt"); //EN ESTA PARTE HAY QUE CAMBIAR LA RUTA EN CADA EQUIPO
				BufferedWriter out = new BufferedWriter(fw);

				for(int i = 1; i <=topeL; i++){
					strLine = br.readLine();
					if(strLine != null){
						out.write(strLine);
						if( i != topeL){
							out.newLine();
						}
					}
				}
				out.close();
			}
			in.close();
			for(int k=0;k<NumArchivos;k++){
				compTAM("/home/pablo/EDA"+k+".txt");

			}
		}catch(IOException e){
			System.out.println("Error");
		}
	}


