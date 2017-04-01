package my.app.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import my.app.box.AppConts;
import my.app.model.BaseData;
import my.app.model.Count;
import my.app.serivce.CountService;
import my.app.serivce.DataSerivce;
import my.app.util.Arith;
import my.app.util.GsonUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据图形化控制器
 * Created by hanyu on 2017/3/30 0030.
 */
public class FxmlViewController extends FxmlBase implements Initializable {

    private DataSerivce dataSerivce = new DataSerivce();
    private CountService countService = new CountService();

    @FXML
    private LineChart lineChart;
    @FXML
    private PieChart pieChart;

    /**
     * 初始化函数
     * 根据已有数据初始化图形及信息
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<BaseData> dataList = dataSerivce.getTempAll();//获取基础数据
        List<Count> countList = countService.getTempAll();//获取计数数据

        XYChart.Series series = new XYChart.Series();//初始化一条线 该对象抽象了折线图的一条线
        series.setName(AppConts.LINECHART_LINE_NAME);//设置该线名称

        Map dataMap = dataList
                .stream()
                .collect(Collectors.groupingBy(BaseData::getCreateTime,Collectors.counting()));//根据日期对基础数据进行分组查询，聚合同日期类型的总条数
        Set<Map.Entry<LocalDate,Long>> dataSet = dataMap.entrySet();

        Map countMap = countList
                .stream()
                .collect(Collectors.groupingBy(Count::getCreateTime, Collectors.summingInt(Count::getCount)));//根据日期对计数数据分组查询，并计算同日内计数的和
        Set<Map.Entry<LocalDate,Integer>> set = countMap.entrySet();
        set
                .stream()
                .sorted((s1,s2)-> s1.getKey().isBefore(s2.getKey())?-1:1)
                .forEach(entry->{
                    dataSet.forEach(e->{
                        if (entry.getKey().equals(e.getKey())){
                            try {
                                double sum = Arith.div(e.getValue().doubleValue(),entry.getValue().doubleValue(),3);
                                series.getData().add(new XYChart.Data(entry.getKey().format(DateTimeFormatter.ofPattern(AppConts.LOCALDATE_FORMAT_NYR2)),sum));
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

        });//使用了java 8 新特性 stream 及 lambda表达式对计数数据及基础数据进行计算 得到数据 并设置给线

        lineChart.getData().add(series);//将线放入折线图中

        Map<Integer,Long> pieMap = dataList
                .stream()
                .collect(Collectors.groupingBy(BaseData::getType,Collectors.counting()));//根据基础数据的类型进行分组查询

        /*计算两种类型数据总数的占比，并设置到饼状图中*/
        int total = (int) (pieMap.get(85)+pieMap.get(90));
        try {
            double a = Arith.div(pieMap.get(85).doubleValue(),total,2);
            double b = Arith.div(pieMap.get(90).doubleValue(),total,2);
            PieChart.Data slice1 = new PieChart.Data("85 - "+Arith.mul(a,100)+"%", a);
            PieChart.Data slice2 = new PieChart.Data("90 - "+Arith.mul(b,100)+"%", b);
            pieChart.getData().add(slice1);
            pieChart.getData().add(slice2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void changeSize() {
    }
}
