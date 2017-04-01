package my.app.custom;

import javafx.scene.control.ListCell;
import my.app.domain.Data;
import my.app.domain.DataCount;
import my.app.model.BaseData;
import my.app.model.Count;

/**
 * Created by hanyu on 2017/3/31 0031.
 */
public class ListViewCountCell extends ListCell<Count> {
    @Override
    public void updateItem(Count count, boolean empty)
    {
        super.updateItem(count,empty);
        if(count != null)
        {
            DataCount dataCount = new DataCount();
            dataCount.setInfo(count);
            setGraphic(dataCount.getBox());
        }
    }
}
