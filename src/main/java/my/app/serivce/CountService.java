package my.app.serivce;

import my.app.model.Count;
import my.app.source.AppCountData;
import my.app.source.DataSourceBase;

/**
 * Created by hanyu on 2017/3/30 0030.
 */
public class CountService extends ServiceBase<Count>{


    @Override
    protected DataSourceBase<Count> getDataSource() {
        return AppCountData.getAppBaseData();
    }
}
