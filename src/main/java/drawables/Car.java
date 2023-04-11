package drawables;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import neuralNetwork.Matrix;
import neuralNetwork.NeuralNetwork;
import neuralNetwork.NeuralNetworkFileManager;
import neuralNetwork.NeuralNetworkVisualizer;
import vectors.Vector2;
import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;

public class Car extends DrawableObject{
    
    private Vector2 velocity,desiredDirection;
    private Rectangle hitBoxRectangle;
    private double acceleration;
    private double rotationSpeed;
    
    private static double width,height;
    private static final double maxVelocity = 300;
    private static final double deceleration = -20;
    
    private boolean isCrashed = false;
    private double fitness = 0;
    private static String nameOfNeuralNetwork = "unnamed";
    private NeuralNetwork neuralNetwork;
    // Sensors
    private static double sensorRange;
    Vector2[] sensorVectors = new Vector2[7];
    Vector2[] trackSensorPoints = new Vector2[7];   
    GoalLine nextGoalLine = null;
    
    public Car(Vector2 centerPoint) {
        super(Constants.CAR_COLOR, centerPoint);
        velocity = new Vector2(1, 1);
        desiredDirection = new Vector2(1, 1);
        desiredDirection.setMagnitude(1);
        hitBoxRectangle = new Rectangle(centerPoint.getX()-width/2,centerPoint.getY()-width/2,width,width);
        neuralNetwork = new NeuralNetwork(sensorVectors.length+4,8,4,2);
        updateSensorVectors();
        updateTrackSensorPoints();
    }
    
    public static String getNameOfNeuralNetwork() {
        return nameOfNeuralNetwork;
    }
    
    public static void setSensorRange(double sensorRange) {
        Car.sensorRange = sensorRange;
    }
    
    public static void setNameOfNeuralNetwork(String nameOfNeuralNetwork) {
        Car.nameOfNeuralNetwork = nameOfNeuralNetwork;
    }
    
    public double getFitness() {
        return fitness;
    }
    
    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
    
    public NeuralNetwork getMutatedNeuralNetworkCopy() {
        NeuralNetwork neuralNetworkCopy = neuralNetwork.getCopyNeuralNetwork();
        neuralNetworkCopy.mutate(0.2);
        return neuralNetworkCopy;
    }
    
    public NeuralNetwork getNeuralNetworkCopy() {
        return neuralNetwork.getCopyNeuralNetwork();
    }
    
    private void updateSensorVectors() {
        int amountOfSensors = sensorVectors.length;
        int range = 180;
        int angleStep = range / (amountOfSensors-1);
        for(int i = 0;i<amountOfSensors;i++) {
            sensorVectors[i] = new Vector2(0, 0);
            sensorVectors[i].setAngle(angleStep*i-range/2+desiredDirection.getAngle());
            sensorVectors[i].setMagnitude(sensorRange);
        }
    }
    
    private void updateTrackSensorPoints() {
        int amountOfSensors = sensorVectors.length;
        for(int i = 0;i<amountOfSensors;i++) {
            trackSensorPoints[i] = getPointClosestTrackIntersection(sensorVectors[i]);
        }
    }
    
    public void saveNeuralNetwork(String fileName) {
    	NeuralNetworkFileManager.saveNeuralNetworkAsFile(neuralNetwork, fileName);
    }
    
    public void loadNeuralNetwork(String fileName) {
    	neuralNetwork = NeuralNetworkFileManager.loadNeuralNetwork(fileName);
    }
    
    public static void setSizes(){
        height = GUIController.getCanvasWidth()*0.02;
        width = height*0.6;
    }
    
    public boolean isCrashed() {
        return isCrashed;
    }
    
    public void setCenterPoint(Vector2 centerPoint) {
        this.centerPoint = centerPoint;
        hitBoxRectangle = new Rectangle(centerPoint.getX()-width/2,centerPoint.getY()-width/2,width,width);
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        steerCarNeuralNetwork();
        if(secondsSinceLastFrame<1 && !isCrashed) {
            move(secondsSinceLastFrame);
        }
        hitBoxRectangle = new Rectangle(centerPoint.getX()-width/2,centerPoint.getY()-width/2,width,width);
        updateSensorVectors();
        updateTrackSensorPoints();
    }
    
