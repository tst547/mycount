package my.app.serivce;

import my.app.source.AppBaseData;
import my.app.model.BaseData;
import my.app.source.DataSourceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyu on 2017/3/29 0029.
 */
public class DataSerivce extends ServiceBase<BaseData>{

    @Override
    protected DataSourceBase<BaseData> getDataSource() {
        return AppBaseData.getAppBaseData();
    }
}
