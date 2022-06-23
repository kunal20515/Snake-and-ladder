package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.IOException;

public  class Main {
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void switchToGame(MouseEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage =  (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}