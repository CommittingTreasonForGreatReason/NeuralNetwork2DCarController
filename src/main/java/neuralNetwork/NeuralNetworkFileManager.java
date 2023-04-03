package neuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public interface NeuralNetworkFileManager {
	
	static final String directory = "src/main/resources/";
	
	public static void saveNeuralNetworkAsFile(NeuralNetwork neuralNetwork, String fileName, int amountOfDecimalPoints) {
		try {
			File neuralNetworkTextFile = new File(directory+fileName+".txt");
			// only creates a new File if the File does not exist !!
			neuralNetworkTextFile.createNewFile();
			PrintWriter pw = new PrintWriter(neuralNetworkTextFile);
			
			int nInputNodes = neuralNetwork.nInputNodes;
			int nHiddenNodes = neuralNetwork.nHiddenNodes;
			int nOutputNodes = neuralNetwork.nOutputNodes;
			int nHiddenLayers = neuralNetwork.nHiddenLayers;
			float roundValue = 1;
			for(int i = 0;i<amountOfDecimalPoints;i++) {
				roundValue *= 10;
			}
			pw.println("InputNodes: "+nInputNodes+" HiddenNodes: "+nHiddenNodes+
					" OutputNodes: "+nOutputNodes+" HiddenLayers: "+nHiddenLayers);
			//--------------------------------------weights--------------------------------------//
			pw.println("--------------------------------------Weights--------------------------------------");
			// Hidden * Input
			pw.println("__________________________Format [Hidden * Input]:["+nHiddenNodes + " * " + nInputNodes+"]__________________________");
			for(int i = 0;i<nHiddenNodes;i++) {
				for(int j = 0;j<nInputNodes;j++) {
					pw.print(Math.round(neuralNetwork.weightMatrixHH[0].matrix[i][j]*roundValue)/roundValue+" ");
				}
				pw.println();
			}
			// Hidden * Hidden
			if(nHiddenLayers > 0) {
				 for(int k = 1;k<nHiddenLayers;k++) {
					 pw.println("__________________________Format [Hidden * Hidden]:["+nHiddenNodes + " * " + nHiddenNodes+"]__________________________");
					 for(int i = 0;i<nHiddenNodes;i++) {
						for(int j = 0;j<nHiddenNodes;j++) {
							pw.print(Math.round(neuralNetwork.weightMatrixHH[k].matrix[i][j]*roundValue)/roundValue+" ");	
						}
						pw.println();
					 }
				 }
			}
			// Output * Hidden
			pw.println("__________________________Format [Output * Hidden]:["+nOutputNodes + " * " + nHiddenNodes+"]__________________________");
			for(int i = 0;i<nOutputNodes;i++) {
				for(int j = 0;j<nHiddenNodes;j++) {
					pw.print(Math.round(neuralNetwork.weightMatrixHH[nHiddenLayers].matrix[i][j]*roundValue)/roundValue+" ");
				}
				pw.println();
			}
			//--------------------------------------bias--------------------------------------//
			pw.println("--------------------------------------Bias--------------------------------------");
			// Hidden * Input
			pw.println("__________________________Format [Hidden * 1]:["+nHiddenNodes + " * " + 1+"]__________________________");
				for(int i = 0;i<nHiddenNodes;i++) {
					pw.print(Math.round(neuralNetwork.biasMatrixHH[0].matrix[i][0]*roundValue)/roundValue+" ");
					pw.println();
				}
			// Hidden * Hidden
			if(nHiddenLayers > 0) {
				for(int k = 1;k<nHiddenLayers;k++) {
					pw.println("__________________________Format [Hidden * 1]:["+nHiddenNodes + " * " + 1+"]__________________________");
					for(int i = 0;i<nHiddenNodes;i++) {
						pw.print(Math.round(neuralNetwork.biasMatrixHH[k].matrix[i][0]*roundValue)/roundValue+" ");	
						pw.println();
					}
				}
			}
			// Output * Hidden
			pw.println("__________________________Format [Output * 1]:["+nOutputNodes + " * " + 1+"]__________________________");
			for(int i = 0;i<nOutputNodes;i++) {
				pw.print(Math.round(neuralNetwork.biasMatrixHH[nHiddenLayers].matrix[i][0]*roundValue)/roundValue+" ");
				if(i<nOutputNodes-1) {
					pw.println();
				}
			}
			pw.flush();
			pw.close();
			
		} catch (Exception e) {

		}
	}
	public static NeuralNetwork readNeuralNetworkFromFile(String fileName) {
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
					System.out.print(weightValue +" ");
					neuralNetwork.weightMatrixHH[0].matrix[i][j] = weightValue;
				}
				scanner.nextLine();
				System.out.println();
			}
			
			// Hidden * Hidden
			if(nHiddenLayers > 0) {
				 for(int k = 1;k<nHiddenLayers;k++) {
					 scanner.nextLine();
					 System.out.println();
					 for(int i = 0;i<nHiddenNodes;i++) {
						for(int j = 0;j<nHiddenNodes;j++) {
							float weightValue = Float.parseFloat(scanner.next());
							System.out.print(weightValue +" ");
							neuralNetwork.weightMatrixHH[k].matrix[i][j] = weightValue;
						}
						scanner.nextLine();
						System.out.println();
					}
				 }
			}
			// Output * Hidden
			scanner.nextLine();
			System.out.println();
			for(int i = 0;i<nOutputNodes;i++) {
				for(int j = 0;j<nHiddenNodes;j++) {
					float weightValue = Float.parseFloat(scanner.next());
					System.out.print(weightValue +" ");
					neuralNetwork.weightMatrixHH[nHiddenLayers].matrix[i][j] = weightValue;
				}
				scanner.nextLine();
				System.out.println();
			}
			//--------------------------------------bias--------------------------------------//
			scanner.nextLine();
			System.out.println();
			// Hidden * 1
			scanner.nextLine();
			for(int i = 0;i<nHiddenNodes;i++) {
				float biasValue = Float.parseFloat(scanner.nextLine());
				System.out.println(biasValue +" ");
				neuralNetwork.biasMatrixHH[0].matrix[i][0] = biasValue;
			}
			System.out.println();
			// Hidden * 1
			if(nHiddenLayers > 0) {
				for(int k = 1;k<nHiddenLayers;k++) {
					scanner.nextLine();
					for(int i = 0;i<nHiddenNodes;i++) {
						float biasValue = Float.parseFloat(scanner.nextLine());
						System.out.println(biasValue +" ");
						neuralNetwork.biasMatrixHH[k].matrix[i][0] = biasValue;
					}
					System.out.println();
				}
			}
			System.out.println();
			// Output * 1
			scanner.nextLine();
			for(int i = 0;i<nOutputNodes;i++) {
				float biasValue = Float.parseFloat(scanner.nextLine());
				System.out.println(biasValue +" ");
				neuralNetwork.biasMatrixHH[nHiddenLayers].matrix[i][0] = biasValue;
			}
			scanner.close();
			return neuralNetwork;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
