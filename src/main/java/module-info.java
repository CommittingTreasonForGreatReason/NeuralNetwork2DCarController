module NeuralNetworkGroup.NeuralNetworkArtifact {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens NeuralNetworkGroup.NeuralNetworkArtifact to javafx.fxml;
    exports NeuralNetworkGroup.NeuralNetworkArtifact;
}
