package XQBHController.Controller;


import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.ControllerUIMain;
import XQBHController.Utils.Updater.AutoUpdateMain;

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
