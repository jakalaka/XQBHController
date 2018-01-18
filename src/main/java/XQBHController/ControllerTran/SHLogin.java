package XQBHController.ControllerTran;

import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class SHLogin {
    public static boolean exec(String sSHBH_U,String sSHMM_U,String []sMessage) {
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        long startTime = System.currentTimeMillis();

        In.put("SHBH_U",sSHBH_U);
        In.put("SHMM_U",sSHMM_U);
        if (false == ComCall.Call("SHLogin", "SHLogin", In, Out)) {
            sMessage[0]=Out.get("CWXX_U").toString();
            return false;
        } else
            Logger.log("LOG_DEBUG", "re="+Out.get("re") );



        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "spand " + (endTime - startTime) + "ms");
        Logger.log("LOG_IO", Com.getOut);



        return true;
    }
}
