package oinarrizkoBertsioa;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class IrudiManager {

	public static String PATH_TRAIN;
	public static String PATH_TEST;

	private Vector<Irudia> vTrain;
	private Vector<Irudia> vTest;

//	public IrudiManager(String pathTrain, String pathTest) throws IOException {
//		IrudiManager.PATH_TRAIN = pathTrain;
//		IrudiManager.PATH_TEST = pathTest;
//	}
	
	public IrudiManager(){
		
	}

	private Vector<Irudia> irudiakKargatu(String path) throws IOException {
		String[] fz = new File(path).list();

		Vector<Irudia> vIrudiak = new Vector<Irudia>();
		for (int i = 0; i < fz.length; i++) {
			String filePath = path + File.separatorChar + fz[i];
			vIrudiak.addElement(new Irudia(new File(filePath)));
		}
		return vIrudiak;
	}

	public Vector<Irudia> getTrain() throws IOException {
		if (vTrain == null)
			vTrain = irudiakKargatu(PATH_TRAIN);
		return vTrain;
	}

	public Vector<Irudia> getTest() throws IOException {
		if (vTest == null)
			vTest = irudiakKargatu(PATH_TEST);
		return vTest;
	}

	public Vector<Irudia> getAll() throws IOException {
		if (vTrain == null)
			vTrain = irudiakKargatu(PATH_TRAIN);
		if (vTest == null)
			vTest = irudiakKargatu(PATH_TEST);

		Vector<Irudia> emaitza = new Vector<Irudia>(vTrain);
		emaitza.addAll(vTest);
		return emaitza;
	}
	
	public void setTrainPath(String path){
		PATH_TRAIN = path;
	}
	
	public void setTestPath(String path){
		PATH_TEST = path;
	}
}
