package lab1;

import java.io.BufferedReader;
import java.io.FileReader;

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
		String[] opcionesFiltro = new String[1];
		opcionesFiltro[0] = "-R first-last";
		Discretize discretizeFilter = new Discretize();
		discretizeFilter.setOptions(opcionesFiltro);
		discretizeFilter.setAttributeIndices("first-last");
		discretizeFilter.setInvertSelection(false);
		discretizeFilter.setMakeBinary(false);
		discretizeFilter.setUseBetterEncoding(false);
		discretizeFilter.setUseKononenko(false);
		discretizeFilter.setInputFormat(trainDB);

		// Instantzia filtratua sortzen da
		Instances trainDBdiscretized = Filter.useFilter(trainDB,
				discretizeFilter);
		System.out.println(trainDBdiscretized.toString());

		// Instantzia ikasteko
		String sailkIzena = "weka.classifiers.bayes.NaiveBayes";
		String[] sailkAukerak = null;
		Classifier sailk = Classifier.forName(sailkIzena, sailkAukerak);
		sailk.buildClassifier(trainDBdiscretized);

		// Testerako datubasea finkatu (Ez dago diskretizatuta horrela ezin da
		// egin!!)
		String test = "TEST.arff";
		Instances testDB = new Instances(new BufferedReader(
				new FileReader(test)));
		testDB.setClassIndex(testDB.numAttributes() - 1);

		//
		Evaluation sailkatu = new Evaluation(trainDBdiscretized);
		sailkatu.evaluateModel(sailk, testDB); // sailkatu testeko DBarekin
		// emaitzen laburpen bat atera
		System.out.println(sailkatu.toSummaryString("\nResults\n=======\n",
				false));
		System.out.println(sailkatu.pctCorrect()); // porcentaje bien
		// clasificado
		System.out.println(sailkatu.pctIncorrect()); // porcentaje mal
		// clasificado
	}
}