    private void steerCarNeuralNetwork() {
    	Matrix inputMatrix = new Matrix(neuralNetwork.getnInputNodes(), 1);
    	int i = 0;
		for(;i<trackSensorPoints.length;i++) {
			Vector2 trackSensorPoint = trackSensorPoints[i];
			inputMatrix.matrix[i][0] = (trackSensorPoint!=null?getDistanceFromPoint(trackSensorPoints[i]):sensorRange)/GridCell.getSize();
		}
		inputMatrix.matrix[i][0] = desiredDirection.getAngle();
        i++;
        inputMatrix.matrix[i][0] = velocity.getAngleInDegrees();
        i++;
        inputMatrix.matrix[i][0] = velocity.getMagnitude();
        i++;
        inputMatrix.matrix[i][0] = getNextGoalLineDirectionAngle();
        i++;
        
        Matrix outputMatrix = neuralNetwork.feedForward(inputMatrix);
        
        if(outputMatrix.matrix[0][0] > 0.5) {
            acceleration = 60;
        }else if(outputMatrix.matrix[1][0] > 0.5){
            acceleration = -80;
        }else {
            acceleration = 0;
        }
        if(outputMatrix.matrix[2][0] > 0.5) {
            rotationSpeed = -120;
        }else if(outputMatrix.matrix[3][0] > 0.5) {
            rotationSpeed = 120;
        }else {
            rotationSpeed = 0;
        }
    }
    
    private void move(double secondsSinceLastFrame) {
        double normalizer = GridCell.getSize()/20.0;
        centerPoint = Vector2.add(centerPoint, Vector2.getScaledVector(velocity, secondsSinceLastFrame*normalizer));
        desiredDirection.setAngle(desiredDirection.getAngle()+rotationSpeed*secondsSinceLastFrame);
        
        double speed = velocity.getMagnitude() + (acceleration+deceleration)*secondsSinceLastFrame * normalizer;
        if(speed < 0) {
            speed = 0;
        }
        velocity = Vector2.add(velocity, Vector2.getScaledVector(desiredDirection, 2*normalizer*secondsSinceLastFrame*1000));
        velocity.setMagnitude(speed);
        if(velocity.getMagnitude() > maxVelocity*normalizer) {
            velocity.setMagnitude(maxVelocity*normalizer);
        }
    }
    
    public void updateCrashed(ArrayList<Line2D> trackLines) {
        for (Line2D line : trackLines) {
            if(line.intersects(hitBoxRectangle.getX(),hitBoxRectangle.getY(),hitBoxRectangle.getWidth(),hitBoxRectangle.getHeight())) {
                 isCrashed = true;
                 baseColor = Constants.CAR_CRASHED_COLOR;
                 return;
            }
        }
    }
    
    public void updateGoalLineScore(ArrayList<GoalLine> goalLines) {
        fitness = (int)fitness; 
        nextGoalLine = goalLines.get(((int)fitness) % goalLines.size());
        if(nextGoalLine.getLine().intersects(hitBoxRectangle.getX(),hitBoxRectangle.getY(),hitBoxRectangle.getWidth(),hitBoxRectangle.getHeight())) {
            fitness++;
        }
        double bonusFitness = 100/getDistanceFromPoint(goalLines.get(((int)fitness)%goalLines.size()).getCenterPoint())/GridCell.getSize();
        fitness += bonusFitness;
    }
    
    public Vector2 getPointClosestTrackIntersection(Vector2 v2) {
        double closest = Double.MAX_VALUE;
        Vector2 vectorPoint = null;
        Line2D vectorLine = new Line2D.Double(centerPoint.getX(), centerPoint.getY(), centerPoint.getX() + v2.getX(), centerPoint.getY() + v2.getY());
        ArrayList<Line2D> trackLines = RaceTrack.getRaceTrackInstance().getTrackLines();
        for(Line2D trackLine : trackLines) {
            if(trackLine.intersectsLine(vectorLine)){
                Vector2 vectorPointCurrent = getLineIntersectionPoint(trackLine,vectorLine);
                double distanceNoSqrt = getDistanceFromPointNoSqrt(vectorPointCurrent);
                if(distanceNoSqrt < v2.getMagnitude()*v2.getMagnitude() && distanceNoSqrt < closest) {
                    vectorPoint = vectorPointCurrent;
                    closest = distanceNoSqrt;
                }
                
            }
        }
        return vectorPoint;
    }
    
    public Vector2 getLineIntersectionPoint(Line2D line1,Line2D line2) {
        // ax + c = line1
        // bx + d = line2
        double a = (line1.getY1() - line1.getY2()) / (line1.getX1() - line1.getX2()); 
        double b = (line2.getY1() - line2.getY2()) / (line2.getX1() - line2.getX2()); 
        a = line1.getX1() == line1.getX2()?10:a;
        b = line2.getX1() == line2.getX2()?10:b;
        float c = (int) (line1.getY1() - a*line1.getX1());
        float d = (int) (line2.getY1() - b*line2.getX1());
        
        float x = (float) ((d-c) / (a-b));
        float y = (float) (a*x + c);
        
        return new Vector2(x,y);
    }
    
