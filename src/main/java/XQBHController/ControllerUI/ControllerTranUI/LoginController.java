package XQBHController.ControllerUI.ControllerTranUI;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.Com.KHLogin;
import XQBHController.ControllerAPI.Com.SHLogin;
import XQBHController.Utils.Data.DataUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
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
                Com.sQTDX_U="kh";
                Com.sKHDLZH =userName.getText();
                Com.sSHBH_U="";
                Com.isLogin=true;
                Event.fireEvent(stage, new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        } else {

            if (!SHLogin.exec(userName.getText(),passWord.getText(),sCWXX_U)) {
                warmLabel.setText(sCWXX_U[0]);
                OK.setDisable(false);
                return;
            }else{
                Com.sQTDX_U="sh";
                Com.sKHDLZH="";
                Com.sSHBH_U=userName.getText();
                Com.isLogin=true;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataUtils.setValue(userName,"userName","18984101377");
        DataUtils.setValue(passWord,"passWord","123321");
        RB_Customer.setSelected(true);


    }
}
