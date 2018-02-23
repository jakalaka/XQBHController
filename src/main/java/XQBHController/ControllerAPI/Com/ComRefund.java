package XQBHController.ControllerAPI.Com;


import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.ControllerUI.Order;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class ComRefund {
    public static String exec(String sZFZHLX,String sYHTRQ_,String sYHTLS_,String sTHJYJE) {
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        long startTime = System.currentTimeMillis();
        In.put("ZFZHLX", sZFZHLX);
        In.put("YHTRQ_", sYHTRQ_);
        In.put("YHTLS_", sYHTLS_);
        In.put("THJYJE", sTHJYJE);


        Logger.log("LOG_DEBUG", "sZFZHLX=" + sZFZHLX);
        Logger.log("LOG_DEBUG", "sYHTRQ_=" + sYHTRQ_);
        Logger.log("LOG_DEBUG", "sYHTLS_=" + sYHTLS_);
        Logger.log("LOG_DEBUG", "sTHJYJE=" + sTHJYJE);


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
