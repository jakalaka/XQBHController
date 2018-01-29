package XQBHController.ControllerUI.ControllerUnitUI;


import XQBHController.Controller.Com;
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
                    if (false == updateClientStock(position.getText(), dif + "")) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                        DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                    }
                } catch (IOException e) {
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
                    if (false == updateClientStock(position.getText(), newValue)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                        DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                    }
                } catch (IOException e) {
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
                    if (false == updateClientStock(position.getText(), newValue)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新库存信息出错!!!");
                        DataUtils.setValue(root, "goodsNum", oldGoodsAccount);
                    }

                } catch (IOException e) {
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

    private boolean updateClientStock(String sKey, String goodsAccount) throws IOException {
        String sIP = "";
        for (Map map:
             Com.listSH_ZDXX) {
            String sZDBH_U=map.get("ZDBH_U").toString();
            if (sZDBH_U.equals(ZDBH_U.getText())) {
                sIP = map.get("IP_UUU").toString();
                break;
            }
        }

        Logger.log("LOG_DEBUG", "IP=[" + sIP + "] Port=[" + 9001 + "]");
        Socket socket = new Socket(sIP, 9001);
//2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
        Map xmlMapIn = new HashMap();
        xmlMapIn.put("FUNCTION", "updateClientStock");
        xmlMapIn.put("position", sKey);
        xmlMapIn.put("goodsAccount", goodsAccount);

        String sXmlIn = XmlUtils.map2XML(xmlMapIn);
        pw.write(sXmlIn);
        pw.flush();
        socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String info = null;
        info = br.readLine();

        //4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
        Logger.log("LOG_DEBUG",info);
        Map mapOut = XmlUtils.XML2map(info);
        if (!"AAAAAA".equals(mapOut.get("CWDM_U")))
            return false;

        return true;
    }
}
