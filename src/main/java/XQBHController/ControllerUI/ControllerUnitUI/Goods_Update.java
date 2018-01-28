package XQBHController.ControllerUI.ControllerUnitUI;


import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.Data.DataUtils;
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
    private Label goodsName;
    @FXML
    private HBox hb_goodsNum;

    @FXML
    private GridPane root;

    private String oldGoodsAccount = "";


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
                oldGoodsAccount = oldValue;
            }
        });
        goodsNum.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                //更新库存信息
                String newValue = goodsNum.getText();
                int dif = Integer.parseInt(newValue) - Integer.parseInt(oldGoodsAccount);
                System.out.println("dif="+dif);
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

            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String newValue = 1 + "";
                DataUtils.setValue(root, "goodsNum", ((Integer.parseInt(goodsNum.getText()) + 1) + ""));
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

            }
        });
        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String newValue = (-1) + "";
                DataUtils.setValue(root, "goodsNum", ((Integer.parseInt(goodsNum.getText()) - 1) + ""));
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
            }
        });

    }

    private boolean updateClientStock(String sKey, String goodsAccount) throws IOException {
        System.out.println("sKey=" + sKey + " goodsAccount=" + goodsAccount);
        String sIP = "192057t6r9.iask.in";
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
        System.out.println(info);
        Map mapOut = XmlUtils.XML2map(info);
        if (!"AAAAAA".equals(mapOut.get("CWDM_U")))
            return false;

        return true;
    }
}
