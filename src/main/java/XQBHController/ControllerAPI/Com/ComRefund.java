package XQBHController.ControllerAPI.Com;


import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.ControllerUI.Order;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class ComRefund {
    public static String exec(String sHTRQ_U,String sHTLS_U,String sJYJE_U) {
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        long startTime = System.currentTimeMillis();
        In.put("HTRQ_U", sHTRQ_U);
        In.put("HTLS_U", sHTLS_U);
        In.put("JYJE_U", sJYJE_U);

        Logger.log("LOG_DEBUG", "sHTRQ_U=" + sHTRQ_U);
        Logger.log("LOG_DEBUG", "sHTLS_U=" + sHTLS_U);
        Logger.log("LOG_DEBUG", "sJYJE_U=" + sJYJE_U);


        if (false == ComCall.Call("ComRefund", "ControllerRefund", In, Out)) {
            return DataUtils.getValue(Out,"CWXX_U");
        } else
            Logger.log("LOG_DEBUG", "re=" + Out.get("CWDM_U"));
        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "spand " + (endTime - startTime) + "ms");


        Logger.log("LOG_IO", Com.getOut);
        return "SUCCESS";
    }
}
