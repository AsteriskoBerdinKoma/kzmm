package oinarrizkoBertsioa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;

public class WekaManager {

	public WekaManager() throws IOException {
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

		// setup filter
		filter = new Discretize();
		filter.setInputFormat(input);

		// apply filter
		output = Filter.useFilter(input, filter);
		return output.toString();
	}
	
	public void discretizeUnsupervised(String allInputFilename, String allOutputFilename, int param) throws Exception {
		Instances inputAll;
		Instances outputAll;
		weka.filters.unsupervised.attribute.Discretize filter;

		// load data (class attribute is assumed to be last attribute)
		inputAll = load(allInputFilename);

		// setup filter
		filter = new weka.filters.unsupervised.attribute.Discretize();
		filter.setInputFormat(inputAll);
		filter.setBins(param); //Balio posibleak: 4, 6, 10 eta 20

		// apply filter
		outputAll = Filter.useFilter(inputAll, filter);

		// save output
		save(outputAll, allOutputFilename);
	}
}
