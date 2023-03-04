package NeuralNetworkGroup.NeuralNetworkArtifact;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import Drawables.RaceTrack;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), GUIController.initial_width, GUIController.initial_height);
        stage.setTitle("1000 IQ Car");
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(this::keyPressed);
        scene.setOnKeyReleased(this::keyReleased);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    private void keyPressed(KeyEvent e) {
        RaceTrack.getRaceTrackInstance().keyPressed(e);
    }
    private void keyReleased(KeyEvent e) {
        RaceTrack.getRaceTrackInstance().keyReleased(e);
    }
}