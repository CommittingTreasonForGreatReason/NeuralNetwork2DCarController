package Vectors;

public class Vector2 {
    double x,y;
    
    public static Vector2 zero = new Vector2(0, 0);
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setLength(double length){
        double angleInRadians = getArgument();
        
        this.x = Math.cos(angleInRadians) * length;
        this.y = Math.sin(angleInRadians) * length;
    }
    
    public void setAngle(double angleInRadians){
        double length = getLength();
        
        this.x = Math.cos(angleInRadians) * length;
        this.y = Math.sin(angleInRadians) * length;
    }
    
    public double getLength() {
        return Math.sqrt(x*x+y*y);
    }
    
    public double getArgument() {
        return Math.atan2(x,y);
    }
    
    public void scale(double scaleFactor) {
        this.x *= scaleFactor;
        this.y *= scaleFactor;
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
        return "Vector2:\n" + "[x = " + x +  "|y = " + y +"]\n" + "[length = " + getLength() + "|argument / angle = " + getArgument() + " / " + Math.toDegrees(getArgument()) +"]";
    }
}
