package my.app.domain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import my.app.box.AppBox;
import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.model.Count;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Created by hanyu on 2017/3/31 0031.
 */
public class DataCount {
    @FXML
    private HBox hBox;
    @FXML
    private Label createTime;
    @FXML
    private Label name;

    public DataCount()
    {
        try
        {
            FXMLLoader fxmlLoader = AppBox.getLoader(AppConts.LIST_ITEM);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Count count)
    {
        createTime.setText(count.getCreateTime().format(DateTimeFormatter.ofPattern("uuuu-MM-dd")));
        name.setText(String.valueOf(count.getCount()));
    }

    public HBox getBox()
    {
        return hBox;
    }
}
