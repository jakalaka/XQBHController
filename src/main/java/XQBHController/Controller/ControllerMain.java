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
        ������
         */
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡ����ʧ�ܣ����������Ƿ�ͨ��");

            return;
        }

        /*
        ��ʼ������
         */
        if (false == ControllerInit.Init())
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ʼ��ʧ�ܣ�����ϵά����Ա");
            return;
        }
        /*
        �̻�ǩ��
         */
//        if (!ZDLogin.exec())
//        {
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ն�ǩ��ʧ�ܣ�����ϵά����Ա");
//            return;
//        }






        /*
        ��������
         */
        ControllerUIMain.main(args);


    }

}
