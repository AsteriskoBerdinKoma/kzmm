package oinarrizkoBertsioa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Vector;

public class ARFFManager {

	public ARFFManager() {

	}

	public String toARFF(Vector<Irudia> vIrudiak) {
		int pixelKop = vIrudiak.firstElement().getPixelKop();

		 String arff = "@RELATION KZMM\n";
		for (int i = 1; i <= pixelKop; i++)
			arff += "@ATTRIBUTE P" + i + " NUMERIC\n";
		arff += "@ATTRIBUTE KLASEA {BAI, EZ}\n";
		arff += "@DATA\n";

		for (Irudia irudi : vIrudiak) {
			// System.out.println(irudi.getFitxIzen());
			if (irudi.getPixelKop() == pixelKop)
				arff += irudi.getDatuak() + "\n";
		}

		return arff;
	}

	public void toARFF(Vector<Irudia> vIrudiak, String filename) throws IOException, IllegalMotaException{

		int pixelKop = vIrudiak.firstElement().getPixelKop();

		FileWriter fo = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fo);

		bw.write("@RELATION KZMM\n");
		for (int i = 1; i <= pixelKop; i++)
			bw.write("@ATTRIBUTE P" + i + " NUMERIC\n");
		bw.write("@ATTRIBUTE KLASEA {BAI, EZ}\n");
		bw.write("@DATA\n");

		for (Irudia irudi : vIrudiak) {
			if (irudi.getPixelKop() == pixelKop)
				bw.write(irudi.getDatuak() + "\n");
		}

		bw.close();
		fo.close();
	}

	/**
	 * @param filePath
	 *            All diskretizatuaren fitxategia
	 * @param trainKop
	 *            Zenbat train argazki dauden
	 * @return Train eta Test arff-ak diskretizatuta. 0 posizioan TrainD dago
	 *         eta 1 posizioan TestD.
	 * @throws IOException
	 */
	public String[] separateDiscretized(String filePath, int trainKop)
			throws IOException {
		FileInputStream fi = new FileInputStream(filePath);
		BufferedReader bri = new BufferedReader(new InputStreamReader(fi));

		String train = "", test = "";

		String lerroa;
		while (!(lerroa = bri.readLine()).startsWith("\'\\\'")) {
			train += lerroa + "\n";
			test += lerroa + "\n";
		}

		for (int i = 0; i < trainKop; i++) {
			train += lerroa + "\n";
			lerroa = bri.readLine();
		}

		while (lerroa != null) {
			test += lerroa + "\n";
			lerroa = bri.readLine();
		}
		return new String[] { train, test };
	}
	
	public void separateDiscretized(String filePath, String trainPath, String testPath, int trainKop) {
		try {
			FileInputStream fi = new FileInputStream(filePath);
			BufferedReader bri = new BufferedReader(new InputStreamReader(fi));

			FileWriter foTR = new FileWriter(trainPath);
			BufferedWriter bwTR = new BufferedWriter(foTR);

			FileWriter foTS = new FileWriter(testPath);
			BufferedWriter bwTS = new BufferedWriter(foTS);

			String lerroa;
			while (!(lerroa = bri.readLine()).startsWith("\'\\\'")) {
				bwTR.write(lerroa + "\n");
				bwTS.write(lerroa + "\n");
			}

			for (int i = 0; i < trainKop; i++) {
				bwTR.write(lerroa + "\n");
				lerroa = bri.readLine();
			}

			bwTR.close();
			foTR.close();

			while (lerroa != null) {
				bwTS.write(lerroa + "\n");
				lerroa = bri.readLine();
			}

			bwTS.close();
			foTS.close();

			bri.close();
			fi.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param filePath
	 * @param trainKop
	 * @return Train eta Test arff-ak diskretizatuta. 0 posizioan TrainD dago
	 *         eta 1 posizioan TestD.
	 * @throws IOException
	 */
	public String[] separateDiscretizedData(String data, int trainKop)
			throws IOException {
		BufferedReader bri = new BufferedReader(new StringReader(data));

		String train = "", test = "";

		String lerroa;
		while (!(lerroa = bri.readLine()).startsWith("\'\\\'")) {
			train += lerroa + "\n";
			test += lerroa + "\n";
		}

		for (int i = 0; i < trainKop; i++) {
			train += lerroa + "\n";
			lerroa = bri.readLine();
		}

		while (lerroa != null) {
			test += lerroa + "\n";
			lerroa = bri.readLine();
		}
		return new String[] { train, test };
	}
}
