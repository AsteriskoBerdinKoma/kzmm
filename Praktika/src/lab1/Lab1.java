package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import magick.ImageInfo;
import magick.MagickException;

import weka.core.Instances;

public class Lab1 {

	private String pathTest;

	private String pathTrain;

	public Lab1(String pathTest, String pathTrain) {
		this.pathTest = pathTest;
		this.pathTrain = pathTrain;
	}

	public void irudiakDeskonprimatu(String mota) throws IOException,
			IllegalMotaException {
		String[] fz = getFitxZerrenda(mota);
		Process p;
		for (int i = 0; i < fz.length; i++) {
			p = Runtime.getRuntime().exec(
					"convert " + fz[i] + " -compress none " + fz[i]);
			System.out.println(p.exitValue());
		}
	}

	public void irudiakTratatu(String mota) throws IOException,
			IllegalMotaException {
		String[] fz = getFitxZerrenda(mota);
		for (int i = 0; i < fz.length; i++)
			Runtime.getRuntime().exec(
					"convert " + fz[i] + " -compress none -edge 1 " + fz[i]);
	}

	public void makeARFF(String mota) throws IOException, IllegalMotaException, MagickException {
		String testOrTrain;
		if (mota.compareToIgnoreCase("test") == 0)
			testOrTrain = "TEST";
		else if (mota.compareToIgnoreCase("train") == 0)
			testOrTrain = "TRAIN";
		else if (mota.compareToIgnoreCase("all") == 0)
			testOrTrain = "ALL";
		else
			throw new IllegalMotaException();

		Vector<Irudia> vIrudiak = irudiakSortu(testOrTrain);

		//
		MagickManager mm = new MagickManager();
		System.out.println(1);
		mm.scale(vIrudiak);
		System.out.println(2);
		//
		
		int pixelKop = vIrudiak.firstElement().getPixelKop();

		FileWriter fo = new FileWriter(testOrTrain + ".arff");
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

	private Vector<Irudia> irudiakSortu(String mota) throws IOException,
			IllegalMotaException, MagickException {
		String[] fz = getFitxZerrenda(mota);
		String fitxDir;
		if (mota.compareToIgnoreCase("test") == 0)
			fitxDir = this.pathTest;
		else if (mota.compareToIgnoreCase("train") == 0)
			fitxDir = this.pathTrain;
		else if (mota.compareToIgnoreCase("all") == 0) {
			Vector<Irudia> vIrudiak = new Vector<Irudia>();
			fz = getFitxZerrenda("train");
			for (int i = 0; i < fz.length; i++) {
				String filePath = this.pathTrain + File.separatorChar + fz[i];
				vIrudiak.addElement(new Irudia(new ImageInfo(filePath)));
			}
			fz = getFitxZerrenda("test");
			for (int i = 0; i < fz.length; i++) {
				String filePath = this.pathTest + File.separatorChar + fz[i];
				vIrudiak.addElement(new Irudia(new ImageInfo(filePath)));
			}
			return vIrudiak;
		} else
			throw new IllegalMotaException();

		Vector<Irudia> vIrudiak = new Vector<Irudia>();
		for (int i = 0; i < fz.length; i++) {
			String filePath = fitxDir + File.separatorChar + fz[i];
			vIrudiak.addElement(new Irudia(new ImageInfo(filePath)));
		}
		return vIrudiak;
	}

	private String[] getFitxZerrenda(String mota) throws IllegalMotaException {
		if (mota.compareToIgnoreCase("test") == 0)
			return new File(pathTest).list();
		else if (mota.compareToIgnoreCase("train") == 0)
			return new File(pathTrain).list();
		else if (mota.compareToIgnoreCase("all") == 0)
			return null;
		else
			throw new IllegalMotaException();
	}

	public void separateDiscretized(String filePath) {
		try {
			FileInputStream fi = new FileInputStream(filePath);
			BufferedReader bri = new BufferedReader(new InputStreamReader(fi));

			FileWriter foTR = new FileWriter("TRAIND.arff");
			BufferedWriter bwTR = new BufferedWriter(foTR);

			FileWriter foTS = new FileWriter("TESTD.arff");
			BufferedWriter bwTS = new BufferedWriter(foTS);

			String lerroa;
			while (!(lerroa = bri.readLine()).startsWith("\'\\\'")) {
				bwTR.write(lerroa + "\n");
				bwTS.write(lerroa + "\n");
			}

			int trainKop = new File(pathTrain).list().length;

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

	public static void main(String args[]) {
		try {
			 System.setProperty("jmagick.systemclassloader","no");
			Lab1 l1 = new Lab1(args[0], args[1]);
			WekaManager wm = new WekaManager();
			l1.makeARFF("TEST");
			System.out.println("TEST.arff sortua");
			l1.makeARFF("TRAIN");
			System.out.println("TRAIN.arff sortu");
			l1.makeARFF("ALL");
			System.out.println("ALL.arff sortua");
			wm.discretize("ALL.arff", "ALLD.arff");
			System.out.println("ALLD.arff sortua");
			l1.separateDiscretized("ALLD.arff");
			System.out
					.println("TESTD.arff eta TRAIND.arff fitxategiak sortu dira");

			Instances train = new Instances(new BufferedReader(new FileReader(
					"TRAIND.arff")));
			Instances test = new Instances(new BufferedReader(new FileReader(
					"TESTD.arff")));

			train.setClassIndex(train.numAttributes() -1);
			test.setClassIndex(test.numAttributes() -1);
			
			wm.classify("NaiveBayes", null, train, test);
			
		} catch (IOException e) {
			// TODO Bloque catch generado automticamente
			e.printStackTrace();
		} catch (IllegalMotaException e) {
			// TODO Bloque catch generado automticamente
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
