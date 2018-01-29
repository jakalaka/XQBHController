package XQBHController.ControllerUI.ControllerAPIUI;

import XQBHController.ControllerAPI.Com.GetZDModel;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.ControllerTranUI.UpdateStock;
import XQBHController.Utils.Model.DataModel;
import XQBHController.Utils.log.Logger;

public class GetLastModelFromZD {
    public static DataModel exec(String sZDBH_U){
        DataModel dataModel = null;
        try {
            dataModel = GetZDModel.exec(sZDBH_U);
        } catch (Exception e) {
            Logger.logException("LOG_DEBUG",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取终端" + sZDBH_U + "的商品数量失败!!!");
            return null;
        }
        if (dataModel == null) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取终端" + sZDBH_U + "的商品数量失败!!!");
            return null;
        }
        UpdateStock.mapDataModel.put(sZDBH_U,dataModel);
        return dataModel;
    }
}
