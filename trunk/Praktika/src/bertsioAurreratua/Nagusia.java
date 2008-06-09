package bertsioAurreratua;

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

	public void prozesatu(String trainPath, String testPath) throws Exception {
		// Aurretik existitzen diren ARFF fitxategiak ezabatu
		new File("TRAIN.arff").delete();
		new File("TEST.arff").delete();
		new File("ALL.arff").delete();
		new File("TRAIND.arff").delete();
		new File("TESTD.arff").delete();
		new File("ALLD.arff").delete();
		deleteDirectory(new File("irudiak"));

		// Entrenamendu eta Froga irudien katalogoa finkatu
		String pathTrainIrudiak = trainPath;
		String pathTestIrudiak = testPath;

		// Irudien iragazketa, Arff fitxategien sorrera eta Sailkatze prozesua
		// kontrolatzen duten objektuak hasieratu
		IrudiManager irudiKud = new IrudiManager(pathTrainIrudiak,
				pathTestIrudiak);
		ARFFManager arff = new ARFFManager();
		WekaManager wekaKud = new WekaManager();

		System.out.println("Emandako TR Irudiak bektorizatzen...\n");
		Vector<Irudia> vTrain = irudiKud.getTrainOriginalak();
		System.out.println("Emandako TS Irudiak bektorizatzen...\n");
		Vector<Irudia> vTest = irudiKud.getTestOriginalak();
		System.out.println("Emandako irudi guztiak bektorizatzen...\n");
		Vector<Irudia> vAll = irudiKud.getAllOriginalak();

		BufferedWriter bwEmaitza;
		BufferedWriter bwEmaitzaD;
		String emaitza = "";
		String emaitzaD = "";

		System.out.println("TRAIN.arff fitxategia sortzen...\n");
		arff.toARFF(vTrain, "TRAIN.arff");
		System.out.println("TEST.arff fitxategia sortzen...\n");
		arff.toARFF(vTest, "TEST.arff");
		System.out.println("ALL.arff fitxategia sortzen...\n");
		arff.toARFF(vAll, "ALL.arff");
		System.out.println("ALL.arff fitxategia diskretizatzen...\n");
		wekaKud.discretize("ALL.arff", "ALLD.arff");
		System.out.println("ALLD.arff fitxategia bitan banatzen...\n");
		arff.separateDiscretized("ALLD.arff", "TRAIND.arff", "TESTD.arff",
				new File(pathTrainIrudiak).list().length);

		System.out.println("DISKRETIZATU GABEKO SAILKAPENA...\n");
		emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
		bwEmaitza = new BufferedWriter(new FileWriter(
				"irud_orig-not_discretized.txt"));
		bwEmaitza
				.write("IRUDI ORIGINALAK - DISKRETIZATU GABE\n-------------------------------------\n\n");
		bwEmaitza.write(emaitza);
		bwEmaitza.close();

		System.out.println("DISKRETIZATUTAKO SAILKAPENA...\n");
		emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
		bwEmaitzaD = new BufferedWriter(new FileWriter(
				"irud_orig-discretized.txt"));
		bwEmaitzaD
				.write("IRUDI ORIGINALAK - DISKRETIZATUTA\n-------------------------------------\n\n");
		bwEmaitzaD.write(emaitzaD);
		bwEmaitzaD.close();

		File f1 = new File("TRAIN.arff");
		File f2 = new File("TEST.arff");
		File f3 = new File("ALL.arff");
		File f4 = new File("TRAIND.arff");
		File f5 = new File("TESTD.arff");
		File f6 = new File("ALLD.arff");

		System.runFinalization();

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
			arff.separateDiscretized("ALLD.arff", "TRAIND.arff", "TESTD.arff",
					new File(pathTrainIrudiak).list().length);

			String unekoFiltro = irudiKud.getUnekoFiltroa();

			emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
			bwEmaitza = new BufferedWriter(new FileWriter(unekoFiltro
					+ "-not_discretized.txt"));
			bwEmaitza
					.write(unekoFiltro
							+ " - DISKRETIZATU GABE\n-------------------------------------\n\n");
			bwEmaitza.write(irudiKud.getUnekoInfo() + "\n\n" + emaitza);
			bwEmaitza.write(emaitza);
			bwEmaitza.close();

			emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
			bwEmaitzaD = new BufferedWriter(new FileWriter(unekoFiltro
					+ "-discretized.txt"));
			bwEmaitzaD
					.write(unekoFiltro
							+ " - DISKRETIZATUTA\n-------------------------------------\n\n");
			bwEmaitzaD.write(irudiKud.getUnekoInfo() + "\n\n" + emaitza);
			bwEmaitzaD.write(emaitzaD);
			bwEmaitzaD.close();

			System.runFinalization();
		}

	}

	public static void main(String[] args) {
		try {
			System.setProperty("jmagick.systemclassloader", "no");

			String emaDir = "emaitzak";
			if (args.length > 2)
				emaDir = args[3];

			String ezDiskEmaitzenDir = emaDir + File.separator
					+ "ez_diskretizatuta";
			String diskEmaitzenDir = emaDir + File.separator + "diskretizatuta";

			// Aurretik existitzen diren ARFF fitxategiak ezabatu
			new File("TRAIN.arff").delete();
			new File("TEST.arff").delete();
			new File("ALL.arff").delete();
			new File("TRAIND.arff").delete();
			new File("TESTD.arff").delete();
			new File("ALLD.arff").delete();
			deleteDirectory(new File("irudiak"));

			if (args.length >= 2) {
				// Entrenamendu eta Froga irudien katalogoa finkatu
				String pathTrainIrudiak = args[0];
				String pathTestIrudiak = args[1];

				// Irudien iragazketa, Arff fitxategien sorrera eta Sailkatze
				// prozesua kontrolatzen duten objektuak hasieratu
				System.out.println("Programa hasieratzen...\n");
				IrudiManager irudiKud = new IrudiManager(pathTrainIrudiak,
						pathTestIrudiak);
				ARFFManager arff = new ARFFManager();
				WekaManager wekaKud = new WekaManager();

				System.out.println("Emandako TR Irudiak bektorizatzen...\n");
				Vector<Irudia> vTrain = irudiKud.getTrainOriginalak();
				System.out.println("Emandako TS Irudiak bektorizatzen...\n");
				Vector<Irudia> vTest = irudiKud.getTestOriginalak();
				System.out.println("Emandako irudi guztiak bektorizatzen...\n");
				Vector<Irudia> vAll = irudiKud.getAllOriginalak();

				BufferedWriter bwEmaitza;
				BufferedWriter bwEmaitzaD;
				String emaitza = "";
				String emaitzaD = "";

				System.out.println("TRAIN.arff fitxategia sortzen...\n");
				arff.toARFF(vTrain, "TRAIN.arff");
				System.out.println("TEST.arff fitxategia sortzen...\n");
				arff.toARFF(vTest, "TEST.arff");
				System.out.println("ALL.arff fitxategia sortzen...\n");
				arff.toARFF(vAll, "ALL.arff");
				System.out.println("ALL.arff fitxategia diskretizatzen...\n");
				wekaKud.discretize("ALL.arff", "ALLD.arff");
				System.out.println("ALLD.arff fitxategia bitan banatzen...\n");
				arff.separateDiscretized("ALLD.arff", "TRAIND.arff",
						"TESTD.arff", new File(pathTrainIrudiak).list().length);

				System.out.println("DISKRETIZATU GABEKO SAILKAPENA...\n");
				emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
				bwEmaitza = new BufferedWriter(new FileWriter(ezDiskEmaitzenDir
						+ File.separator + "irud_orig-not_discretized.txt"));
				bwEmaitza
						.write("IRUDI ORIGINALAK - DISKRETIZATU GABE\n-------------------------------------\n\n");
				bwEmaitza.write(emaitza);
				bwEmaitza.close();

				System.out.println("DISKRETIZATUTAKO SAILKAPENA...\n");
				emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
				bwEmaitzaD = new BufferedWriter(new FileWriter(diskEmaitzenDir
						+ File.separator + "irud_orig-discretized.txt"));
				bwEmaitzaD
						.write("IRUDI ORIGINALAK - DISKRETIZATUTA\n-------------------------------------\n\n");
				bwEmaitzaD.write(emaitzaD);
				bwEmaitzaD.close();

				File f1 = new File("TRAIN.arff");
				File f2 = new File("TEST.arff");
				File f3 = new File("ALL.arff");
				File f4 = new File("TRAIND.arff");
				File f5 = new File("TESTD.arff");
				File f6 = new File("ALLD.arff");

				System.runFinalization();

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
							"TESTD.arff",
							new File(pathTrainIrudiak).list().length);

					String unekoFiltro = irudiKud.getUnekoFiltroa();

					emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
					bwEmaitza = new BufferedWriter(new FileWriter(
							ezDiskEmaitzenDir + File.separator + unekoFiltro
									+ "-not_discretized.txt"));
					bwEmaitza
							.write(unekoFiltro
									+ " - DISKRETIZATU GABE\n-------------------------------------\n\n");
					bwEmaitza.write(irudiKud.getUnekoInfo() + "\n\n" + emaitza);
					bwEmaitza.write(emaitza);
					bwEmaitza.close();

					emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
					bwEmaitzaD = new BufferedWriter(new FileWriter(
							diskEmaitzenDir + File.separator + unekoFiltro
									+ "-discretized.txt"));
					bwEmaitzaD
							.write(unekoFiltro
									+ " - DISKRETIZATUTA\n-------------------------------------\n\n");
					bwEmaitzaD
							.write(irudiKud.getUnekoInfo() + "\n\n" + emaitza);
					bwEmaitzaD.write(emaitzaD);
					bwEmaitzaD.close();

					System.runFinalization();
				}
			} else
				System.out
						.println("Ez dituzu test eta train irudien fitxategien bidea sartu.\n"
								+ "Hau da modu egokia:\n"
								+ "\tjava -jar praktika.jar TrainIrudienBidea TestIrudienBidea [EmaitzakGordetzekoBidea]");

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
