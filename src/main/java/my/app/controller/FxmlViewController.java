package my.app.controller;

import com.jfoenix.controls.JFXComboBox;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import my.app.App;
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
@ViewController(value = "/my/app/box/fxml_view.fxml")
public class FxmlViewController implements Initializable {

    private DataSerivce dataSerivce = new DataSerivce();
    private CountService countService = new CountService();

    @FXML
    private LineChart lineChart;
    @FXML
    private LineChart totalLineChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChartTotal;
    @FXML
    private JFXComboBox jfxComboBox;

    private static String[] boxItems = {
            AppConts.CHOICE_TEXT_PERCENTAGE,
            AppConts.CHOICE_TEXT_DATA,
            AppConts.CHOICE_TEXT_COUNT
    };

    /**
     * 初始化函数
     * 根据已有数据初始化图形及信息
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jfxComboBox.getItems().addAll(boxItems);
        jfxComboBox.getSelectionModel().select(0);
        jfxComboBox.getSelectionModel().selectedIndexProperty().addListener((observableValue,value,newValue)->{
            List<BaseData> dataList = dataSerivce.getTempAll();//获取基础数据
            List<Count> countList = countService.getTempAll();//获取计数数据
            if (boxItems[newValue.intValue()].equals(AppConts.CHOICE_TEXT_PERCENTAGE)){
                setPercentage(dataList);
            }else if (boxItems[newValue.intValue()].equals(AppConts.CHOICE_TEXT_DATA)){
                setData(dataList);
            }else if (boxItems[newValue.intValue()].equals(AppConts.CHOICE_TEXT_COUNT)){
                setCount(countList,dataList);
            }
        });
        Platform.runLater(()->{
            List<BaseData> dataList = dataSerivce.getTempAll();//获取基础数据
            List<Count> countList = countService.getTempAll();//获取计数数据

            XYChart.Series series = new XYChart.Series();//初始化一条线 该对象抽象了折线图的一条线
            series.setName(AppConts.LINECHART_LINE_NAME);//设置该线名称

            Map dataMap = dataList
                    .stream()
                    .collect(Collectors.groupingBy(BaseData::getCreateTime,Collectors.counting()));//根据日期对基础数据进行分组查询，聚合同日期类型的总条数

            Map countMap = countList
                    .stream()
                    .collect(Collectors.groupingBy(Count::getCreateTime, Collectors.summingInt(Count::getCount)));//根据日期对计数数据分组查询，并计算同日内计数的和
            Set<Map.Entry<LocalDate,Integer>> set = countMap.entrySet();
            set
                    .stream()
                    .sorted((s1,s2)-> s1.getKey().isBefore(s2.getKey())?-1:1)
                    .forEach(entry->{
                        if (dataMap.containsKey(entry.getKey())){
                            Long dat = (Long) dataMap.get(entry.getKey());
                            try {
                                double sum;
                                if(dat.doubleValue()==0)
                                    sum = 0;
                                else
                                    sum = Arith.div(dat.doubleValue(),entry.getValue().doubleValue(),3);
                                series.getData().add(new XYChart.Data(entry.getKey().format(DateTimeFormatter.ofPattern(AppConts.LOCALDATE_FORMAT_NYR2)),sum));
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }else{
                            series.getData().add(new XYChart.Data(entry.getKey().format(DateTimeFormatter.ofPattern(AppConts.LOCALDATE_FORMAT_NYR2)),0));
                        }
                    });//使用了java 8 新特性 stream 及 lambda表达式对计数数据及基础数据进行计算 得到数据 并设置给线

            lineChart.getData().add(series);//将线放入折线图中

            setPercentage(dataList);

        });
        Platform.runLater(()->{
            List<BaseData> dataList = dataSerivce.getTempAll();//获取基础数据
            List<Count> countList = countService.getTempAll();//获取计数数据

            XYChart.Series series = new XYChart.Series();//初始化一条线 该对象抽象了折线图的一条线
            series.setName(AppConts.LINECHART_LINE_NAME);//设置该线名称

            Map dataMap = dataList
                    .stream()
                    .collect(Collectors.groupingBy(BaseData::getCreateTime,Collectors.counting()));//根据日期对基础数据进行分组查询，聚合同日期类型的总条数

            Map countMap = countList
                    .stream()
                    .collect(Collectors.groupingBy(Count::getCreateTime, Collectors.summingInt(Count::getCount)));//根据日期对计数数据分组查询，并计算同日内计数的和
            Set<Map.Entry<LocalDate,Integer>> set = countMap.entrySet();
            final int[] total = {0,0};
            set
                    .stream()
                    .sorted((s1,s2)-> s1.getKey().isBefore(s2.getKey())?-1:1)
                    .forEach(entry->{//以计数列表为基准进入foreach循环
                        total[1] += entry.getValue().intValue();//计数每次循环累加
                        if (dataMap.containsKey(entry.getKey())){//如果数据map中有这一天数据
                            try {
                                Long dat = (Long) dataMap.get(entry.getKey());
                                total[0] += dat.intValue();
                                double sum = Arith.div(total[0],total[1],3);
                                series.getData().add(new XYChart.Data(entry.getKey().format(DateTimeFormatter.ofPattern(AppConts.LOCALDATE_FORMAT_NYR2)),sum));
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }else{//如果没有当天数据
                            try {
                                double sum = Arith.div(total[0],total[1],3);
                                series.getData().add(new XYChart.Data(entry.getKey().format(DateTimeFormatter.ofPattern(AppConts.LOCALDATE_FORMAT_NYR2)),sum));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                    });//使用了java 8 新特性 stream 及 lambda表达式对计数数据及基础数据进行计算 得到数据 并设置给线

            totalLineChart.getData().add(series);//将线放入折线图中

        });
    }

    private void setPercentage(List<BaseData> dataList){
        pieChart.getData().clear();
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

    private void setData(List<BaseData> dataList){
        pieChart.getData().clear();
        Map<Integer,Long> pieMap = dataList
                .stream()
                .collect(Collectors.groupingBy(BaseData::getType,Collectors.counting()));//根据基础数据的类型进行分组查询
        PieChart.Data slice1 = new PieChart.Data("85 - "+pieMap.get(85), pieMap.get(85));
        PieChart.Data slice2 = new PieChart.Data("90 - "+pieMap.get(90), pieMap.get(90));
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
    }

    private void setCount(List<Count> countList,List<BaseData> dataList){
        pieChart.getData().clear();
        long total = countList.stream().collect(Collectors.summarizingInt(Count::getCount)).getSum();
        int totalData = dataList.size();
        PieChart.Data slice1 = new PieChart.Data("y - "+totalData, totalData);
        PieChart.Data slice2 = new PieChart.Data("w - "+total, total);
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
    }
}
