package XQBHController.ControllerUI.ControllerAPIUI;


import XQBHController.Controller.Com;
import XQBHController.Utils.Modbus.ModbusUtil;
import XQBHController.Utils.QRReader.QRReader;
import XQBHController.Utils.log.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class WarmingDialogController {

    public Stage stage;
    private Scene scene;


    @FXML
    public Label warmingInfo;

    @FXML
    public Label warmingTitle;

    @FXML
    public void OK() {
        Logger.log("LOG_DEBUG", "OK");
        if (Com.UIFinish)
            Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        else
            System.exit(0);

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
