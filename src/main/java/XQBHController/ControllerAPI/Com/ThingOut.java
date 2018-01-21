package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.Order;
import XQBHController.Utils.Modbus.ModbusUtil;
import XQBHController.Utils.log.Logger;

public class ThingOut {
    public static boolean exec() throws Exception{
        Order.outFail = false;
        try {
            ModbusUtil.doThingsOut(Order.controllerIP, Order.controllerPort, Order.controllerAdress);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "ִ�г�������!");
            Order.outFail = true;
            return false;
        }
        int time = 0;
        while (!Order.finalOut) {//30��û�����ʹ������װ�ô���
            Thread.sleep(1000);
            time++;
            if (time > 30) {
                Com.ZDZT_U = "ERR_ERR_OutNull";
                Order.outFail = true;
                return false;
            }
        }
        return true;
    }
}
