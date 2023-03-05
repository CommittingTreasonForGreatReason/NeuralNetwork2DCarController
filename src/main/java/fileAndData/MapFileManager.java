package fileAndData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import drawables.Grid;
import drawables.GridCell;
import drawables.RaceTrack;

public class MapFileManager {
    
    private static String directory = "src/main/resources/";
    
    public static void saveMap(RaceTrack racetrack, String fileName) {
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
            
            System.out.println("saved Map :)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadMap(RaceTrack racetrack,String fileName) {
       
        try {
            File mapImageFile = new File(directory+fileName+".png");
            BufferedImage mapBufferedImage = ImageIO.read(mapImageFile);
            
            racetrack.getGrid().initGrid(mapBufferedImage.getHeight(), mapBufferedImage.getWidth(), GUIController.getCanvasWidth(), GUIController.getCanvasHeight());
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
            System.out.println("loaded Map :)");
            System.out.println("Resolution: " + mapBufferedImage.getWidth() + "/" + mapBufferedImage.getHeight());
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}
