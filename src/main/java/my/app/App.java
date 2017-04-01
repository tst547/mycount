package my.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.app.box.AppBox;
import my.app.box.AppConts;
import org.mistfx.decoration.Decoration;

import java.io.IOException;

/**
 * Created by hanyu on 2017/3/28 0028.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            AppBox.openNew(AppConts.FXML_SPLIT,AppConts.FX_TITLE_MAIN,592,420);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
