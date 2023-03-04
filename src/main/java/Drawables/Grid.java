package Drawables;

import java.util.ArrayList;

import Vectors.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Grid extends DrawableObject{
    
    private GridCell[][] gridCells;
    private ArrayList<GridCell> wallGridCells = new ArrayList<GridCell>();
    private GridCell hoverGridCell;
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
    
    public GridCell[][] getGridCells() {
        return gridCells;
    }
    
    public ArrayList<GridCell> getWallGridCells() {
        return wallGridCells;
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
        if(hoverGridCell!=null) {
            hoverGridCell.drawHover(gc);
        }
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }
    
    public void tryClickBoardCell(final MouseEvent e) {
        if(hoverGridCell==null) {
            return;
        }
        if(e.getButton() == MouseButton.PRIMARY) {
            if(!wallGridCells.contains(hoverGridCell)) {
                wallGridCells.add(hoverGridCell);
                hoverGridCell.setWall(true);
                System.out.println(wallGridCells.size());
            }
        }else if(e.getButton() == MouseButton.SECONDARY){
            if(wallGridCells.contains(hoverGridCell)) {
                wallGridCells.remove(hoverGridCell);
                hoverGridCell.setWall(false);
                System.out.println(wallGridCells.size());
            }
        }
    }
    
    public void trySetHoverGridCell(Point2D mousePosition) {
        hoverGridCell = null;
        for (int i = 0;i<gridCells.length;i++) {
            for (int j = 0;j<gridCells[i].length;j++) {
                if(gridCells[i][j].containsPoint(mousePosition)) {
                    hoverGridCell = gridCells[i][j];
                }
            }
        }
    }

}
