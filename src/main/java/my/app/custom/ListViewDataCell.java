package my.app.custom;

import javafx.scene.control.ListCell;
import my.app.domain.Data;
import my.app.model.BaseData;

public class ListViewDataCell extends ListCell<BaseData>
{
    @Override
    public void updateItem(BaseData baseData, boolean empty)
    {
        super.updateItem(baseData,empty);
        if(baseData != null)
        {
            Data data = new Data();
            data.setInfo(baseData);
            setGraphic(data.getBox());
        }
    }
}
