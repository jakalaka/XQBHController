package XQBHController.ControllerAPI.Com;


import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.ControllerUI.Order;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class CashPayBill {
    public static String exec() {
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        long startTime = System.currentTimeMillis();
        In.put("SPMC_U", Order.SPMC_U);
        In.put("JYJE_U", Order.JYJE_U);

        Logger.log("LOG_DEBUG", "SPMC_U=" + Order.SPMC_U);
        Logger.log("LOG_DEBUG", "JYJE_U=" + Order.JYJE_U);


        if (false == ComCall.Call("CashPay", "CashPay", In, Out)) {
            return DataUtils.getValue(Out,"CWXX_U");
        } else
            Logger.log("LOG_DEBUG", "re=" + Out.get("re"));
        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "spand " + (endTime - startTime) + "ms");


        Logger.log("LOG_IO", Com.getOut);
        return "SUCCESS";
    }
}
