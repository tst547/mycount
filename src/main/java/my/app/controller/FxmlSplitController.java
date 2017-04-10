package my.app.controller;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import io.datafx.controller.context.FXMLApplicationContext;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import my.app.box.AppBox;
import my.app.box.AppConts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 分割面板控制器
 * Created by hanyu on 2017/3/30 0030.
 */
@ViewController(value = "/my/app/box/fxml_split.fxml")
public final class FxmlSplitController{

    @FXML
    private SplitPane splitPane;
    @FXML
    private Button dataBtn;
    @FXML
    private Button viewBtn;
    @FXML
    private Button xxBtn;
    @FXML
    private Button countBtn;
    @FXML
    private TabPane rightPane;


    /**
     * 打开数据图形化统计窗口
     * @param actionEvent
     */
    public void ViewWindowAction(ActionEvent actionEvent) {
        Tab employeeTab = new Tab(AppConts.FX_TITLE_VIEW);
        try {
            employeeTab.setContent(AppBox.getNew(AppConts.FXML_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
            AppBox.AlertError(e.getMessage());
        }
        rightPane.getTabs().add(employeeTab);
    }

    /**
     * 打开新增计数窗口
     * @param actionEvent
     */
    public void CountWindowAction(ActionEvent actionEvent) {
        Tab employeeTab = new Tab(AppConts.FX_TITLE_COUNT);
        try {
            employeeTab.setContent(AppBox.getNew(AppConts.FXML_COUNT));
        } catch (IOException e) {
            e.printStackTrace();
            AppBox.AlertError(e.getMessage());
        }
        rightPane.getTabs().add(employeeTab);
    }

    /**
     * 打开新增基础数据窗口
     * @param actionEvent
     */
    public void DataWindowAction(ActionEvent actionEvent) {
        Tab employeeTab = new Tab(AppConts.FX_TITLE_DATA);
        try {
            employeeTab.setContent(AppBox.getNew(AppConts.FXML_DATA));
        } catch (IOException e) {
            e.printStackTrace();
            AppBox.AlertError(e.getMessage());
        }
        rightPane.getTabs().add(employeeTab);
    }

    public void XxWindowAction(ActionEvent actionEvent) {
    }

    public static final class InputController {
        @FXML
        private JFXListView<?> toolbarPopupList;

        // close application
        @FXML
        private void submit() {
            if (toolbarPopupList.getSelectionModel().getSelectedIndex() == 1) {
                Platform.exit();
            }
        }
    }
}
