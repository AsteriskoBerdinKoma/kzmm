package lab1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import magick.MagickException;

public class Nagusia {

	private static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	public static void main(String[] args) {
		try {
			System.setProperty("jmagick.systemclassloader", "no");

			new File("TRAIN.arff").delete();
			new File("TEST.arff").delete();
			new File("ALL.arff").delete();
			new File("TRAIND.arff").delete();
			new File("TESTD.arff").delete();
			new File("ALLD.arff").delete();			
			deleteDirectory(new File("irudiak"));
			
			String pathTrainIrudiak = args[0];
			String pathTestIrudiak = args[1];

			IrudiManager irudiKud = new IrudiManager(pathTrainIrudiak,
					pathTestIrudiak);
			ARFFManager arff = new ARFFManager();
			WekaManager wekaKud = new WekaManager();

			Vector<Irudia> vTrain = irudiKud.getTrainOriginalak();
			Vector<Irudia> vTest = irudiKud.getTestOriginalak();
			Vector<Irudia> vAll = irudiKud.getAllOriginalak();

			BufferedWriter bwEmaitza;
			BufferedWriter bwEmaitzaD;
			String emaitza = "";
			String emaitzaD = "";

			// ORIGINALEKIN
			// BufferedWriter bwTrain = new BufferedWriter(new
			// FileWriter("TRAIN.arff"));
			// System.out.println("TRAIN\n-------");
			// bwTrain.write(arff.toARFF(vTrain));
			arff.toARFF(vTrain, "TRAIN.arff");

			// BufferedWriter bwTest = new BufferedWriter(new
			// FileWriter("TEST.arff"));
			// System.out.println("-----------------------------------------\nTEST\n--------");
			// bwTest.write(arff.toARFF(vTest));
			arff.toARFF(vTest, "TEST.arff");

			arff.toARFF(vAll, "ALL.arff");
			wekaKud.discretize("ALL.arff", "ALLD.arff");
			arff.separateDiscretized("ALLD.arff", "TRAIND.arff", "TESTD.arff",
					new File(pathTrainIrudiak).list().length);

			// BufferedWriter bwTrainD = new BufferedWriter(new FileWriter(
			// "TRAIND.arff"));
			// bwTrainD.write(trainDtestD[0]);
			//
			// BufferedWriter bwTestD = new BufferedWriter(new FileWriter(
			// "TESTD.arff"));
			// bwTestD.write(trainDtestD[1]);

			 emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
			 bwEmaitza = new BufferedWriter(new FileWriter("irud_orig-not_discretized.txt"));
			 bwEmaitza.write("IRUDI ORIGINALAK - DISCRETIZATU GABE\n-------------------------------------\n\n");
			 bwEmaitza.write(emaitza);
			 bwEmaitza.close();

			// emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
			// bwEmaitzaD = new BufferedWriter(new FileWriter(
			// "irud_orig-discretized.txt"));
			// bwEmaitzaD.write("IRUDI ORIGINALAK -
			// DISCRETIZATUTA\n-------------------------------------\n\n");
			// bwEmaitzaD.write(emaitzaD);
			// bwEmaitzaD.close();

			File f1 = new File("TRAIN.arff");
			File f2 = new File("TEST.arff");
			File f3 = new File("ALL.arff");
			File f4 = new File("TRAIND.arff");
			File f5 = new File("TESTD.arff");
			File f6 = new File("ALLD.arff");
			
			// FILTROEKIN
			while (irudiKud.next()) {	
				f1.delete();
				f2.delete();
				f3.delete();
				f4.delete();
				f5.delete();
				f6.delete();
				
				vTrain = irudiKud.getUnekoTrain();
				vTest = irudiKud.getUnekoTest();
				vAll = irudiKud.getUnekoAllIrudiak();

				arff.toARFF(vTrain, "TRAIN.arff");
				arff.toARFF(vTest, "TEST.arff");

				arff.toARFF(vAll, "ALL.arff");
				wekaKud.discretize("ALL.arff", "ALLD.arff");
				arff.separateDiscretized("ALLD.arff", "TRAIND.arff",
						"TESTD.arff", new File(pathTrainIrudiak).list().length);

				String unekoFiltro = irudiKud.getUnekoFiltroa();

				emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
				bwEmaitza = new BufferedWriter(new FileWriter(unekoFiltro
						+ "-not_discretized.txt"));
				bwEmaitza
						.write(unekoFiltro
								+ " - DISCRETIZATU GABE\n-------------------------------------\n\n");
				bwEmaitza.write(irudiKud.getUnekoInfo() + "\n\n" + emaitza);
				bwEmaitza.write(emaitza);
				bwEmaitza.close();

//				emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
//				bwEmaitzaD = new BufferedWriter(new FileWriter(unekoFiltro
//						+ "-discretized.txt"));
//				bwEmaitzaD
//						.write(unekoFiltro
//								+ " - DISCRETIZATUTA\n-------------------------------------\n\n");
//				bwEmaitzaD.write(irudiKud.getUnekoInfo() + "\n\n" + emaitza);
//				bwEmaitzaD.write(emaitzaD);
//				bwEmaitzaD.close();
			}

			// bwTrain.close();
			// bwTest.close();
			// bwTrainD.close();
			// bwTestD.close();

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
