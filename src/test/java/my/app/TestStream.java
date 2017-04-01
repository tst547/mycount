package my.app;

import javafx.application.Application;
import javafx.stage.Stage;
import my.app.box.AppBox;
import my.app.box.AppConts;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by hanyu on 2017/3/31 0031.
 */
public class TestStream extends Application{

    @Test
    public void testStream() throws IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppBox.openNew(AppConts.FXML_VIEW,"dt");
    }
}