    public double getDistanceFromPointNoSqrt(Vector2 v2) {
        double ak = v2.getX() - centerPoint.getX();
        double gk = v2.getY() - centerPoint.getY();
        return ak*ak + gk*gk;
    }
    public double getDistanceFromPoint(Vector2 v2) {
        double ak = v2.getX() - centerPoint.getX();
        double gk = v2.getY() - centerPoint.getY();
        return Math.sqrt(ak*ak + gk*gk);
    }
    
    private double getNextGoalLineDirectionAngle() {
        double x = nextGoalLine.getLine().getBounds().getCenterX();
        double y = nextGoalLine.getLine().getBounds().getCenterY();
        return Vector2.subtract(new Vector2(x, y), centerPoint).getAngleInDegrees();
    }
    
    

    @Override
    public void draw(GraphicsContext gc) {
            gc.setFill(baseColor);
            gc.translate(centerPoint.getX(), centerPoint.getY());
            gc.rotate(-desiredDirection.getAngleInDegrees());
            gc.fillRect(-width/2, -height/2, width, height);
            gc.rotate(desiredDirection.getAngleInDegrees());
            
//            gc.setFont(new Font(20));
//            gc.setFill(Color.RED);
//            gc.fillText(Math.round(fitness*1000)/1000.0 + "", 0, 0);
            
            gc.translate(-centerPoint.getX(), -centerPoint.getY());
    }
    
    public void drawSensorVectors(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        for(Vector2 sensorVector : sensorVectors) {
            gc.strokeLine(centerPoint.getX(), centerPoint.getY(), centerPoint.getX() + sensorVector.getX(), centerPoint.getY() + sensorVector.getY());
        }
    }
    
    public void drawTrackSensorPoints(GraphicsContext gc) {
        gc.setFill(Color.RED);
        for(Vector2 trackSensorPoint : trackSensorPoints) {
            if(trackSensorPoint != null) {
                gc.fillOval(trackSensorPoint.getX()-3, trackSensorPoint.getY()-3, 6, 6);
            }
        }
    }
    
    public void drawNeuralNetwork(GraphicsContext gc) {
        String inputLabels = "";
        for(int i = 0;i<sensorVectors.length;i++) {
            inputLabels += "sensor " + i + ":";
        }
        inputLabels+= "desired direction:direction:velocity:next goal direction";
        
        String outputLabels = "accelerate:decelerate:turn left:turn right";
        NeuralNetworkVisualizer.visualizeNeuralNetwork(nameOfNeuralNetwork,inputLabels,outputLabels,gc, neuralNetwork, GUIController.getCanvasWidth(), GUIController.getCanvasHeight());
    }
    
    public void drawCrashedCross(GraphicsContext gc) {
        gc.setLineWidth(2);
        gc.setStroke(baseColor);
        gc.translate(centerPoint.getX(), centerPoint.getY());
        gc.rotate(-desiredDirection.getAngleInDegrees());
        gc.strokeLine(-width/2, -width/2, width/2, width/2);
        gc.strokeLine(width/2, -width/2, -width/2, width/2);
        gc.rotate(desiredDirection.getAngleInDegrees());
        gc.translate(-centerPoint.getX(), -centerPoint.getY());
    }
    
    public void drawHitBox(GraphicsContext gc) {
          gc.setStroke(Color.BLACK);
          gc.setLineWidth(3);
          gc.strokeRect(hitBoxRectangle.getX(), hitBoxRectangle.getY(), hitBoxRectangle.getWidth(), hitBoxRectangle.getHeight());
    }
    
    public void drawVectors(GraphicsContext gc) {
        gc.setLineWidth(3);
        gc.setStroke(Constants.VECTOR_COLOR);
        gc.strokeLine(centerPoint.getX(), centerPoint.getY(), centerPoint.getX() + desiredDirection.getX(), centerPoint.getY() + desiredDirection.getY());
        gc.setStroke(Color.GREEN);
        gc.strokeLine(centerPoint.getX(), centerPoint.getY(), centerPoint.getX() + velocity.getX(), centerPoint.getY() + velocity.getY());
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }
    
    public void steerCarKeyPressed(KeyEvent e) {
        if(e.getText().equals("w")) {
            acceleration = 60;
        }else if(e.getText().equals("s")) {
            acceleration = -80;
        }else if(e.getText().equals("a")) {
            rotationSpeed = -120;
        }else if(e.getText().equals("d")) {
            rotationSpeed = 120;
        }
    }
    
    public void steerCarKeyReleased(KeyEvent e) {
        if(e.getText().equals("w")) {
            acceleration = 0;
        }else if(e.getText().equals("s")) {
            acceleration = 0;
        }else if(e.getText().equals("a")) {
            rotationSpeed = 0;
        }else if(e.getText().equals("d")) {
            rotationSpeed = 0;
        }
    }
    
}
