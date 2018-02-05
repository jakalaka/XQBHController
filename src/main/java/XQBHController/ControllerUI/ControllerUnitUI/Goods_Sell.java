package XQBHController.ControllerUI.ControllerUnitUI;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.Com.CashPayBill;
import XQBHController.ControllerAPI.Com.UpdateClientStock;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.ControllerAPIUI.WarmingDialogController;
import XQBHController.ControllerUI.ControllerTranUI.UpdateStock;
import XQBHController.ControllerUI.ControllerUIMain;
import XQBHController.ControllerUI.Order;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.Modbus.ModbusUtil;
import XQBHController.Utils.Model.DataModel;
import XQBHController.Utils.log.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Goods_Sell implements Initializable {
    @FXML
    private GridPane root;
    @FXML
    private TextField ZDBH_U;
    @FXML
    private TextField position;



    @FXML
    private Button sell;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ("sh".equals(Com.sQTDX_U))
                {
                    WarmingDialog.show(WarmingDialog.Dialog_ERR,"商户不允许销售，请更换为柜员或客户");
                    return;
                }

                //写销售
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Goods_Sell.class.getResource("ChoosePayMode.fxml"));

                try {
                    loader.load();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Parent chooseroot = loader.getRoot();
                Scene scene = new Scene(chooseroot);
                Stage stage_dialog = new Stage(StageStyle.UNDECORATED);
                stage_dialog.initModality(Modality.WINDOW_MODAL);
                stage_dialog.setScene(scene);
                stage_dialog.setMaximized(false);
//                scene.setFill(Color.TRANSPARENT);
//                stage_dialog.initStyle(StageStyle.TRANSPARENT);
                stage_dialog.initOwner(ControllerUIMain.primaryStage);
                stage_dialog.setTitle("请选择...");

                Image image=new Image("resources/img/cio.jpg");
                stage_dialog.getIcons().add(image);
                ChoosePayMode choosePayMode=loader.getController();
                choosePayMode.stage=stage_dialog;
                stage_dialog.showAndWait();

                if("".equals(ChoosePayMode.resoult))
                {

                }else if("getGoods".equals(ChoosePayMode.resoult))
                {

                    //取货
                    String goodsNum=DataUtils.getValue(root,"goodsNum");
                    if (!DataUtils.isNumber(goodsNum))
                    {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR,"库存错误=["+goodsNum+"]");
                    }
                    int igoodNum=Integer.parseInt(goodsNum);
//
//                    try {
//                        Logger.log("LOG_DEBUG","IP="+DataUtils.getValue(root,"IP_UUU")+"  PORT_U="+DataUtils.getValue(root,"PORT_U")+"  ADRESS="+DataUtils.getValue(root,"ADRESS"));
//                        ModbusUtil.doThingsOut(DataUtils.getValue(root,"IP_UUU"), Integer.parseInt(DataUtils.getValue(root,"PORT_U")),Integer.parseInt(DataUtils.getValue(root,"ADRESS")));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Logger.logException("LOG_ERR", e);
//                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "执行出货错误!");
//                        return;
//                    }
                    //通知客户端减库存
                    String oldGoodsAccount=goodsNum;
                    String newValue = (-1) + "";
                    DataUtils.setValue(root, "goodsNum", ((igoodNum - 1) + ""));
                    try {
                        if (false == UpdateClientStock.exec(ZDBH_U.getText(),position.getText(), newValue,"1")) {
                            WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                            DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                            return;
                        }

                    } catch (Exception e) {
                        Logger.logException("LOG_ERR", e);
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                        DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                        return;
                    }
                    String sZDBH_U=ZDBH_U.getText();
                    DataModel dataModel=DataModel.getDataModelByKey(UpdateStock.mapDataModel.get(sZDBH_U),position.getText());
                    dataModel.setGoodsAmount(igoodNum - 1);

                }else if("AliPay".equals(ChoosePayMode.resoult))
                {

                    //支付宝
                    Order.Init();


                }else if("WechatPay".equals(ChoosePayMode.resoult))
                {
                    Order.Init();


                }else if("cashPay".equals(ChoosePayMode.resoult))
                {


                    Order.Init();
                    Order.SPMC_U=DataUtils.getValue(root,"goodsName");
                    Order.JYJE_U=Double.parseDouble(DataUtils.getValue(root,"unitPrice"));
                    Order.callStatus = CashPayBill.exec();
                    String CWXX_U;
                    if (!(CWXX_U=Order.callStatus).equals("SUCCESS"))
                    {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR,CWXX_U+"!\n给您带来不便请您谅解!");
                        return;
                    }else {
                        WarmingDialog.show(WarmingDialog.Dialog_OVER,"记录账务成功!");
                    }
                }


            }
        });

    }
}
