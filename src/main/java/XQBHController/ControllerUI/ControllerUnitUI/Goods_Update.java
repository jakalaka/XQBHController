package XQBHController.ControllerUI.ControllerUnitUI;


import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.Com.UpdateClientStock;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.ControllerTranUI.UpdateStock;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.Model.DataModel;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Goods_Update implements Initializable {
    @FXML
    private ImageView image;
    @FXML
    private Button add;
    @FXML
    private Button minus;
    @FXML
    private TextField goodsNum;
    @FXML
    private TextField position;
    @FXML
    private TextField ZDBH_U;
    @FXML
    private Label goodsName;
    @FXML
    private HBox hb_goodsNum;

    @FXML
    private GridPane root;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goodsNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                //只能数字
                if (!newValue.matches("\\d*")) {
                    goodsNum.setText(newValue.replaceAll("[^\\d]", ""));
                }

            }
        });
        goodsNum.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                String sZDBH_U=ZDBH_U.getText();
                Logger.log("LOG_DEBUG","ZDBH_U="+ZDBH_U);
                DataModel dataModel=DataModel.getDataModelByKey(UpdateStock.mapDataModel.get(sZDBH_U),position.getText());
                Logger.log("LOG_DEBUG","position.getText()="+position.getText());

                String oldGoodsAccount=dataModel.getGoodsAmount()+"";
                Logger.log("LOG_DEBUG","oldGoodsAccount="+oldGoodsAccount);

                //更新库存信息
                String newValue = goodsNum.getText();
                int dif = Integer.parseInt(newValue) -Integer.parseInt(oldGoodsAccount);
                Logger.log("LOG_DEBUG","dif="+dif);
                try {
                    if (false == UpdateClientStock.exec(ZDBH_U.getText(), position.getText(), dif + "","0")) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                        DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                    }
                } catch (Exception e) {
                    Logger.logException("LOG_ERR", e);
                    WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                    DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                }
                dataModel.setGoodsAmount(Integer.parseInt(goodsNum.getText()));


            }
        });






        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String oldGoodsAccount=goodsNum.getText();
                String newValue = 1 + "";
                DataUtils.setValue(root, "goodsNum", ((Integer.parseInt(oldGoodsAccount) + 1) + ""));
                try {
                    if (false ==UpdateClientStock.exec(ZDBH_U.getText(),position.getText(), newValue,"0")) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                        DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                    }
                } catch (Exception e) {
                    Logger.logException("LOG_ERR", e);
                    WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                    DataUtils.setValue(root, "goodsNum", oldGoodsAccount);

                }
                String sZDBH_U=ZDBH_U.getText();
                DataModel dataModel=DataModel.getDataModelByKey(UpdateStock.mapDataModel.get(sZDBH_U),position.getText());
                dataModel.setGoodsAmount(Integer.parseInt(goodsNum.getText()));

            }
        });
        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String oldGoodsAccount=goodsNum.getText();
                String newValue = (-1) + "";
                DataUtils.setValue(root, "goodsNum", ((Integer.parseInt(oldGoodsAccount) - 1) + ""));
                try {
                    if (false == UpdateClientStock.exec(ZDBH_U.getText(),position.getText(), newValue,"0")) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                        DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                    }

                } catch (Exception e) {
                    Logger.logException("LOG_ERR", e);
                    WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                    DataUtils.setValue(root, "goodsNum", oldGoodsAccount);

                }
                String sZDBH_U=ZDBH_U.getText();
                DataModel dataModel=DataModel.getDataModelByKey(UpdateStock.mapDataModel.get(sZDBH_U),position.getText());
                dataModel.setGoodsAmount(Integer.parseInt(goodsNum.getText()));
            }
        });

    }

}
