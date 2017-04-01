package my.app.source;

import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.model.Count;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyu on 2017/3/30 0030.
 */
public class AppCountData extends DataSourceBase<Count>{

    private static AppCountData appCountData;

    public static AppCountData getAppBaseData(){
        if (null==appCountData)
            appCountData = new AppCountData();
        return appCountData;
    }

    @Override
    public String getFileSuffix() {
        return AppConts.FILE_COUNT_SUFFIX;
    }

    @Override
    public Class<Count> getDataClazz() {
        return Count.class;
    }

    @Override
    public Map<Long, Count> getDataSourceMap(List<Count> dataSource) {
        Map<Long,Count> map = new HashMap<>();
        dataSource.stream().forEach((count)->map.put(count.getId(),count));
        return map;
    }

    public AppCountData(){
        super();
    }
}
