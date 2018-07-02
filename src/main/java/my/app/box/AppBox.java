package my.app.box;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDialog;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

/**
 * 提供对图形化界面的管理
 * Created by hanyu on 2017/3/30 0030.
 */
public class AppBox {

    private static AppBox box = new AppBox();

    /**
     * 打开新窗口用fxml中的宽高
     * @param fxml
     * @throws IOException
     */
/*    public static Parent openNew(final String fxml, final String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(box.getClass().getResource(fxml));
        Parent target = loader.load();//载入窗口
        Decoration decoration = new Decoration(target);
        Scene scene = new Scene(decoration, Color.TRANSPARENT); //创建场景；
        Stage stg = new Stage();//创建舞台；
        stg.initStyle(StageStyle.TRANSPARENT);
        stg.setScene(scene); //将场景载入舞台；
        stg.setTitle(title);
        stg.show(); //显示窗口；
        return target;
    }*/

    /**
     * 打开新窗口
     * @param
     * @throws IOException
     */
/*    public static Parent openNew(final String fxml, final String title,long width ,long height) throws IOException {
        FXMLLoader loader = new FXMLLoader(box.getClass().getResource(fxml));
        Parent target = loader.load();//载入窗口
        Decoration decoration = new Decoration(target);
        Scene scene = new Scene(decoration,width ,height , Color.TRANSPARENT); //创建场景；
        Stage stg = new Stage();//创建舞台；
        stg.initStyle(StageStyle.TRANSPARENT);
        stg.setScene(scene); //将场景载入舞台；
        stg.setTitle(title);
        stg.show(); //显示窗口；
        return target;
    }*/

    public static void openJFX(Stage stage,ViewFlowContext flowContext,Class fxmlClass ,long width ,long height) throws IOException {
        Flow flow = new Flow(fxmlClass);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", stage);
        try {
            flow.createHandler(flowContext).start(container);
        } catch (FlowException e) {
            e.printStackTrace();
        }
        JFXDecorator decorator = new JFXDecorator(stage, container.getView());
        decorator.setCustomMaximize(true);
        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(AppBox.class.getResource("jfoenix-fonts.css").toExternalForm(),
                AppBox.class.getResource("jfoenix-design.css").toExternalForm(),
                AppBox.class.getResource("jfoenix-main-demo.css").toExternalForm());
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setScene(scene);
        stage.show();

    }


    /**
     * 获取窗口
     * @param fxml
     * @throws IOException
     */
    public static Parent getNew(final String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(box.getClass().getResource(fxml));
        Parent target = loader.load();//载入窗口
        return target;
    }

    /**
     * 获取loader
     * @param fxml
     * @throws IOException
     */
    public static FXMLLoader getLoader(final String fxml) throws IOException {
         return new FXMLLoader(box.getClass().getResource(fxml));
    }

    /**
     * 操作成功对话框提示
     */
    public static void AlertDialog(ViewFlowContext context,Dialog flag){
        JFXDialog dialog = new JFXDialog();
        try {
            Parent parent = getNew(AppConts.DIALOG_FXML);
            Label label = (Label) parent.lookup("#msg");
            Button acceptButton = (Button) parent.lookup("#acceptButton");
            label.setText(flag==Dialog.success?"操作成功!":flag==Dialog.failed?"操作失败!":"未知提示信息");
            acceptButton.setOnMouseClicked((e) -> dialog.close());
            dialog.setContent((Region) parent);
            dialog.show((StackPane) context.getRegisteredObject(AppConts.CONTENT_PANE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public enum Dialog{
        success,
        failed
    }

}
