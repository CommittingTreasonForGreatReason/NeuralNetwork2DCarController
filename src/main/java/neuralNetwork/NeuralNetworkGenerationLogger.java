package neuralNetwork;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public interface NeuralNetworkGenerationLogger {
    static final String directory = "src/main/resources/neuralNetworks/";
    static ArrayList<Double> fitnessValues= new ArrayList<Double>();
    static ArrayList<Double> averageFitnessValues= new ArrayList<Double>();
    
    public static void addFitnessValue(double fitnessValue) {
        fitnessValues.add(fitnessValue);
    }
    
//    public static void clearFitnessValues() {
//        fitnessValues.clear();
//    }
    
    public static ArrayList<Double> getFitnessValues() {
        return fitnessValues;
    }
    
    public static void addAverageFitnessValue(double averageFitnessValue) {
        averageFitnessValues.add(averageFitnessValue);
    }
    
    public static void clearAverageFitnessValues() {
        averageFitnessValues.clear();
    }
    
    public static ArrayList<Double> getAverageFitnessValues() {
        return averageFitnessValues;
    }
    
    public static void saveGenerationLogAsFile(String fileName, int amountOfDecimalPoints) {
        System.out.println("saving generation log...");
        try {
            // peak fitness
            File generationlogTextFile = new File(directory+fileName+"_log.txt");
            generationlogTextFile.createNewFile();
            PrintWriter pw = new PrintWriter(generationlogTextFile);
            
            double roundValue = 1;
            for(int i = 0;i<amountOfDecimalPoints;i++) {
                roundValue *= 10;
            }
            for(Double fitnessValue : fitnessValues) {
                pw.print(Math.round(fitnessValue*roundValue)/roundValue+" ");
            }
            pw.flush();
            pw.close();
            // average fitness
            File generationAveragelogTextFile = new File(directory+fileName+"_avg_log.txt");
            generationAveragelogTextFile.createNewFile();
            pw = new PrintWriter(generationAveragelogTextFile);

            for(Double averageFitnessValue : averageFitnessValues) {
                pw.print(Math.round(averageFitnessValue*roundValue)/roundValue+" ");
            }
            pw.flush();
            pw.close();
            System.out.println("saved generation log :)");
        }catch (Exception e) {

        }
        
    }
    
    public static void loadGenerationLog(String fileName) {
        fitnessValues.clear();
        System.out.println("loading generation log...");
        try {
            // peak fitness
            File generationlogTextFile = new File(directory+fileName+"_log.txt");
            Scanner scanner = new Scanner(generationlogTextFile);
            while(scanner.hasNext()) {
                fitnessValues.add(Double.parseDouble(scanner.next()));
            }
            scanner.close();
            // average fitness
            File generationAveragelogTextFile = new File(directory+fileName+"_avg_log.txt");
            scanner = new Scanner(generationAveragelogTextFile);
            while(scanner.hasNext()) {
                averageFitnessValues.add(Double.parseDouble(scanner.next()));
            }
            scanner.close();
            System.out.println("loaded generation log :)");
        }catch (Exception e) {

        }
        
    }
    
    public static void printFitness() {
        for(Double fitnessValue:fitnessValues) {
            System.out.println(fitnessValue);
        }
    }
}
