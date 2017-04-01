package my.app.box;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import my.app.App;
import my.app.controller.FxmlBase;
import my.app.source.AppBaseData;
import org.mistfx.decoration.Decoration;

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
    public static Parent openNew(final String fxml, final String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(box.getClass().getResource(fxml));
        Parent target = loader.load();//载入窗口
        FxmlBase fxmlBase = loader.getController();
        Decoration decoration = new Decoration(target);
        Scene scene = new Scene(decoration, Color.TRANSPARENT); //创建场景；
        Stage stg=new Stage();//创建舞台；
        stg.initStyle(StageStyle.TRANSPARENT);
        stg.setScene(scene); //将场景载入舞台；
        stg.setTitle(title);
        stg.show(); //显示窗口；
        fxmlBase.setStage(stg);
        return target;
    }

    /**
     * 打开新窗口
     * @param fxml
     * @throws IOException
     */
    public static Parent openNew(final String fxml, final String title,long width ,long height) throws IOException {
        FXMLLoader loader = new FXMLLoader(box.getClass().getResource(fxml));
        Parent target = loader.load();//载入窗口
        FxmlBase fxmlBase = loader.getController();
        Decoration decoration = new Decoration(target);
        Scene scene = new Scene(decoration,width ,height , Color.TRANSPARENT); //创建场景；
        Stage stg=new Stage();//创建舞台；
        stg.initStyle(StageStyle.TRANSPARENT);
        stg.setScene(scene); //将场景载入舞台；
        stg.setTitle(title);
        stg.show(); //显示窗口；
        fxmlBase.setStage(stg);
        return target;
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
    public static void AlertSuccess(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText("操作成功!");
        alert.showAndWait();
    }

    /**
     * 错误信息对话框提示
     */
    public static void AlertError(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误！");
        alert.setHeaderText("详细信息");
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
