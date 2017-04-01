package my.app;

import my.app.model.BaseData;
import my.app.serivce.DataSerivce;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by hanyu on 2017/3/29 0029.
 */
public class TestService {

    @Test
    public void testAdd(){
        BaseData baseData = new BaseData();
        baseData.setType(1);
        baseData.setName("测试");
        baseData.setCreateTime(LocalDate.now());
        baseData.setId(System.currentTimeMillis());
        DataSerivce dataSerivce = new DataSerivce();
        dataSerivce.add(baseData);

    }

    @Test
    public void testDelete(){
        DataSerivce dataSerivce = new DataSerivce();
        BaseData baseData = dataSerivce.getTempAll().get(0);
        dataSerivce.delete(baseData);
        dataSerivce.commitAll();

    }
}
