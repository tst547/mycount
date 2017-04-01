package my.app.source;


import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.util.BinaryFile;
import my.app.util.GsonUtil;
import my.app.util.JarToolUtil;
import my.app.util.TextFile;

import java.io.File;
import java.util.*;

/**
 * Created by hanyu on 2017/3/29 0029.
 */
public class AppBaseData extends DataSourceBase<BaseData>{

    private static AppBaseData appBaseData;

    public static AppBaseData getAppBaseData(){
        if (null==appBaseData)
            appBaseData = new AppBaseData();
        return appBaseData;
    }

    @Override
    public String getFileSuffix() {
        return AppConts.FILE_DATA_SUFFIX;
    }

    @Override
    public Class getDataClazz() {
        return BaseData.class;
    }

    @Override
    public Map getDataSourceMap(List<BaseData> dataSource) {
        Map<Long,BaseData> map = new HashMap<>();
        dataSource.stream().forEach((baseData)->map.put(baseData.getId(),baseData));
        return map;
    }

    public AppBaseData(){
        super();
    }
}
