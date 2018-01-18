package XQBHController.Controller;


import XQBHController.ControllerAPI.WarmingDialog;
import XQBHController.ControllerTran.ZDLogin;
import XQBHController.ControllerUI.ControllerUIMain;
import XQBHController.Utils.FinishComListener.FinishComListener;
import XQBHController.Utils.Updater.AutoUpdateMain;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
public class ControllerMain {

    public static void main(String[] args) {

        /*
        检查更新
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取更新失败，请检查网络是否通畅");

            return;
        }

        /*
        初始化程序
         */
        if (false == ControllerInit.Init())
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "初始化失败，请联系维护人员");
            return;
        }
        /*
        商户签到
         */
//        if (!ZDLogin.exec())
//        {
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "终端签到失败，请联系维护人员");
//            return;
//        }






        /*
        启动界面
         */
        ControllerUIMain.main(args);


    }

}
