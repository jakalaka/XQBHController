package XQBHController.ControllerUI;


import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.UI.ShowLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private Tab Tab_QueryTran;
    @FXML
    private Tab Tab_QueryStatement;

    @FXML
    private Tab Tab_UpdateStock;
    @FXML
    private Tab Tab_ManualSell;


    /*
    初始化
     */
    public void initialize(URL location, ResourceBundle resources) {


        //生成模型
//        String rootPath = "resources/Model";
//        Model=new MyModel(rootPath,viewPane);
//
//        ControllerUIMain.controller=this;
        //用户登录
//        int i=0;
//        while(true)
//        {
//
//
//            i++;
//            if (i>=3) {
//                System.out.println("err");
//                System.exit(0);
//            }
//        }
        Parent root_Tab_QueryTran = null;
        try {
            root_Tab_QueryTran = FXMLLoader.load(getClass().getResource("ControllerTranUI/QueryTran.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tab_QueryTran.setContent(root_Tab_QueryTran);

        Parent root_Tab_QueryStatement = null;
        try {
            root_Tab_QueryStatement = FXMLLoader.load(getClass().getResource("ControllerTranUI/QueryStatement.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tab_QueryStatement.setContent(root_Tab_QueryStatement);

        Parent root_Tab_UpdateStock = null;
        try {
            root_Tab_UpdateStock = FXMLLoader.load(getClass().getResource("ControllerTranUI/UpdateStock.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tab_UpdateStock.setContent(root_Tab_UpdateStock);

        Parent root_Tab_ManualSell = null;
        try {
            root_Tab_ManualSell = FXMLLoader.load(getClass().getResource("ControllerTranUI/ManualSell.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tab_ManualSell.setContent(root_Tab_ManualSell);





        Com.UIFinish=true;




        //登录
//        Com.sKHDLZH="18984101377";
        ShowLogin showLogin=new ShowLogin();
        showLogin.exec();

    }
    @FXML
    public void onQueryTranEnter() {
        System.out.println(1);
    }
    @FXML
    public void onQueryTranAction() {

        System.out.println(2);
    }
    @FXML
    public void keyPressed(KeyEvent event) {
        //Logger.log("LOG_DEBUG",event);
        if (event.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
    }


}
