package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.Utils.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class KHLogin {
    public static boolean exec(String sKHDLZH,String sKHMM_U,String []sMessage) {
        Logger.log("LOG_IO", Com.getIn);
        Map In = new HashMap();
        Map Out = new HashMap();
        long startTime = System.currentTimeMillis();

        In.put("KHDLZH",sKHDLZH);
        In.put("KHMM_U",sKHMM_U);
        if (false == ComCall.Call("KHLogin", "KHLogin", In, Out)) {
            sMessage[0]=Out.get("CWXX_U").toString();
            return false;
        } else
            Logger.log("LOG_DEBUG", "re="+Out.get("re") );
        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "spand " + (endTime - startTime) + "ms");

        //将对应的商户信息，终端信息写到内存中
        String sSHXX_U=Out.get("SHXX_U").toString();
        String sZDXX_U=Out.get("ZDXX_U").toString();
        for (String string:sSHXX_U.split("\\|"))
        {
            Com.listSHXX_U.add(string);
            Logger.log("LOG_DEBUG","Com.listSHXX_U ADD "+string);

        }
        for (String string:sZDXX_U.split("\\|"))
        {
            Com.listZDXX_U.add(string);
            Logger.log("LOG_DEBUG","Com.listZDXX_U ADD "+string);
        }

        Logger.log("LOG_IO", Com.getOut);


        return true;
    }
}
