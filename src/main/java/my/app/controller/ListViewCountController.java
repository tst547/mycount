package my.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import my.app.custom.ListViewCountCell;
import my.app.custom.ListViewDataCell;
import my.app.model.BaseData;
import my.app.model.Count;
import my.app.serivce.CountService;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by hanyu on 2017/3/31 0031.
 */
public class ListViewCountController implements Initializable{

    private CountService countSerivceSerivce = new CountService();

    @FXML
    private ListView listView;
    private Set<Count> set = new LinkedHashSet<Count>();
    ObservableList observableList = FXCollections.observableArrayList();

    public void setListView()
    {
        set.addAll(countSerivceSerivce.getTempAll());
        observableList.setAll(set);
        listView.setItems(observableList);
        listView.setCellFactory((listView)-> new ListViewCountCell());
        /*listView.setCellFactory(new Callback<ListView<Count>, ListCell<Count>>()
        {
            @Override
            public ListCell<Count> call(ListView<Count> listView)
            {
                return new ListViewCountCell();
            }
        });*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListView();
    }
}
