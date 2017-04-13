package my.app.serivce;

import my.app.model.BaseData;
import my.app.model.BaseModel;
import my.app.source.AppBaseData;
import my.app.source.DataSourceBase;
import my.app.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyu on 2017/3/30 0030.
 */
public abstract class ServiceBase<T extends BaseModel> {

    protected abstract DataSourceBase<T> getDataSource();
    /**
     * 获取所有数据临时列表 操作该列表对基础数据不会造成影响
     * @return
     */
    public List<T> getTempAll(){
        return new ArrayList<T>(getDataSource().getBaseDataSource());
    }

    /**
     * 新增数据
     * @param t
     */
    public void add(T t){
        getDataSource().getDataSource().add(t);
        getDataSource().commit();
        int []as = {104};
    }

    /**
     * 删除数据
     * @param t
     */
    public void delete(T t){
        T t1 = getDataSource().getDataMap().get(t.getId());
        List list = getDataSource().getBaseDataSource();
        list.remove(t1);
    }

    /**
     * 对删除数据操作进行提交 将导致已删除的数据无法复原
     */
    public void commitAll(){
        getDataSource().commitAll();
    }
}
