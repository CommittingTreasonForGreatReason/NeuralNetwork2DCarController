package neuralNetwork;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import vectors.Vector2;

public interface NeuralNetworkVisualizer {
    
    static final Color redColor = Color.RED;
    static final Color greenColor = Color.GREEN;    
    static final Font labelFont = new Font("Arial",20);   
    
    private static void drawBackGround(GraphicsContext gc,double w, double h) {
        gc.setFill(new Color(0.1, 0.1, 0.1, 0.95));
        gc.fillRect(0, 0, w, h);
        gc.setStroke(Color.ORANGERED);
        gc.setLineWidth(8);
        
        gc.strokeRect(0, 0, w, h);
        gc.setFill(Color.WHITE);
    }
    
    public static void visualizeNeuralNetwork(GraphicsContext gc, NeuralNetwork neuralNetwork, double w, double h) {
        visualizeNeuralNetwork("unnamed", null, null, gc, neuralNetwork, w, h);
    }
    
    public static void visualizeNeuralNetwork(String name, String inputLabels,String outputLabels,GraphicsContext gc, NeuralNetwork neuralNetwork, double w, double h) {
        int nHiddenLayers = neuralNetwork.nHiddenLayers;
        int nInputNodes = neuralNetwork.nInputNodes;
        int nHiddenNodes = neuralNetwork.nHiddenNodes;
        int nOutputNodes = neuralNetwork.nOutputNodes;
        Matrix[] weightMatrixHH = neuralNetwork.weightMatrixHH;
        
        drawBackGround(gc, w, h);
        gc.fillText(name, w/2, h/10);
        int border = 50;
        int amountOfLayersTotal = nHiddenLayers+2;
        
        double usedWidth = (w-border*2);
        double usedHeight = (h-border*2);
        double wUnit = (usedWidth)/((amountOfLayersTotal+1));
        
        double hUnitInput = (usedHeight)/(nInputNodes+1);
        double hUnitHidden = (usedHeight)/(nHiddenNodes+1);
        double hUnitOutput = (usedHeight)/(nOutputNodes+1);
        
        int neuronSize = 20;
        String[] inputLabelStrings = null;
        String[] outputLabelStrings = null;
        if(inputLabels != null) {
            inputLabelStrings = inputLabels.split(":");
        }
        if(outputLabels != null) {
            outputLabelStrings = outputLabels.split(":");
        }
        // actually starts drawing 
        for(int i = 1;i<amountOfLayersTotal+1;i++) {
            if(i == 1) {
                // draws the InputNeurons
                for(int j = 1;j<nInputNodes+1;j++) {
                    if(inputLabelStrings!=null) {
                        drawNeuronInput(gc, border+i*wUnit, border+j*hUnitInput, neuronSize,inputLabelStrings[j-1]);
                    }else {
                        drawNeuron(gc, border+i*wUnit, border+j*hUnitInput, neuronSize);
                    }
                    
                    for(int k = 1;k<nHiddenNodes+1;k++) {
                        gc.setStroke(weightMatrixHH[i-1].matrix[k-1][i-1]<0?redColor:greenColor);
                        gc.setLineWidth(3*Math.abs(weightMatrixHH[i-1].matrix[k-1][j-1])+0.1);
                        gc.strokeLine(border+(int)(i*wUnit), border+(int)(j*hUnitInput), border+(int)((i+1)*wUnit), border+(int)(k*hUnitHidden));
                    }
                }
                gc.setFill(Color.WHITE);
                gc.fillText(nInputNodes+"", border+wUnit-10, border+hUnitInput*(nInputNodes+1));
                
                
            }else if(i < amountOfLayersTotal){
                // draws the HiddenNeurons
                for(int j = 1;j<nHiddenNodes+1;j++) {
                    drawNeuron(gc, border+i*wUnit, border+j*hUnitHidden, neuronSize);
                    if(i < amountOfLayersTotal-1) {
                        for(int k = 1;k<nHiddenNodes+1;k++) {
                            gc.setStroke(weightMatrixHH[i-1].matrix[k-1][j-1]<0?redColor:greenColor);
                            gc.setLineWidth(3*Math.abs(weightMatrixHH[i-1].matrix[k-1][j-1])+0.1);
                            gc.strokeLine((int)(border+i*wUnit), (int)(border+j*hUnitHidden), (int)(border+(i+1)*wUnit), (int)(border+k*hUnitHidden));
                        }
                    }else {
                        for(int k = 1;k<nOutputNodes+1;k++) {
                            gc.setStroke(weightMatrixHH[i-1].matrix[k-1][j-1]<0?redColor:greenColor);
                            gc.setLineWidth(3*Math.abs(weightMatrixHH[i-1].matrix[k-1][j-1])+0.1);
                            gc.strokeLine((int)(border+i*wUnit), (int)(border+j*hUnitHidden), (int)(border+(i+1)*wUnit), (int)(border+k*hUnitOutput));
                        }
                    }
                }
                gc.setFill(Color.WHITE);
                gc.fillText(nHiddenNodes+"", border+wUnit*i-10, border+hUnitHidden*(nHiddenNodes+1));
            }else {
                // draws the OutputNeurons
                for(int j = 1;j<nOutputNodes+1;j++) {
                    if(outputLabelStrings!=null) {
                        drawNeuronOutput(gc, border+i*wUnit, border+j*hUnitOutput, neuronSize,outputLabelStrings[j-1]);
                    }else {
                        drawNeuron(gc, border+i*wUnit, border+j*hUnitInput, neuronSize);
                    }
                }
                gc.setFill(Color.WHITE);
                gc.fillText(nOutputNodes+"", border+wUnit*i-10, border+hUnitOutput*(nOutputNodes+1));
            }
        }
        
    }
    
