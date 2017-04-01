package my.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import my.app.box.AppBox;
import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.serivce.DataSerivce;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * 数据控制器
 * Created by hanyu on 2017/3/29 0029.
 */
public class FxmlDataController extends FxmlBase implements Initializable{

    DataSerivce dataSerivce = new DataSerivce();

    @FXML private AnchorPane anchorPane;
    @FXML private TextField data;
    @FXML private ChoiceBox choiceType;
    @FXML private DatePicker datePicker;
    @FXML private Button submit;

    /**
     * 提交新增的data数据
     * @param actionEvent
     */
    @FXML
    public void submitBtnAction(ActionEvent actionEvent) {
        BaseData baseData = new BaseData();
        baseData.setId(System.currentTimeMillis());
        baseData.setCreateTime(datePicker.getValue());
        baseData.setType(Integer.valueOf(choiceType.getValue().toString()));
        baseData.setName(data.getText()==""? AppConts.KONG:data.getText());
        dataSerivce.add(baseData);
        AppBox.AlertSuccess();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
    }


    @Override
    public void changeSize() {
        getStage().setWidth(anchorPane.getPrefWidth());
        getStage().setHeight(anchorPane.getPrefHeight());
        System.out.println("w: "+anchorPane.getPrefWidth()+" h:"+anchorPane.getPrefHeight());
    }
}
