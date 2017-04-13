package my.app.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
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
import my.app.model.Count;
import my.app.serivce.CountService;
import my.app.serivce.DataSerivce;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 计数控制器
 * Created by hanyu on 2017/3/30 0030.
 */
@ViewController(value = "/my/app/box/fxml_count.fxml")
public class FxmlCountController implements Initializable {
    @FXMLViewFlowContext
    private ViewFlowContext context;

    CountService countSerivce = new CountService();
    @FXML
    private JFXTreeTableView listView;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField data;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button submit;

    ObservableList<DCount> observableList;


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
        AppBox.AlertDialog(context, AppBox.Dialog.success);
    }

    /**
     * 初始化时执行该函数
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        JFXTreeTableColumn<DCount, String> dateColumn = new JFXTreeTableColumn<>("date");
        dateColumn.setPrefWidth(285);
        dateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DCount, String> param) -> {
            if (dateColumn.validateValue(param)) {
                return param.getValue().getValue().createTime;
            } else {
                return dateColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<DCount, String> countColumn = new JFXTreeTableColumn<>("count");
        countColumn.setPrefWidth(285);
        countColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<DCount, String> param) -> {
            if (dateColumn.validateValue(param)) {
                return param.getValue().getValue().count;
            } else {
                return dateColumn.getComputedValue(param);
            }
        });

        dateColumn.setCellFactory((TreeTableColumn<DCount, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        dateColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<DCount, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().createTime.set(t.getNewValue()));

        countColumn.setCellFactory((TreeTableColumn<DCount, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        countColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<DCount, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition()
                        .getRow())
                .getValue().count.set(t.getNewValue()));

        observableList = FXCollections.observableArrayList();
        List<Count> countList = countSerivce.getTempAll();
        countList
                .stream()
                .forEach(count ->
                        observableList.add(new DCount(
                                count.getCreateTime().format(DateTimeFormatter.ofPattern(AppConts.LOCALDATE_FORMAT_NYR2))
                                ,String.valueOf(count.getCount()))));
        final TreeItem<DCount> root = new RecursiveTreeItem<>(observableList, RecursiveTreeObject::getChildren);
        listView.setRoot(root);
        listView.setShowRoot(false);
        listView.setEditable(true);
        listView.getColumns().setAll(dateColumn, countColumn);
    }
    private static final class DCount extends RecursiveTreeObject<DCount> {
        final StringProperty createTime;
        final StringProperty count;

        DCount(String createTime, String count) {
            this.createTime = new SimpleStringProperty(createTime);
            this.count = new SimpleStringProperty(count);
        }
    }

}

