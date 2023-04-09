package neuralNetwork;

public class NeuralNetwork {	
	
	int nInputNodes;
	int nHiddenNodes;
	int nOutputNodes;
	int nHiddenLayers;
	
	Matrix[] weightMatrixHH;
	Matrix[] biasMatrixHH;
	
	private static final double lr = 0.01;

	public NeuralNetwork(int nInputNodes, int nHiddenNodes, int nOutputNodes, int nHiddenLayers) {
		this.nInputNodes = nInputNodes;
		this.nHiddenNodes = nHiddenNodes;
		this.nOutputNodes = nOutputNodes;
		this.nHiddenLayers = nHiddenLayers;
		
		weightMatrixHH = new Matrix[nHiddenLayers+1];
		biasMatrixHH = new Matrix[nHiddenLayers+1];
			
		// initializing all Input-Hidden Weights
		weightMatrixHH[0] = new Matrix(nHiddenNodes, nInputNodes); 
		weightMatrixHH[0].randomize();
		// initializing all Input-Hidden Biases
		biasMatrixHH[0] = new Matrix(nHiddenNodes, 1); 
		biasMatrixHH[0].randomize();
		
		if(nHiddenLayers > 0) {
			 for(int i = 1;i<nHiddenLayers;i++) {
				 // initializing all Hidden-Hidden Weights
				 weightMatrixHH[i] = new Matrix(nHiddenNodes, nHiddenNodes);
				 weightMatrixHH[i].randomize();
				 // initializing all Hidden-Hidden Biases 
				 biasMatrixHH[i] = new Matrix(nHiddenNodes, 1);
				 biasMatrixHH[i].randomize();
			 }
		}
		// initializing all Hidden-Output Weights
		weightMatrixHH[nHiddenLayers] = new  Matrix(nOutputNodes,nHiddenNodes); 
		weightMatrixHH[nHiddenLayers].randomize();
		// initializing all Hidden-Output Biases
		biasMatrixHH[nHiddenLayers] = new Matrix(nOutputNodes, 1);
		biasMatrixHH[nHiddenLayers].randomize();
	}
	
	public int getnInputNodes() {
		return nInputNodes;
	}
	
	public NeuralNetwork getCopyNeuralNetwork() {
		NeuralNetwork copyOfNeuralNetwork =  new NeuralNetwork(nInputNodes, nHiddenNodes, nOutputNodes, nHiddenLayers);
		for(int index = 0;index<weightMatrixHH.length;index++) {
			for(int i = 0;i<weightMatrixHH[index].rows;i++) {
				for(int j = 0;j<weightMatrixHH[index].colums;j++) {
					copyOfNeuralNetwork.weightMatrixHH[index].matrix[i][j] = weightMatrixHH[index].matrix[i][j];
				}
				copyOfNeuralNetwork.biasMatrixHH[index].matrix[i][0] = biasMatrixHH[index].matrix[i][0];
			}
		}
		return copyOfNeuralNetwork;
	}
	
	
	public Matrix feedForward(Matrix inputMatrix) {
		Matrix[] hiddenMatrixes= new Matrix[nHiddenLayers];
		
		Matrix hiddenMatrixNoSigmoid = Matrix.matrixProdukt(weightMatrixHH[0],inputMatrix);
		hiddenMatrixNoSigmoid.addMatrix(biasMatrixHH[0]);
		hiddenMatrixes[0] = sigmoidMatrix(hiddenMatrixNoSigmoid);
		
		for(int i = 1;i<nHiddenLayers;i++) {
			hiddenMatrixNoSigmoid = Matrix.matrixProdukt(weightMatrixHH[i],hiddenMatrixes[i-1]);
			hiddenMatrixNoSigmoid.addMatrix(biasMatrixHH[i]);
			hiddenMatrixes[i] = sigmoidMatrix(hiddenMatrixNoSigmoid);
		}
		
		Matrix outputMatrixNoSigmoid = Matrix.matrixProdukt(weightMatrixHH[nHiddenLayers],hiddenMatrixes[nHiddenLayers-1]);
		outputMatrixNoSigmoid.addMatrix(biasMatrixHH[nHiddenLayers]);
		Matrix outputMatrix = sigmoidMatrix(outputMatrixNoSigmoid);
		// returns the last outputMatrix
		return outputMatrix;
	}
	
	private static double sigmoidOne(double x) {
		return 1/(1 + Math.exp(-x));
	}
	
	private static Matrix sigmoidMatrix(Matrix m) {
		for(int i = 0;i<m.rows;i++) {
			for(int j = 0;j<m.colums;j++) {
				m.matrix[i][j] = sigmoidOne(m.matrix[i][j]);
			}
		}
		return m;
	}
	
	public void mutate(double mutationMagnitude) {
		for(int index = 0;index<weightMatrixHH.length;index++) {
			for(int i = 0;i<weightMatrixHH[index].rows;i++) {
				for(int j = 0;j<weightMatrixHH[index].colums;j++) {
					weightMatrixHH[index].matrix[i][j] += (Math.random()-0.5) * mutationMagnitude;
				}
				biasMatrixHH[index].matrix[i][0] += (Math.random()-0.5) * mutationMagnitude;
			}
		}
	}
	
	
}
