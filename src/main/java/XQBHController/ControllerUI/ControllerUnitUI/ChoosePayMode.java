package XQBHController.ControllerUI.ControllerUnitUI;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;


public class ChoosePayMode implements Initializable{
    public static String resoult;

    public Stage stage;

    @FXML
    private Button getGoods;
    @FXML
    private Button AliPay;
    @FXML
    private Button WechatPay;
    @FXML
    private Button cashPay;
    @FXML
    private Button cancel;

    public EventHandler eventHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            Object eventObj = event.getSource();

            if (eventObj instanceof Button) {
                if ("getGoods".equals(((Button) eventObj).getId())) {
                    resoult="getGoods";
                }else if("AliPay".equals(((Button) eventObj).getId())){
                    resoult="AliPay";
                }else if("WechatPay".equals(((Button) eventObj).getId())){
                    resoult="WechatPay";
                }else if("cashPay".equals(((Button) eventObj).getId())){
                    resoult="cashPay";
                }
                Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

            }
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resoult="";
        getGoods.setOnAction(eventHandler);
        AliPay.setOnAction(eventHandler);
        WechatPay.setOnAction(eventHandler);
        cashPay.setOnAction(eventHandler);
        cancel.setOnAction(eventHandler);


    }

}
