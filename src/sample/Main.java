package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        InputStream iconStream=getClass().getResourceAsStream("/images/icon.png");
        Image  image=new Image(iconStream);
        FXMLLoader loader=new FXMLLoader();
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Archiever");
        Label helloWordLabel=new Label("Hello World!");
        primaryStage.setScene(new Scene(root, 700, 540));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
