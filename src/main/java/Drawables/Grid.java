package Drawables;

import Vectors.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid extends DrawableObject{
    
    private GridCell[][] gridCells;
    private double size;

    public Grid(Color baseColor, Vector2 centerPoint, double size) {
        super(baseColor, centerPoint);
        this.size=size;
        initGrid(20);
    }
    
    private void initGrid(int gridResolution) {
        gridCells =  new GridCell[gridResolution][gridResolution];
        GridCell.initSize(size, gridResolution);
        double gridCellSize = GridCell.getSize();
        for (int i = 0;i<gridResolution;i++) {
            for (int j = 0;j<gridResolution;j++) {
                Vector2 gridCellCenterPoint = new Vector2(centerPoint.getX()-size/2+gridCellSize/2+gridCellSize*j, centerPoint.getY()-size/2+gridCellSize/2+gridCellSize*i);
                
                gridCells[i][j] = new GridCell(Color.AQUA, gridCellCenterPoint);
            }
        }
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        
        
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(baseColor);
        gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        for (int i = 0;i<gridCells.length;i++) {
            for (int j = 0;j<gridCells[i].length;j++) {
                gridCells[i][j].draw(gc);
            }
        }
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }

}
