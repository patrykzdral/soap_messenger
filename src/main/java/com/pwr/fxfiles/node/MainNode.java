package com.pwr.fxfiles.node;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainNode extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setResizable(false);
            setMainStage(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/node.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            primaryStage.setTitle("Normal Node");
            primaryStage.setScene(new Scene(root, 667, 514));
           // primaryStage.getIcons().add(new Image("/image/icon.png"));


            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(MainNode.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        MainNode.mainStage = mainStage;
    }


}
