package lab1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import magick.MagickException;

public class Nagusia {

	public static void main(String[] args) {
		try {
			System.setProperty("jmagick.systemclassloader","no");
			
			String pathTrainIrudiak = args[0];
			String pathTestIrudiak = args[1];
			
			IrudiManager irudiKud = new IrudiManager(pathTrainIrudiak, pathTestIrudiak);
			ARFFManager arff = new ARFFManager();
			WekaManager wekaKud = new WekaManager();
			
			Vector<Irudia> vTrain = irudiKud.getTrainOriginalak();
			Vector<Irudia> vTest = irudiKud.getTestOriginalak();
			Vector<Irudia> vAll = irudiKud.getAllOriginalak();
			
			BufferedWriter bwEmaitza;
			BufferedWriter bwEmaitzaD;
			String emaitza = "";
			String emaitzaD = "";
			
			//ORIGINALEKIN
			BufferedWriter bwTrain = new BufferedWriter(new FileWriter("TRAIN.arff"));
			System.out.println("TRAIN\n-------");
			bwTrain.write(arff.toARFF(vTrain));
			
			BufferedWriter bwTest = new BufferedWriter(new FileWriter("TEST.arff"));
			System.out.println("-----------------------------------------\nTEST\n--------");
			bwTest.write(arff.toARFF(vTest));
			
			String allD = wekaKud.discretize(arff.toARFF(vAll));			
			String[] trainDtestD = arff.separateDiscretizedData(allD, new File(pathTrainIrudiak).list().length);
			
			BufferedWriter bwTrainD = new BufferedWriter(new FileWriter("TRAIND.arff"));
			bwTrainD.write(trainDtestD[0]);
			
			BufferedWriter bwTestD = new BufferedWriter(new FileWriter("TESTD.arff"));
			bwTestD.write(trainDtestD[1]);
			
			emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
			
			emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
			
			emaitza = "IRUDI ORIGINALAK - DISCRETIZATU GABE\n-------------------------------------\n\n" + emaitza;
			bwEmaitza = new BufferedWriter(new FileWriter("irud_orig-not_discretized.txt"));
			bwEmaitza.write(emaitza);
			bwEmaitza.close();
			
			emaitzaD = "IRUDI ORIGINALAK - DISCRETIZATUTA\n-------------------------------------\n\n" + emaitza;
			bwEmaitzaD = new BufferedWriter(new FileWriter("irud_orig-discretized.txt"));
			bwEmaitzaD.write(emaitzaD);
			bwEmaitzaD.close();
			
//			//FILTROEKIN
//			while (irudiKud.next()){
//				vTrain = irudiKud.getUnekoTrain();
//				vTest = irudiKud.getUnekoTest();
//				vAll = irudiKud.getUnekoAllIrudiak();
//				
//				bwTrain.write(arff.toARFF(vTrain));
//				bwTest.write(arff.toARFF(vTest));
//				
//				allD = wekaKud.discretize(arff.toARFF(vAll));			
//				trainDtestD = arff.separateDiscretizedData(allD, new File(pathTrainIrudiak).list().length);
//				
//				bwTrainD.write(trainDtestD[0]);
//				bwTestD.write(trainDtestD[1]);
//				
//				emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");				
//				emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
//				
//				String unekoFiltro = irudiKud.getUnekoFiltroa();
//				emaitza = unekoFiltro +" - DISCRETIZATU GABE\n-------------------------------------\n\n" + irudiKud.getUnekoInfo() + "\n\n" + emaitza;
//				bwEmaitza = new BufferedWriter(new FileWriter(unekoFiltro + "-not_discretized.txt"));
//				bwEmaitza.write(emaitza);
//				bwEmaitza.close();
//				
//				emaitzaD = unekoFiltro + " - DISCRETIZATUTA\n-------------------------------------\n\n" + irudiKud.getUnekoInfo() + "\n\n" + emaitza;
//				bwEmaitzaD = new BufferedWriter(new FileWriter(unekoFiltro + "-discretized.txt"));
//				bwEmaitzaD.write(emaitzaD);
//				bwEmaitzaD.close();
//			}

			bwTrain.close();
			bwTest.close();
			bwTrainD.close();
			bwTestD.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
