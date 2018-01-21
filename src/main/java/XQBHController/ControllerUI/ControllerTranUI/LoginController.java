package XQBHController.ControllerUI.ControllerTranUI;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.Com.KHLogin;
import XQBHController.ControllerAPI.Com.SHLogin;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController {
    public Stage stage;
    private Scene scene;
    @FXML
    private RadioButton RB_Customer;
    @FXML
    private RadioButton RB_Merchant;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passWord;
    @FXML
    private Label warmLabel;
    @FXML
    private Button OK;


    @FXML
    public void userLogin(ActionEvent actionEvent) {
        OK.setDisable(true);

        String []sCWXX_U = new String[1];
        warmLabel.setText("");
        //这里写登录的东西
        if (RB_Customer.isSelected()) {
            if (!KHLogin.exec(userName.getText(),passWord.getText(),sCWXX_U)) {
                warmLabel.setText(sCWXX_U[0]);
                OK.setDisable(false);
                return;
            }else{
                Com.isLogin=true;
                Com.sKHDLZH =userName.getText();
                Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

            }
        } else {
            if (!SHLogin.exec(userName.getText(),passWord.getText(),sCWXX_U)) {
                warmLabel.setText(sCWXX_U[0]);
                OK.setDisable(false);
                return;
            }else{
                Com.isLogin=true;
                Com.sSHBH_U=userName.getText();
                Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        }


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
