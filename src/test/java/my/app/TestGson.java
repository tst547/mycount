package my.app;

import junit.framework.TestCase;
import my.app.model.BaseData;
import my.app.serivce.DataSerivce;
import my.app.util.GsonUtil;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hanyu on 2017/3/29 0029.
 */
public class TestGson extends TestCase{

    @Test
    public void testListGson(){
        List<BaseData> list = new ArrayList<BaseData>();
        int i = 0;
        while(i<5){
            BaseData data = new BaseData();
            data.setId(System.currentTimeMillis());
            data.setCreateTime(LocalDate.now());
            data.setName("for"+i);
            data.setType(1);
            list.add(data);
            i++;
        }
        System.out.print(GsonUtil.GsonString(list));
    }


}
