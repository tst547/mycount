package my.app.domain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import my.app.box.AppBox;
import my.app.box.AppConts;
import my.app.model.BaseData;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Data
{
    @FXML
    private HBox hBox;
    @FXML
    private Label createTime;
    @FXML
    private Label name;

    public Data()
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

    public void setInfo(BaseData baseData)
    {
        createTime.setText(baseData.getCreateTime().format(DateTimeFormatter.ofPattern("uuuu-MM-dd")));
        name.setText(baseData.getName());
    }

    public HBox getBox()
    {
        return hBox;
    }
}