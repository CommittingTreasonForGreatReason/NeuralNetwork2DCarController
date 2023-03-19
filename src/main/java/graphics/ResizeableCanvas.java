package graphics;

import javafx.scene.canvas.Canvas;

public final class ResizeableCanvas extends Canvas { 

    // constructor
    public ResizeableCanvas(final double width, final double height) {
        setWidth(width);
        setHeight(height);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(final double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(final double width) {
        return getHeight();
    }
}
