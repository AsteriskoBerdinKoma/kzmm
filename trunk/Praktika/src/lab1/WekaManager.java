package lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

public class WekaManager {

	public WekaManager() {

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
		Instances inputTrain;
		Instances outputTrain;
		Discretize filter;

		// load data (class attribute is assumed to be last attribute)
		inputTrain = load(allInputFilename);
		;

		// setup filter
		filter = new Discretize();
		filter.setInputFormat(inputTrain);

		// apply filter
		outputTrain = Filter.useFilter(inputTrain, filter);

		// save output
		save(outputTrain, allOutputFilename);
	}
	
	public void classify(String classifier, String[] options, Instances train, Instances test) throws Exception{
		//train classifier
		//Classifier cls = Classifier.forName(classifier, options);
		Classifier cls = new NaiveBayes();
		cls.buildClassifier(train);
		//evaluate classifier and print some statistics
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(cls, test);
		System.out.println(eval.toSummaryString("\nResults\n=======\n", false));
	}
}
