package my.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import my.app.custom.ListViewDataCell;
import my.app.model.BaseData;
import my.app.serivce.DataSerivce;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by hanyu on 2017/3/31 0031.
 */
public class ListViewDataController implements Initializable {

    private DataSerivce dataSerivce = new DataSerivce();

    @FXML
    private ListView listView;
    private Set<BaseData> set = new LinkedHashSet<BaseData>();
    ObservableList observableList = FXCollections.observableArrayList();

    public void setListView()
    {
        set.addAll(dataSerivce.getTempAll());
        observableList.setAll(set);
        listView.setItems(observableList);
        listView.setCellFactory(new Callback<ListView<BaseData>, ListCell<BaseData>>()
        {
            @Override
            public ListCell<BaseData> call(ListView<BaseData> listView)
            {
                return new ListViewDataCell();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }
}
