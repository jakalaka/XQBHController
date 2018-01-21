package XQBHController.ControllerAPI.Com;

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



        //将对应的商户信息，终端信息写到内存中
        String sZDXX_U=Out.get("ZDXX_U").toString();

        Com.listSHXX_U.add(sSHBH_U);
        Logger.log("LOG_DEBUG","Com.listSHXX_U ADD "+sSHBH_U);


        for (String string:sZDXX_U.split("\\|"))
        {
            Com.listZDXX_U.add(string);
            Logger.log("LOG_DEBUG","Com.listZDXX_U ADD "+string);

        }

        Logger.log("LOG_IO", Com.getOut);



        return true;
    }
}
