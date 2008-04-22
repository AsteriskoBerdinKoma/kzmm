package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

public class WekaManager {
	
	Vector<String> sailkatzaileak;

	public WekaManager() {
		sailkatzaileak = new Vector<String>();

	}

	protected static Instances load(String filename) throws Exception {
		Instances result;
		BufferedReader reader;

		reader = new BufferedReader(new FileReader(filename));
		result = new Instances(reader);
		result.setClassIndex(result.numAttributes() - 1);
		reader.close();

		return result;
	}
	
	private Instances loadData(String data) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(data));
		return new Instances(reader);
	}

	/**
	 * Saves the data to the specified file
	 * 
	 * @param data
	 *            the data to save to a file
	 * @param filename
	 *            the file to save the data to
	 * @throws Exception
	 *             if something goes wrong
	 */
	protected static void save(Instances data, String filename)
			throws Exception {
		BufferedWriter writer;

		writer = new BufferedWriter(new FileWriter(filename));
		writer.write(data.toString());
		writer.newLine();
		writer.flush();
		writer.close();
	}

	public void discretize(String trainInputFilename, String testInputFilename,
			String trainOutputFilename, String testOutputFilename)
			throws Exception {
		Instances inputTrain;
		Instances inputTest;
		Instances outputTrain;
		Instances outputTest;
		Discretize filter;

		// load data (class attribute is assumed to be last attribute)
		inputTrain = load(trainInputFilename);
		inputTest = load(testInputFilename);

		// setup filter
		filter = new Discretize();
		filter.setInputFormat(inputTrain);

		// apply filter
		outputTrain = Filter.useFilter(inputTrain, filter);
		outputTest = Filter.useFilter(inputTest, filter);

		// save output
		save(outputTrain, trainOutputFilename);
		save(outputTest, testOutputFilename);
	}

	public void discretize(String allInputFilename, String allOutputFilename)
			throws Exception {
		Instances inputAll;
		Instances outputAll;
		Discretize filter;

		// load data (class attribute is assumed to be last attribute)
		inputAll = load(allInputFilename);
		;

		// setup filter
		filter = new Discretize();
		filter.setInputFormat(inputAll);

		// apply filter
		outputAll = Filter.useFilter(inputAll, filter);

		// save output
		save(outputAll, allOutputFilename);
	}

	public String discretize(String data) throws Exception {
		Instances input;
		Instances output;
		Discretize filter;

		// load data (class attribute is assumed to be last attribute)
		input = loadData(data);
		;

		// setup filter
		filter = new Discretize();
		filter.setInputFormat(input);

		// apply filter
		output = Filter.useFilter(input, filter);
		return output.toString();
	}
	
	public void discretizeUnsupervised(String allInputFilename, String allOutputFilename) throws Exception {
		Instances inputAll;
		Instances outputAll;
		weka.filters.unsupervised.attribute.Discretize filter;

		// load data (class attribute is assumed to be last attribute)
		inputAll = load(allInputFilename);

		// setup filter
		filter = new weka.filters.unsupervised.attribute.Discretize();
		filter.setInputFormat(inputAll);
		filter.setBins(4); //Balio posibleak: 4, 6, 10 eta 20

		// apply filter
		outputAll = Filter.useFilter(inputAll, filter);

		// save output
		save(outputAll, allOutputFilename);
	}

	public void classify(String classifier, String[] options, Instances train,
			Instances test) throws Exception {
		// train classifier
		// Classifier cls = Classifier.forName(classifier, options);
		Classifier cls = new NaiveBayes();
		cls.buildClassifier(train);
		// evaluate classifier and print some statistics
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(cls, test);
		System.out.println(eval.toSummaryString("\nResults\n=======\n", false));
	}

	public String classify(String trainFile, String testFile) throws FileNotFoundException, IOException {
		/*
		 * Sarrerako ARFF fitxategiek beharrezkoa duten preprozesamendu
		 * tratamendua eginda edukiko dute metodo hau deitzeko unean.
		 */
		
		String emaitzak = "";
		
		//Entrenamenduko fitxategiaren instantzia
		Instances trainDB = new Instances(new BufferedReader(new FileReader(
				trainFile)));
		trainDB.setClassIndex(trainDB.numAttributes() - 1);
		
		for (String sailka : sailkatzaileak) {
			try {
				String sailkIzena = sailka;
				String[] sailkAukerak = null;
				Classifier sailk = Classifier.forName(sailkIzena,sailkAukerak);
				emaitzak +="SAILKATZAILEA: " + sailka;
				emaitzak +="-------------------------------------------------------------\n";

				//These classifiers take too much time to classify, so we skip them
				if (sailka.equals("weka.classifiers.functions.MultilayerPerceptron") || 
					sailka.equals("weka.classifiers.trees.NBTree") ||
					sailka.equals("weka.classifiers.lazy.LBR") ||
					sailka.equals("weka.classifiers.trees.UserClassifier")) {
					// ...UserClassifier: prompts a panel to define the desired tree
					// ...LBR: Takes too much time, but only with discretized ARFF files
				} else {	
					try {
						sailk.buildClassifier(trainDB);
						Instances testDB = new Instances(new BufferedReader(new FileReader(testFile)));
						testDB.setClassIndex(testDB.numAttributes() - 1);
						
						Evaluation sailkatu = new Evaluation(trainDB);
						sailkatu.evaluateModel(sailk, testDB);
						emaitzak +=sailkatu.toSummaryString("\nResults\n=======\n", false);

					} catch (Exception e) {
						System.out.println("Errorea: ARFF desegokia!\n");
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				System.out.println("Errorea: Ez da sailkatzailea!\n");
				e.printStackTrace();
				
			}
		}
		return emaitzak;

	}
	
	public void getSailkatzaileZerrenda() throws IOException{
		Vector<String> s = new Vector<String>();
		JarFile weka = new JarFile("./lib/weka.jar");
		Enumeration<JarEntry> classes = weka.entries();
		while (classes.hasMoreElements()) {
			JarEntry elementua = classes.nextElement();
			if (elementua.getName().startsWith("weka/classifiers/")
					&& elementua.getName().endsWith(".class")) {
				if (elementua.getName().lastIndexOf("/") > 16) {
					String path = elementua.getName();
					String path2 = path.replace("/", ".");
					String path3 = path2.substring(0, path2.length() - 6);
					if (path3.indexOf("$") != -1) {
						if (!s.contains(path3.substring(0, path3.indexOf("$"))))
							s.add(path3.substring(0, path3.indexOf("$")));
					} else {
						if (!s.contains(path3))
							s.add(path3);
					}
				}
			}
		}
		for (String sailka : s) {
			try {
				String sailkIzena = sailka;
				String[] sailkAukerak = null;
				Classifier.forName(sailkIzena,sailkAukerak);
				sailkatzaileak.add(sailkIzena);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			
		}
	}
}
