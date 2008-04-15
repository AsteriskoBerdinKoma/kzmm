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
			String pathTrainIrudiak = args[0];
			String pathTestIrudiak = args[1];
			
			IrudiManager irudiKud = new IrudiManager(pathTrainIrudiak, pathTestIrudiak);
			ARFFManager arff = new ARFFManager();
			WekaManager wekaKud = new WekaManager();
			
			Vector<Irudia> vTrain = irudiKud.getTrainOriginalak();
			Vector<Irudia> vTest = irudiKud.getTestOriginalak();
			Vector<Irudia> vAll = irudiKud.getAllOriginalak();
			
			//ORIGINALEKIN {
			BufferedWriter bwTrain = new BufferedWriter(new FileWriter("TRAIN.arff"));
			bwTrain.write(arff.toARFF(vTrain));
			
			BufferedWriter bwTest = new BufferedWriter(new FileWriter("TEST.arff"));
			bwTest.write(arff.toARFF(vTest));
			
			String allD = wekaKud.discretize(arff.toARFF(vAll));			
			String[] trainDtestD = arff.separateDiscretizedData(allD, new File(pathTrainIrudiak).list().length);
			
			BufferedWriter bwTrainD = new BufferedWriter(new FileWriter("TRAIND.arff"));
			bwTrainD.write(trainDtestD[0]);
			
			BufferedWriter bwTestD = new BufferedWriter(new FileWriter("TESTD.arff"));
			bwTestD.write(trainDtestD[1]);
			
			wekaKud.classify("TRAIN.arff", "TEST.arff");
			
			wekaKud.classify("TRAIND.arff", "TESTD.arff");
			//}
			
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
