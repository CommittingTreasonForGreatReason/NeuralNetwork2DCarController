module NeuralNetworkGroup.NeuralNetworkArtifact {
    requires javafx.controls;
    requires javafx.fxml;

    opens NeuralNetworkGroup.NeuralNetworkArtifact to javafx.fxml;
    exports NeuralNetworkGroup.NeuralNetworkArtifact;
}
