package my.app;

import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.stage.Stage;
import my.app.box.AppBox;
import my.app.box.AppConts;
import my.app.controller.FxmlMainController;

import java.io.IOException;

/**
 * Created by hanyu on 2017/3/28 0028.
 */
public class App extends Application {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            AppBox.openJFX(stage,flowContext, FxmlMainController.class,600,440);
            //AppBox.openNew(AppConts.FXML_SPLIT,AppConts.FX_TITLE_MAIN,592,420);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
