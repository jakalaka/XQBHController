package XQBHController.FreeTest;

import XQBHController.Controller.ControllerInit;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerAPI.Com.AlipayZFWAITQuery;
import XQBHController.ControllerUI.Order;

public class querytest {
    public static void main(String[] args) {
        if (false == ControllerInit.Init()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ʼ��ʧ�ܣ�����ϵά����Ա");
            return;
        }
        Order.HTRQ_U = "20171218";
            Order.HTLS_U = "SZD0000010000003";
//        while (true) {

//            System.out.println(1);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(2);

                AlipayZFWAITQuery.exec();


            System.out.println(3);
//        }
    }
}
