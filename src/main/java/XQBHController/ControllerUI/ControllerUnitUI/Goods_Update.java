package XQBHController.ControllerUI.ControllerUnitUI;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Goods_Update implements Initializable {
    @FXML
    private ImageView image;
    @FXML
    private Button add;
    @FXML
    private Button minus;
    @FXML
    private TextField goodsNum ;
    @FXML
    private Label goodsName;
    @FXML
    private HBox hb_goodsNum;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
