package lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

public class Adibidea {

	public static void main(String[] args) throws Exception {

		// Entrenamenduko fitxategiaren instantzia
		String train = "TRAIN.arff";
		Instances trainDB = new Instances(new BufferedReader(new FileReader(
				train)));
		trainDB.setClassIndex(trainDB.numAttributes() - 1);

		// Filtroaren sorkuntza eta aplikazioa
		// String[] opcionesFiltro = new String[1];
		// opcionesFiltro[0] = "-R first-last";
		// Discretize discretizeFilter = new Discretize();
		// discretizeFilter.setOptions(opcionesFiltro);
		// discretizeFilter.setAttributeIndices("first-last");
		// discretizeFilter.setInvertSelection(false);
		// discretizeFilter.setMakeBinary(false);
		// discretizeFilter.setUseBetterEncoding(false);
		// discretizeFilter.setUseKononenko(false);
		// discretizeFilter.setInputFormat(trainDB);
		//
		// // Instantzia filtratua sortzen da
		// Instances trainDBdiscretized = Filter.useFilter(trainDB,
		// discretizeFilter);
		// System.out.println(trainDBdiscretized.toString());

		// // Instantzia ikasteko
		// String sailkIzena = "weka.classifiers.bayes.NaiveBayes";
		// String[] sailkAukerak = null;
		// Classifier sailk = Classifier.forName(sailkIzena, sailkAukerak);
		// //sailk.buildClassifier(trainDBdiscretized);
		// sailk.buildClassifier(trainDB);
		//
		// // Testerako datubasea finkatu (Ez dago diskretizatuta horrela ezin
		// da
		// // egin!!)
		// String test = "TEST.arff";
		// Instances testDB = new Instances(new BufferedReader(
		// new FileReader(test)));
		// testDB.setClassIndex(testDB.numAttributes() - 1);
		//
		// //
		// //Evaluation sailkatu = new Evaluation(trainDBdiscretized);
		// Evaluation sailkatu = new Evaluation(trainDB);
		// sailkatu.evaluateModel(sailk, testDB); // sailkatu testeko DBarekin
		// // emaitzen laburpen bat atera
		// System.out.println(sailkatu.toSummaryString("\nResults\n=======\n",
		// false));
		// System.out.println(sailkatu.pctCorrect()); // porcentaje bien
		// // clasificado
		// System.out.println(sailkatu.pctIncorrect()); // porcentaje mal
		// // clasificado

		Vector<String> sailkatzaileak = new Vector<String>();
		JarFile weka = new JarFile("./lib/weka.jar");
		Enumeration<JarEntry> classes = weka.entries();
		System.out.println();
		System.out.println("Sailkatzaileen zerrenda:\n");
		while (classes.hasMoreElements()) {
			JarEntry elementua = classes.nextElement();
			if (elementua.getName().startsWith("weka/classifiers/")
					&& elementua.getName().endsWith(".class")) {
				if (elementua.getName().lastIndexOf("/") > 16) {
					String path = elementua.getName();
					String path2 = path.replace("/", ".");
					String path3 = path2.substring(0, path2.length() - 6);
					if (path3.indexOf("$") != -1) {
						if (!sailkatzaileak.contains(path3.substring(0, path3
								.indexOf("$"))))
							sailkatzaileak.add(path3.substring(0, path3
									.indexOf("$")));
					} else {
						if (!sailkatzaileak.contains(path3))
							sailkatzaileak.add(path3);
					}
				}
			}
		}
		for (String sailka : sailkatzaileak) {
			System.out.println(sailka);

//			 // Instantzia ikasteko
//			 String sailkIzena = sailka;
//			 String[] sailkAukerak = null;
//			 if (Classifier.forName(sailkIzena, sailkAukerak) != null) {
//			 Classifier sailk = Classifier.forName(sailkIzena, sailkAukerak);
//			 // sailk.buildClassifier(trainDBdiscretized);
//			 sailk.buildClassifier(trainDB);
//			
//			 // Testerako datubasea finkatu (Ez dago diskretizatuta horrela
//			 // ezin da
//			 // egin!!)
//			 String test = "TEST.arff";
//			 Instances testDB = new Instances(new BufferedReader(
//			 new FileReader(test)));
//			 testDB.setClassIndex(testDB.numAttributes() - 1);
//			
//			 //
//			 // Evaluation sailkatu = new Evaluation(trainDBdiscretized);
//			 Evaluation sailkatu = new Evaluation(trainDB);
//			 sailkatu.evaluateModel(sailk, testDB); // sailkatu testeko
//			 // DBarekin
//			 // emaitzen laburpen bat atera
//			 System.out.println(sailkatu.toSummaryString(
//			 "\nResults\n=======\n", false));
//			 System.out.println(sailkatu.pctCorrect()); // porcentaje bien
//			 // clasificado
//			 System.out.println(sailkatu.pctIncorrect()); // porcentaje
//			 // mal
//			 // clasificado
//			 }

		}
	}
}
