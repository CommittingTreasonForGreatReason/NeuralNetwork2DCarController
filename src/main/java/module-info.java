module NeuralNetworkGroup.NeuralNetworkArtifact {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens NeuralNetworkGroup.NeuralNetworkArtifact to javafx.fxml;
    exports NeuralNetworkGroup.NeuralNetworkArtifact;
}
