package Vectors;

public class Vector2 {
    private double x,y;
    private double angle;
    private double magnitude;
    
    public static Vector2 zero = new Vector2(0, 0);
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
        calculateMagnitude(x, y);
        calculateAngle(x, y);
    }
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
        calculateAngle(x, y);
        calculateMagnitude(x, y);
    }
    public void setY(double y) {
        this.y = y;
        calculateAngle(x, y);
        calculateMagnitude(x, y);
    }
    public void setAngle(double angle) {
        this.angle = angle;
        calculateXandY(angle, magnitude);
    }
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
        calculateXandY(angle, magnitude);
    }
    
    public void calculateAngle(double x, double y) {
        angle = Math.toDegrees(Math.atan2(y, x));
    }
    public void calculateMagnitude(double x, double y) {
        magnitude = Math.sqrt(x*x+y*y);
    }
    public void calculateXandY(double angle,double magnitude) {
        x = magnitude*Math.cos(Math.toRadians(angle));
        y = magnitude*Math.sin(Math.toRadians(angle));    
    }
    
    public double getMagnitude() {
        return magnitude;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public double getAngleInDegrees() {
        return Math.toDegrees(Math.atan2(x,y));
    }
    
    public void scale(double scaleFactor) {
        this.x *= scaleFactor;
        this.y *= scaleFactor;
    }
    
    public static Vector2 getScaledVector(Vector2 v2,double scaleFactor) {
        return new Vector2(v2.getX()*scaleFactor, v2.getY()*scaleFactor);
    }
    
    public static Vector2 add(Vector2 v1,Vector2 v2) {
        return new Vector2(v1.x+v2.x, v1.y+v2.y);
    }
    
    public static Vector2 subtract(Vector2 v1,Vector2 v2) {
        return new Vector2(v1.x-v2.x, v1.y-v2.y);
    }
    
    public static Vector2 multiply(Vector2 v1,Vector2 v2) {
        return new Vector2(v1.x*v2.x, v1.y*v2.y);
    }
    
    public String toString() {
        return "Vector2:\n" + "[x = " + x +  "|y = " + y +"]\n" + "[magnitude = " + getMagnitude() + "|angle = " + getAngle() + "]";
    }
}
