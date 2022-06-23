package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class m_Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Snake and Ladder");
            Image image  = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\iconOfSnake.PNG");
            stage.getIcons().add(image);
            scene.setFill(Color.BLACK);
//            stage.setFullScreen(true);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
