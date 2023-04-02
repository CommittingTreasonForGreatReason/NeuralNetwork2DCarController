package neuralNetwork;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface NeuralNetworkVisualizer {
    
    static final Color redColor = Color.RED;
    static final Color greenColor = Color.GREEN;    
    
    public static void visualizeNeuralNetwork(GraphicsContext gc, NeuralNetwork neuralNetwork, double w, double h) {
        int nHiddenLayers = neuralNetwork.nHiddenLayers;
        int nInputNodes = neuralNetwork.nInputNodes;
        int nHiddenNodes = neuralNetwork.nHiddenNodes;
        int nOutputNodes = neuralNetwork.nOutputNodes;
        Matrix[] weightMatrixHH = neuralNetwork.weightMatrixHH;
        
        gc.setFill(new Color(0.1, 0.1, 0.1, 0.95));
        gc.fillRect(0, 0, w, h);
        gc.setStroke(Color.ORANGERED);
        gc.setLineWidth(8);
        
        gc.strokeRect(0, 0, w, h);
        int border = 50;
        int amountOfLayersTotal = nHiddenLayers+2;
        
        double usedWidth = (w-border*2);
        double usedHeight = (h-border*2);
        double wUnit = (usedWidth)/((amountOfLayersTotal+1));
        
        double hUnitInput = (usedHeight)/(nInputNodes+1);
        double hUnitHidden = (usedHeight)/(nHiddenNodes+1);
        double hUnitOutput = (usedHeight)/(nOutputNodes+1);
        
        int neuronSize = 20;
        
        // actually starts drawing 
        for(int i = 1;i<amountOfLayersTotal+1;i++) {
            if(i == 1) {
                // draws the InputNeurons
                for(int j = 1;j<nInputNodes+1;j++) {
                    drawNeuron(gc, border+i*wUnit, border+j*hUnitInput, neuronSize);
                    for(int k = 1;k<nHiddenNodes+1;k++) {
                        gc.setStroke(weightMatrixHH[i-1].matrix[k-1][i-1]<0?redColor:greenColor);
                        gc.setLineWidth(Math.abs(weightMatrixHH[i-1].matrix[k-1][j-1])+0.1);
                        gc.strokeLine(border+(int)(i*wUnit), border+(int)(j*hUnitInput), border+(int)((i+1)*wUnit), border+(int)(k*hUnitHidden));
                    }
                }
                
                
            }else if(i < amountOfLayersTotal){
                // draws the HiddenNeurons
                for(int j = 1;j<nHiddenNodes+1;j++) {
                    drawNeuron(gc, border+i*wUnit, border+j*hUnitHidden, neuronSize);
                    if(i < amountOfLayersTotal-1) {
                        for(int k = 1;k<nHiddenNodes+1;k++) {
                            gc.setStroke(weightMatrixHH[i-1].matrix[k-1][j-1]<0?redColor:greenColor);
                            gc.setLineWidth(Math.abs(weightMatrixHH[i-1].matrix[k-1][j-1])+0.1);
                            gc.strokeLine((int)(border+i*wUnit), (int)(border+j*hUnitHidden), (int)(border+(i+1)*wUnit), (int)(border+k*hUnitHidden));
                        }
                    }else {
                        for(int k = 1;k<nOutputNodes+1;k++) {
                            gc.setStroke(weightMatrixHH[i-1].matrix[k-1][j-1]<0?redColor:greenColor);
                            gc.setLineWidth(Math.abs(weightMatrixHH[i-1].matrix[k-1][j-1])+0.1);
                            gc.strokeLine((int)(border+i*wUnit), (int)(border+j*hUnitHidden), (int)(border+(i+1)*wUnit), (int)(border+k*hUnitOutput));
                        }
                    }
                }
            }else {
                // draws the OutputNeurons
                for(int j = 1;j<nOutputNodes+1;j++) {
                    drawNeuron(gc, border+i*wUnit, border+j*hUnitOutput, neuronSize);
                }
            }
        }
    }
    
    private static void drawNeuron(GraphicsContext gc,double x, double y, double size) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x-size/2, y-size/2, size, size);
    }
}
