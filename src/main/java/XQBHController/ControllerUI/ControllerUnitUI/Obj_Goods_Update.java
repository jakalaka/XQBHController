package XQBHController.ControllerUI.ControllerUnitUI;

import XQBHController.Utils.Data.DataUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class Obj_Goods_Update {
    public Parent SP = null;

    public Obj_Goods_Update(String goodsName, int goodsNum, String image) {
        try {
            SP = FXMLLoader.load(getClass().getResource("../ControllerUnitUI/Goods_Update.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataUtils.setValue(SP,"hb_goodsNum",goodsName);
        setLabel();

    }
    private  void setLabel(String ID,String Value)
    {

    }
}
