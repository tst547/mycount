package my.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import my.app.box.AppBox;
import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.model.Count;
import my.app.serivce.CountService;
import my.app.serivce.DataSerivce;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 计数控制器
 * Created by hanyu on 2017/3/30 0030.
 */
public class FxmlCountController extends FxmlBase implements Initializable {

    CountService countSerivce = new CountService();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField data;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button submit;

    /**
     * 提交新增的data数据
     * @param actionEvent
     */
    @FXML
    public void submitBtnAction(ActionEvent actionEvent) {
        Count count = new Count();
        count.setId(System.currentTimeMillis());
        count.setCreateTime(datePicker.getValue());
        count.setCount(data.getText()!=""?Integer.valueOf(data.getText()):0);
        countSerivce.add(count);
        AppBox.AlertSuccess();
    }

    /**
     * 初始化时执行该函数
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void changeSize() {

    }
}
