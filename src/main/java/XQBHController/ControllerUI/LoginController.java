package XQBHController.ControllerUI;

import XQBHController.ControllerAPI.WarmingDialog;
import XQBHController.ControllerTran.SHLogin;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;

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
    public void userLogin(ActionEvent actionEvent) {
        String []sCWXX_U = new String[1];
        warmLabel.setText("");
        //这里写登录的东西
        if (RB_Customer.isSelected()) {

        } else {
            if (!SHLogin.exec(userName.getText(),passWord.getText(),sCWXX_U)) {
                warmLabel.setText(sCWXX_U[0]);
                return;
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
