package neuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public interface NeuralNetworkFileManager {
	
	static final String directory = "src/main/resources/neuralNetworks/";
	
	public static void saveNeuralNetworkAsFile(NeuralNetwork neuralNetwork, String fileName) {
	    System.out.println("saving neural network...");
		try {
			File neuralNetworkTextFile = new File(directory+fileName+".txt");
			// only creates a new File if the File does not exist !!
			neuralNetworkTextFile.createNewFile();
			PrintWriter pw = new PrintWriter(neuralNetworkTextFile);
			
			int nInputNodes = neuralNetwork.nInputNodes;
			int nHiddenNodes = neuralNetwork.nHiddenNodes;
			int nOutputNodes = neuralNetwork.nOutputNodes;
			int nHiddenLayers = neuralNetwork.nHiddenLayers;
			pw.println("InputNodes: "+nInputNodes+" HiddenNodes: "+nHiddenNodes+
					" OutputNodes: "+nOutputNodes+" HiddenLayers: "+nHiddenLayers);
			//--------------------------------------weights--------------------------------------//
			pw.println("--------------------------------------Weights--------------------------------------");
			// Hidden * Input
			pw.println("__________________________Format [Hidden * Input]:["+nHiddenNodes + " * " + nInputNodes+"]__________________________");
			for(int i = 0;i<nHiddenNodes;i++) {
				for(int j = 0;j<nInputNodes;j++) {
					pw.print(neuralNetwork.weightMatrixHH[0].matrix[i][j]+" ");
				}
				pw.println();
			}
			// Hidden * Hidden
			if(nHiddenLayers > 0) {
				 for(int k = 1;k<nHiddenLayers;k++) {
					 pw.println("__________________________Format [Hidden * Hidden]:["+nHiddenNodes + " * " + nHiddenNodes+"]__________________________");
					 for(int i = 0;i<nHiddenNodes;i++) {
						for(int j = 0;j<nHiddenNodes;j++) {
							pw.print(neuralNetwork.weightMatrixHH[k].matrix[i][j]+" ");	
						}
						pw.println();
					 }
				 }
			}
			// Output * Hidden
			pw.println("__________________________Format [Output * Hidden]:["+nOutputNodes + " * " + nHiddenNodes+"]__________________________");
			for(int i = 0;i<nOutputNodes;i++) {
				for(int j = 0;j<nHiddenNodes;j++) {
					pw.print(neuralNetwork.weightMatrixHH[nHiddenLayers].matrix[i][j]+" ");
				}
				pw.println();
			}
			//--------------------------------------bias--------------------------------------//
			pw.println("--------------------------------------Bias--------------------------------------");
			// Hidden * Input
			pw.println("__________________________Format [Hidden * 1]:["+nHiddenNodes + " * " + 1+"]__________________________");
				for(int i = 0;i<nHiddenNodes;i++) {
					pw.print(neuralNetwork.biasMatrixHH[0].matrix[i][0]+" ");
					pw.println();
				}
			// Hidden * Hidden
			if(nHiddenLayers > 0) {
				for(int k = 1;k<nHiddenLayers;k++) {
					pw.println("__________________________Format [Hidden * 1]:["+nHiddenNodes + " * " + 1+"]__________________________");
					for(int i = 0;i<nHiddenNodes;i++) {
						pw.print(neuralNetwork.biasMatrixHH[k].matrix[i][0]+" ");	
						pw.println();
					}
				}
			}
			// Output * Hidden
			pw.println("__________________________Format [Output * 1]:["+nOutputNodes + " * " + 1+"]__________________________");
			for(int i = 0;i<nOutputNodes;i++) {
				pw.print(neuralNetwork.biasMatrixHH[nHiddenLayers].matrix[i][0]+" ");
				if(i<nOutputNodes-1) {
					pw.println();
				}
			}
			pw.flush();
			pw.close();
			System.out.println("saved neural network :)");
		} catch (Exception e) {

		}
	}
	public static NeuralNetwork loadNeuralNetwork(String fileName) {
	    System.out.println("loading neural network...");
		try {
			File neuralNetworkTextFile = new File(directory+fileName+".txt");
			Scanner scanner = new Scanner(neuralNetworkTextFile);
			scanner.next();
			int nInputNodes = Integer.parseInt(scanner.next());
			scanner.next();
			int nHiddenNodes = Integer.parseInt(scanner.next());
			scanner.next();
			int nOutputNodes = Integer.parseInt(scanner.next());
			scanner.next();
			int nHiddenLayers = Integer.parseInt(scanner.next());
			scanner.nextLine();
			
			NeuralNetwork neuralNetwork = new NeuralNetwork(nInputNodes, nHiddenNodes, nOutputNodes, nHiddenLayers);
			//--------------------------------------weights--------------------------------------//
			scanner.nextLine();
			// Hidden * Input
			scanner.nextLine();
			for(int i = 0;i<nHiddenNodes;i++) {
				for(int j = 0;j<nInputNodes;j++) {
					float weightValue = Float.parseFloat(scanner.next());
					neuralNetwork.weightMatrixHH[0].matrix[i][j] = weightValue;
				}
				scanner.nextLine();
			}
			
			// Hidden * Hidden
			if(nHiddenLayers > 0) {
				 for(int k = 1;k<nHiddenLayers;k++) {
					 scanner.nextLine();
					 for(int i = 0;i<nHiddenNodes;i++) {
						for(int j = 0;j<nHiddenNodes;j++) {
							float weightValue = Float.parseFloat(scanner.next());
							neuralNetwork.weightMatrixHH[k].matrix[i][j] = weightValue;
						}
						scanner.nextLine();
					}
				 }
			}
			// Output * Hidden
			scanner.nextLine();
			for(int i = 0;i<nOutputNodes;i++) {
				for(int j = 0;j<nHiddenNodes;j++) {
					float weightValue = Float.parseFloat(scanner.next());
					neuralNetwork.weightMatrixHH[nHiddenLayers].matrix[i][j] = weightValue;
				}
				scanner.nextLine();
			}
			//--------------------------------------bias--------------------------------------//
			scanner.nextLine();
			// Hidden * 1
			scanner.nextLine();
			for(int i = 0;i<nHiddenNodes;i++) {
				float biasValue = Float.parseFloat(scanner.nextLine());
				neuralNetwork.biasMatrixHH[0].matrix[i][0] = biasValue;
			}
			// Hidden * 1
			if(nHiddenLayers > 0) {
				for(int k = 1;k<nHiddenLayers;k++) {
					scanner.nextLine();
					for(int i = 0;i<nHiddenNodes;i++) {
						float biasValue = Float.parseFloat(scanner.nextLine());
						neuralNetwork.biasMatrixHH[k].matrix[i][0] = biasValue;
					}
				}
			}
			// Output * 1
			scanner.nextLine();
			for(int i = 0;i<nOutputNodes;i++) {
				float biasValue = Float.parseFloat(scanner.nextLine());
				neuralNetwork.biasMatrixHH[nHiddenLayers].matrix[i][0] = biasValue;
			}
			scanner.close();
			System.out.println("loaded neural network :)");
			return neuralNetwork;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
