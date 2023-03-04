package NeuralNetworkGroup.NeuralNetworkArtifact;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import drawables.RaceTrack;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static GUIController guiController;
    private static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), GUIController.initial_width, GUIController.initial_height);
        stage.setTitle("1000 IQ Car");
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(this::keyPressed);
        scene.setOnKeyReleased(this::keyReleased);
        guiController = fxmlLoader.getController();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    private void keyPressed(KeyEvent e) {
        if(e.getText().equals("b")) {
            guiController.triggerSaveMapDialog();
        }else {
            RaceTrack.getRaceTrackInstance().keyPressed(e);
        }
    }
    private void keyReleased(KeyEvent e) {
        RaceTrack.getRaceTrackInstance().keyReleased(e);
    }
}