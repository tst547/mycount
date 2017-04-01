package my.app.model;

import java.time.LocalDate;
import java.util.Date;

/**
 * 基础数据
 * Created by hanyu on 2017/3/29 0029.
 */
public class BaseData extends BaseModel{

    private int type;
    private LocalDate createTime;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }
}
