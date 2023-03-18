package drawables;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vectors.Vector2;

public abstract class DrawableObject {
	protected Color baseColor;
	public Vector2 centerPoint;
	public DrawableObject(Color baseColor, Vector2 centerPoint) {
		this.baseColor = baseColor;
		this.centerPoint = centerPoint;
	}
	
	public void setCenterPoint(Vector2 centerPoint){
		this.centerPoint = centerPoint;
	}
	
	public double getCenterX() {
        return centerPoint.getX();
    }
	public double getCenterY() {
        return centerPoint.getY();
    }
	// updates the objects data continuously.
	public abstract void update(final double secondsSinceLastFrame);

	// draws the object
	public abstract void draw(final GraphicsContext gc);
	
	// repositions / changes the coordinates and size of the drawable object
	public abstract void repositionGeometryOnResize();
	
}
