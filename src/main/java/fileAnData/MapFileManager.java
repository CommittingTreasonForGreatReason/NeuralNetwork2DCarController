package fileAnData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import drawables.Grid;
import drawables.GridCell;
import drawables.RaceTrack;

public class MapFileManager {
    
    public static void saveMap(RaceTrack racetrack, String fileName) {
        Grid grid = racetrack.getGrid();
        GridCell gridCells[][] = grid.getGridCells();
        BufferedImage mapBufferedImage = new BufferedImage(grid.getColumns(), grid.getRows(), BufferedImage.TYPE_INT_ARGB);
        for(int i = 0;i<mapBufferedImage.getHeight();i++) {
            for(int j = 0;j<mapBufferedImage.getWidth();j++) {
                mapBufferedImage.setRGB(j, i, gridCells[i][j].isWall()?0xff000000:0xffffffff);
            }
        }
        try {
            File mapImageFile = new File("src/main/resources/"+fileName+".png");
            ImageIO.write(mapBufferedImage, "png", mapImageFile);
            
            System.out.println("saved Map :)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
