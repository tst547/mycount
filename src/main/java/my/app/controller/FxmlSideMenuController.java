package my.app.controller;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Created by Administrator on 2017/4/10 0010.
 */
@ViewController(value = "/my/app/box/fxml_side_menu.fxml")
public class FxmlSideMenuController {

    @FXML
    @ActionTrigger("data")
    private Label dataBtn;

    @FXML
    @ActionTrigger("count")
    private Label countBtn;

    @FXML
    @ActionTrigger("view")
    private Label viewBtn;

    @FXML
    private JFXListView<Label> sideList;

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @PostConstruct
    public void init(){
        Objects.requireNonNull(context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) context.getRegisteredObject("ContentFlowHandler");
        sideList.propagateMouseEventsToParent();
        sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    contentFlowHandler.handle(newVal.getId());
                } catch (VetoException exc) {
                    exc.printStackTrace();
                } catch (FlowException exc) {
                    exc.printStackTrace();
                }
            }
        });
        Flow contentFlow = (Flow) context.getRegisteredObject("ContentFlow");
        bindNodeToController(dataBtn, FxmlDataController.class, contentFlow, contentFlowHandler);
        bindNodeToController(countBtn, FxmlCountController.class, contentFlow, contentFlowHandler);
        bindNodeToController(viewBtn, FxmlViewController.class, contentFlow, contentFlowHandler);
    }

    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}
