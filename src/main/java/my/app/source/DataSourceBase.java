package my.app.source;

import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.util.GsonUtil;
import my.app.util.StrUtil;
import my.app.util.TextFile;

import java.io.File;
import java.util.*;

/**
 * Created by hanyu on 2017/3/30 0030.
 */
public abstract class DataSourceBase<T> {

    protected String fileSuffix;

    protected File root;

    protected volatile List<T> dataSource;

    protected volatile Map<Long,T> dataMap;

    protected volatile List<T> increment;

    /**
     * 指定文件后缀
     * @return
     */
    public abstract String getFileSuffix();

    /**
     * 指定clazz用于Gson
     * @return
     */
    public abstract Class<T> getDataClazz();

    /**
     * 原始数据填充到map中，以id为key
     * @param dataSource
     * @return
     */
    public abstract Map<Long,T> getDataSourceMap(List<T> dataSource);

    /**
     * 构造函数
     */
    public DataSourceBase(){
        //root = new File(JarToolUtil.getJarDir());
        root = new File(AppConts.TestFileRoot);
        fileSuffix = getFileSuffix();
        if (!root.exists())
            root.mkdirs();
        String data = readData();
        dataSource = GsonUtil.GsonToList(data, getDataClazz());
        dataMap = getDataSourceMap(dataSource);
        increment = new ArrayList<>();
    }

    /**
     * 获取基本数据
     * @return
     */
    public List<T> getBaseDataSource(){
        return dataSource;
    }

    /**
     * 获取操作数据
     * @return
     */
    public List<T> getDataSource(){
        return increment;
    }

    /**
     * 获取总数据Map形式
     * 键为 id
     * @return
     */
    public Map<Long,T> getDataMap(){
        return dataMap;
    }

    /**
     * 将临时数据持久化
     */
    public synchronized void commit(){
        writeDate(true);
        dataSource.addAll(increment);
        increment.clear();
    }

    /**
     * 将所有数据持久化
     */
    public synchronized void commitAll(){
        writeDate(false);
        dataSource.addAll(increment);
        increment.clear();
    }

    /**
     * 读取所有数据
     * @return
     */
    private String readData(){
        StringBuilder sbd = new StringBuilder();
        File[] list = root.listFiles((dir, name)->name.endsWith(fileSuffix));
        if (null != list){
            for (File file : list){
                sbd.append(TextFile.read(file.getAbsolutePath()));
            }
        }
        String data = StrUtil.trimSubstring(sbd);
        sbd.setLength(0);
        sbd.append(data);
        if (data.startsWith(","))
            sbd.delete(0,1);
        sbd.append("]");
        sbd.insert(0,"[");
        return sbd.toString();
    }

    /**
     * 写入增量数据 或将总数据全部刷新写入
     * @param isIncrement
     */
    private synchronized void writeDate(Boolean isIncrement){
        StringBuilder sbd = new StringBuilder(isIncrement?GsonUtil.GsonString(increment):GsonUtil.GsonString(dataSource));
        sbd.insert(sbd.indexOf("[")," ");
        sbd.insert(sbd.indexOf("]")," ");
        sbd.deleteCharAt(sbd.indexOf("["));
        sbd.deleteCharAt(sbd.indexOf("]"));
        clearData(!isIncrement);
        File[] list = root.listFiles((dir, name)->name.endsWith(fileSuffix));
        File file = null;
        if (list.length>0){
            file = Arrays.stream(list).sorted((o1, o2)->
                    o1.getTotalSpace()>o2.getTotalSpace()?1:o1.getTotalSpace()<o2.getTotalSpace()?-1:0)
                    .findFirst()
                    .get();
        }else {
            file = new File(root.getAbsolutePath()+"/"+System.currentTimeMillis()+fileSuffix);
        }
        if (file.getTotalSpace()!=0)
            if (isIncrement)
                sbd.insert(0,",");
        TextFile.write(file.getAbsolutePath(),isIncrement,sbd.toString());

    }

    /**
     * 在刷新所有数据时清洗已
     * 持久化的所有数据
     * @param isClear
     */
    private void clearData(boolean isClear){
        if (!isClear)
            return;
        File[] list = root.listFiles();
        if (null != list) {
            for (File file : list) {
                TextFile.write(file.getAbsolutePath(), false, AppConts.SPACE);
            }
        }
    }


}
