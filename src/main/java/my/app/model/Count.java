package my.app.model;

import java.time.LocalDate;

/**
 * 计数数据
 * Created by hanyu on 2017/3/30 0030.
 */
public class Count extends BaseModel{

    private LocalDate createTime;
    private int count;

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
