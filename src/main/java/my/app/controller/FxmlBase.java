package my.app.controller;

import javafx.stage.Stage;

/**
 * Controller继承该类 在界面初始化时能将
 * stage赋值到Controller中 并且提供根据fxml宽高
 * 来调整自身窗体大小的函数（需要子类实现）
 * Created by hanyu on 2017/3/30 0030.
 */
public abstract class FxmlBase {

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public abstract void changeSize();
}