    private static void drawNeuronInput(GraphicsContext gc,double x, double y, double size, String label) {
        drawNeuron(gc, x, y, size);
        gc.setFont(labelFont);
        gc.fillText(label, x-size*8, y+20/3);
    }
    
    private static void drawNeuronOutput(GraphicsContext gc,double x, double y, double size, String label) {
        drawNeuron(gc, x, y, size);
        gc.setFont(labelFont);
        gc.fillText(label, x+size, y+20/3);
    }
    
    private static void drawNeuron(GraphicsContext gc,double x, double y, double size) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x-size/2, y-size/2, size, size);
    }
    
    public static void visualizeGenerationLog(String name,ArrayList<Double> fitnessValues,GraphicsContext gc,double w, double h) {
        drawBackGround(gc, w, h);
        gc.fillText(name, w/2, h/10);
        
        int border = 100;
        double usedWidth = (w-border*2);
        double usedHeight = (h-border*2);
        
        int amountOfValues = fitnessValues.size();
        double wUnit = usedWidth/amountOfValues;
        double maxValue = 600;
        
        int amountOfYTicks = 10;
        double hYTick = usedHeight/amountOfYTicks;
        gc.setLineWidth(4);
        gc.setStroke(Color.WHITE);
        // stroke x-Axis
        gc.strokeLine(border, usedHeight+border, usedWidth+border, usedHeight+border);
        // stroke y-Axis
        gc.strokeLine(border, usedHeight+border, border, border);
        
        // stroke y-ticks
        gc.setLineWidth(2);
        gc.setFont(labelFont);
        gc.setFill(Color.WHITE);
        for(int i=0;i<amountOfYTicks+1;i++) {
            gc.strokeLine(border-10, hYTick*i+border, border+10, hYTick*i+border);
            gc.fillText(Math.round(maxValue-(hYTick*i)/usedHeight*maxValue)+"", border-60, hYTick*i+border+20/3);
        }
        gc.strokeLine(usedWidth+border, border+usedHeight-10, usedWidth+border, border+usedHeight+10);
        gc.fillText(amountOfValues+"", usedWidth+border, border+usedHeight+20);
        
        
        
        
        double[] xPoints = new double[amountOfValues+1];
        double[] yPoints = new double[amountOfValues+1];
        xPoints[0]=border;
        yPoints[0]=border+usedHeight;
        for(int i=0;i<amountOfValues;i++) {
            xPoints[i+1] = border+wUnit*(i+1);
            yPoints[i+1] = border+usedHeight-fitnessValues.get(i)/maxValue*usedHeight;
        }
        gc.setStroke(Color.ORANGERED);
        gc.strokePolyline(xPoints, yPoints, amountOfValues+1);
    }
}
