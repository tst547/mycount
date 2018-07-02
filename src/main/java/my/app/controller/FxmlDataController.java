package my.app.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import my.app.box.AppBox;
import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.serivce.DataSerivce;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 数据控制器
 * Created by hanyu on 2017/3/29 0029.
 */
@ViewController(value = "/my/app/box/fxml_data.fxml")
public class FxmlDataController implements Initializable{
    @FXMLViewFlowContext
    private ViewFlowContext context;

    DataSerivce dataSerivce = new DataSerivce();
    @FXML
    private JFXTreeTableView listView;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField data;
    @FXML
    private JFXComboBox choiceType;
    @FXML
    private JFXComboBox tableType;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXButton submit;

    ObservableList<Data> observableList;

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
        if(data.getText()==""||data.getText().trim().length()==0){
            AppBox.AlertDialog(context, AppBox.Dialog.failed);
            return;
        }
        dataSerivce.add(baseData);
        AppBox.AlertDialog(context, AppBox.Dialog.success);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        JFXTreeTableColumn<Data, String> dateColumn = new JFXTreeTableColumn<>("date");
        dateColumn.setPrefWidth(190);
        dateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Data, String> param) -> {
            if (dateColumn.validateValue(param)) {
                return param.getValue().getValue().createTime;
            } else {
                return dateColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Data, String> typeColumn = new JFXTreeTableColumn<>("type");
        typeColumn.setPrefWidth(190);
        typeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Data, String> param) -> {
            if (typeColumn.validateValue(param)) {
                return param.getValue().getValue().type;
            } else {
                return typeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<Data, String> nameColumn = new JFXTreeTableColumn<>("name");
        nameColumn.setPrefWidth(190);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Data, String> param) -> {
            if (nameColumn.validateValue(param)) {
                return param.getValue().getValue().name;
            } else {
                return nameColumn.getComputedValue(param);
            }
        });

        dateColumn.setCellFactory((TreeTableColumn<Data, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        dateColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Data, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().createTime.set(t.getNewValue()));

        typeColumn.setCellFactory((TreeTableColumn<Data, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        typeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Data, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().type.set(t.getNewValue()));

        nameColumn.setCellFactory((TreeTableColumn<Data, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        nameColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<Data, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().name.set(t.getNewValue()));

        observableList = FXCollections.observableArrayList();
        List<BaseData> baseDataList = dataSerivce.getTempAll();
        baseDataList
                .stream()
                .forEach((baseData)->
                        observableList.add(new Data(
                                String.valueOf(baseData.getType())
                                ,baseData.getCreateTime().format(DateTimeFormatter.ofPattern(AppConts.LOCALDATE_FORMAT_NYR2))
                                ,baseData.getName())));
        final TreeItem<Data> root = new RecursiveTreeItem<>(observableList, RecursiveTreeObject::getChildren);
        listView.setRoot(root);
        listView.setShowRoot(false);
        listView.setEditable(true);
        listView.getColumns().setAll(dateColumn, typeColumn, nameColumn);
    }

    private static final class Data extends RecursiveTreeObject<Data> {
        final StringProperty type;
        final StringProperty createTime;
        final StringProperty name;

        Data(String type, String createTime, String name) {
            this.type = new SimpleStringProperty(type);
            this.createTime = new SimpleStringProperty(createTime);
            this.name = new SimpleStringProperty(name);
        }
    }

}
