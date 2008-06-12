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

	public static void main(String[] args) {
		try {
			System.setProperty("jmagick.systemclassloader", "no");

			String noDiskFiltroa = "";
			String noDiskSailk = "";
			double noDiskAsmatzea = -1;

			String diskFiltroa = "";
			String diskSailk = "";
			double diskAsmatzea = -1;

			String uDiskFiltroa = "";
			String uDiskSailk = "";
			double uDiskAsmatzea = -1;

			String emaDir = "emaitzak";
			if (args.length > 2)
				emaDir = args[2];

			String ezDiskEmaitzenDir = emaDir + File.separator
					+ "ez_diskretizatuta";
			String diskEmaitzenDir = emaDir + File.separator + "diskretizatuta";
			String uDiskEmaitzenDir = emaDir + File.separator
					+ "gainbGabe_Diskretizatuta";
			new File(ezDiskEmaitzenDir).mkdirs();
			new File(diskEmaitzenDir).mkdirs();
			new File(uDiskEmaitzenDir).mkdirs();

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
				BufferedWriter bwEmaitzaUD;
				String emaitza = "";
				String emaitzaD = "";
				String emaitzaUD = "";

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
				System.out
						.println("ALL.arff fitxategia gainbegiratu gabe diskretizatzen...\n");
				wekaKud.discretizeUnsupervised("ALL.arff", "ALLUD.arff");
				System.out.println("ALLUD.arff fitxategia bitan banatzen...\n");
				arff
						.separateDiscretized("ALLUD.arff", "TRAINUD.arff",
								"TESTUD.arff", new File(pathTrainIrudiak)
										.list().length);

				System.out.println("DISKRETIZATU GABEKO SAILKAPENA...\n");
				emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
				bwEmaitza = new BufferedWriter(new FileWriter(ezDiskEmaitzenDir
						+ File.separator + "irud_orig-not_discretized.txt"));
				bwEmaitza
						.write("IRUDI ORIGINALAK - DISKRETIZATU GABE\n-------------------------------------\n\n");
				bwEmaitza.write(emaitza);
				bwEmaitza.close();

				if (wekaKud.getAsmatzea() > noDiskAsmatzea) {
					noDiskAsmatzea = wekaKud.getAsmatzea();
					noDiskSailk = wekaKud.getSailkatzailea();
					noDiskFiltroa = "Filtrorik gabe";
				}

				System.out.println("DISKRETIZATUTAKO SAILKAPENA...\n");
				emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
				bwEmaitzaD = new BufferedWriter(new FileWriter(diskEmaitzenDir
						+ File.separator + "irud_orig-discretized.txt"));
				bwEmaitzaD
						.write("IRUDI ORIGINALAK - DISKRETIZATUTA\n-------------------------------------\n\n");
				bwEmaitzaD.write(emaitzaD);
				bwEmaitzaD.close();

				if (wekaKud.getAsmatzea() > noDiskAsmatzea) {
					diskAsmatzea = wekaKud.getAsmatzea();
					diskSailk = wekaKud.getSailkatzailea();
					diskFiltroa = "Filtrorik gabe";
				}

				System.out
						.println("GAINBEGIRATU GABEKO DISKRETIZATUTAKO SAILKAPENA...\n");
				emaitzaUD = wekaKud.classify("TRAINUD.arff", "TESTUD.arff");
				bwEmaitzaUD = new BufferedWriter(new FileWriter(
						uDiskEmaitzenDir + File.separator
								+ "irud_orig-unsupervised_discretized.txt"));
				bwEmaitzaUD
						.write("IRUDI ORIGINALAK - GAINBEGIRATU GABE DISKRETIZATUTA\n-------------------------------------\n\n");
				bwEmaitzaUD.write(emaitzaUD);
				bwEmaitzaUD.close();

				if (wekaKud.getAsmatzea() > noDiskAsmatzea) {
					uDiskAsmatzea = wekaKud.getAsmatzea();
					uDiskSailk = wekaKud.getSailkatzailea();
					uDiskFiltroa = "Filtrorik gabe";
				}

				File f1 = new File("TRAIN.arff");
				File f2 = new File("TEST.arff");
				File f3 = new File("ALL.arff");
				File f4 = new File("TRAIND.arff");
				File f5 = new File("TESTD.arff");
				File f6 = new File("ALLD.arff");
				File f7 = new File("TRAINUD.arff");
				File f8 = new File("TESTUD.arff");
				File f9 = new File("ALLUD.arff");

				System.runFinalization();

				// FILTROEKIN
				while (irudiKud.next()) {
					f1.delete();
					f2.delete();
					f3.delete();
					f4.delete();
					f5.delete();
					f6.delete();
					f7.delete();
					f8.delete();
					f9.delete();

					System.out.println("Uneko filtroa: "
							+ irudiKud.getUnekoFiltroa() + "\n");

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

					arff.toARFF(vAll, "ALL.arff");
					wekaKud.discretizeUnsupervised("ALL.arff", "ALLUD.arff");
					arff.separateDiscretized("ALLUD.arff", "TRAINUD.arff",
							"TESTUD.arff",
							new File(pathTrainIrudiak).list().length);

					String unekoFiltro = irudiKud.getUnekoFiltroa();

					System.out.println("DISKRETIZATU GABEKO SAILKAPENA...\n");
					emaitza = wekaKud.classify("TRAIN.arff", "TEST.arff");
					bwEmaitza = new BufferedWriter(new FileWriter(
							ezDiskEmaitzenDir + File.separator + unekoFiltro
									+ "-not_discretized.txt"));
					bwEmaitza
							.write(unekoFiltro
									+ " - DISKRETIZATU GABE\n-------------------------------------\n\n");
					bwEmaitza.write(irudiKud.getUnekoInfo() + "\n\n" + emaitza);
					// bwEmaitza.write(emaitza);
					bwEmaitza.close();

					if (wekaKud.getAsmatzea() > noDiskAsmatzea) {
						noDiskAsmatzea = wekaKud.getAsmatzea();
						noDiskSailk = wekaKud.getSailkatzailea();
						noDiskFiltroa = irudiKud.getUnekoInfo();
					}

					System.out.println("DISKRETIZATUTAKO SAILKAPENA...\n");
					emaitzaD = wekaKud.classify("TRAIND.arff", "TESTD.arff");
					bwEmaitzaD = new BufferedWriter(new FileWriter(
							diskEmaitzenDir + File.separator + unekoFiltro
									+ "-discretized.txt"));
					bwEmaitzaD
							.write(unekoFiltro
									+ " - DISKRETIZATUTA\n-------------------------------------\n\n");
					bwEmaitzaD.write(irudiKud.getUnekoInfo() + "\n\n"
							+ emaitzaD);
					// bwEmaitzaD.write(emaitzaD);
					bwEmaitzaD.close();

					if (wekaKud.getAsmatzea() > diskAsmatzea) {
						diskAsmatzea = wekaKud.getAsmatzea();
						diskSailk = wekaKud.getSailkatzailea();
						diskFiltroa = irudiKud.getUnekoInfo();
					}

					System.out
							.println("GAINBEGIRATU GABEKO DISKRETIZATUTAKO SAILKAPENA...\n");
					emaitzaUD = wekaKud.classify("TRAINUD.arff", "TESTUD.arff");
					bwEmaitzaUD = new BufferedWriter(new FileWriter(
							uDiskEmaitzenDir + File.separator + unekoFiltro
									+ "-unsupervised_discretized.txt"));
					bwEmaitzaUD
							.write(unekoFiltro
									+ " - GAINBEGIRATU GABE DISKRETIZATUTA\n-------------------------------------\n\n");
					bwEmaitzaUD.write(irudiKud.getUnekoInfo() + "\n\n"
							+ emaitzaUD);
					// bwEmaitzaUD.write(emaitzaUD);
					bwEmaitzaUD.close();

					if (wekaKud.getAsmatzea() > uDiskAsmatzea) {
						uDiskAsmatzea = wekaKud.getAsmatzea();
						uDiskSailk = wekaKud.getSailkatzailea();
						uDiskFiltroa = irudiKud.getUnekoInfo();
					}

					System.runFinalization();
				}

				f1.delete();
				f2.delete();
				f3.delete();
				f4.delete();
				f5.delete();
				f6.delete();
				f7.delete();
				f8.delete();
				f9.delete();

				deleteDirectory(new File("irudiak"));

				System.out.println("Bukatua. Hauek izan dira emaitzik onenak:");
				System.out.println("Diskretizatu gabe:\n"
						+ "------------------");
				System.out.println("\tSailkatzailea: " + noDiskSailk);
				System.out.println("\t" + noDiskFiltroa);
				System.out.println("\tAsmatzea: %" + noDiskAsmatzea + "\n");

				System.out.println("Diskretizatuta:\n" + "---------------");
				System.out.println("\tSailkatzailea: " + diskSailk);
				System.out.println("\t" + diskFiltroa);
				System.out.println("\tAsmatzea: %" + diskAsmatzea + "\n");

				System.out.println("Gainbegiratu gabe diskretizatuta:\n"
						+ "---------------------------------");
				System.out.println("\tSailkatzailea: " + uDiskSailk);
				System.out.println("\t" + uDiskFiltroa);
				System.out.println("\tAsmatzea: %" + uDiskAsmatzea + "\n");
			} else
				System.out
						.println("Ez dituzu test eta train irudien fitxategien bidea sartu.\n"
								+ "Hau da modu egokia:\n"
								+ "\tjava -jar praktika.jar TrainIrudienBidea TestIrudienBidea [EmaitzakGordetzekoBidea]");
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
