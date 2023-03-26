package fileAndData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import drawables.GoalLine;
import drawables.Grid;
import drawables.GridCell;
import drawables.RaceTrack;

public interface MapFileManager {
    
    static final String directory = "src/main/resources/";
    
    public static void saveMap(RaceTrack racetrack, String fileName) {
        System.out.println("saving map...");
        Grid grid = racetrack.getGrid();
        GridCell gridCells[][] = grid.getGridCells();
        BufferedImage mapBufferedImage = new BufferedImage(grid.getColumns(), grid.getRows(), BufferedImage.TYPE_INT_ARGB);
        for(int i = 0;i<mapBufferedImage.getHeight();i++) {
            for(int j = 0;j<mapBufferedImage.getWidth();j++) {
                mapBufferedImage.setRGB(j, i, gridCells[i][j].isWall()?0xff000000:(gridCells[i][j].isSpawn()?0xffff0000:0xffffffff));
            }
        }
        try {
            File mapImageFile = new File(directory+fileName+".png");
            ImageIO.write(mapBufferedImage, "png", mapImageFile);
            
            saveGoalLines(racetrack.getGoalLines(), fileName);
            
            System.out.println("saved Map :)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void saveGoalLines(ArrayList<GoalLine> goalLines, String fileName) {
        try {
            File mapTextFile = new File(directory+fileName+".txt");
            mapTextFile.createNewFile();
            PrintWriter pw = new PrintWriter(mapTextFile);
            int i = 0;
            for(GoalLine goaLine : goalLines) {
                if(i == goalLines.size()-1) {
                    pw.print(goaLine.getRow1() + " " + goaLine.getColumn1() + " / " + goaLine.getRow2() + " " + goaLine.getColumn2());
                }else {
                    pw.println(goaLine.getRow1() + " " + goaLine.getColumn1() + " / " + goaLine.getRow2() + " " + goaLine.getColumn2());
                }
                i++;
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadMap(RaceTrack racetrack,String fileName) {
       System.out.println("loading map...");
        try {
            File mapImageFile = new File(directory+fileName+".png");
            
            BufferedImage mapBufferedImage = ImageIO.read(mapImageFile);
            double gridCellSize = GUIController.getCanvasWidth()/80;
            racetrack.getGrid().initGrid(mapBufferedImage.getHeight(), mapBufferedImage.getWidth(), gridCellSize*mapBufferedImage.getWidth(), gridCellSize*mapBufferedImage.getHeight());
            
            GridCell[][] gridCells = racetrack.getGrid().getGridCells();
            ArrayList<GridCell> wallGridCells = racetrack.getGrid().getWallGridCells();
            ArrayList<GridCell> spawnGridCells = racetrack.getGrid().getSpawnGridCells();
            wallGridCells.clear();
            spawnGridCells.clear();
            
            for(int i = 0;i<mapBufferedImage.getHeight();i++) {
                for(int j = 0;j<mapBufferedImage.getWidth();j++) {
                    boolean isWall = mapBufferedImage.getRGB(j,i)==0xff000000;
                    boolean isSpawn = mapBufferedImage.getRGB(j,i)==0xffff0000;
                    gridCells[i][j].setWall(isWall);
                    if(isWall) {
                        wallGridCells.add(gridCells[i][j]);
                    }
                    gridCells[i][j].setSpawn(isSpawn);
                    if(isSpawn) {
                        spawnGridCells.add(gridCells[i][j]);
                    }
                }
            }
            racetrack.spawnCars();
            racetrack.initTrackLines();
            racetrack.initGoalLines(getGoalLines(fileName));
            racetrack.initMinimap();
            System.out.println("loaded Map :)");
            System.out.println("Resolution: " + mapBufferedImage.getWidth() + "/" + mapBufferedImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static ArrayList<GoalLine> getGoalLines(String fileName){
        ArrayList<GoalLine> goalLines = new ArrayList<GoalLine>();
        try {
            File mapTextFile = new File(directory+fileName+".txt");
            Scanner scanner = new Scanner(mapTextFile);
            
            
            while(scanner.hasNextLine()) {
                int row1 = scanner.nextInt();
                int column1 = scanner.nextInt();
                scanner.next();
                int row2 = scanner.nextInt();
                int column2 = scanner.nextInt();
                goalLines.add(new GoalLine(row1, column1, row2, column2));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goalLines;
    }
}
