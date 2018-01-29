package XQBHController.ControllerUI.ControllerUnitUI;

import XQBHController.Controller.Com;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.Model.DataModel;
import XQBHController.Utils.log.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Obj_Goods_Update {
    public Parent SP = null;

    public Obj_Goods_Update(String sZDBH_U, DataModel dataModel) {
        try {
            SP = FXMLLoader.load(getClass().getResource("../ControllerUnitUI/Goods_Update.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SP.setId(dataModel.getPosition());
        DataUtils.setValue(SP, "goodsName", dataModel.getModelName());
        DataUtils.setValue(SP, "goodsNum",dataModel.getGoodsAmount()+"");
        DataUtils.setValue(SP, "position", dataModel.getPosition());
        DataUtils.setValue(SP, "ZDBH_U", sZDBH_U);

        String imagePath = "resources/"+sZDBH_U+"/"+dataModel.getImgs()[0];
        File file = new File(imagePath);
        Image image = null;
        image = new Image(String.valueOf(file.toURI()));
        Logger.log("LOG_DEBUG",String.valueOf(file.toURI()));
        ((ImageView) DataUtils.getTarget(SP, "image")).setImage(image);


//        setLabel();

    }


}
