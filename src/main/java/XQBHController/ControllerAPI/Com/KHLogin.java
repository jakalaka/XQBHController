package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.Utils.log.Logger;

import java.util.HashMap;
import java.util.List;
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
        List<HashMap> listZDLIST=(List) Out.get("ZDLIST");
        List<HashMap> listSHLIST=(List) Out.get("SHLIST");


        for (Map map:listSHLIST)
        {
            Com.listKH_SHXX.add(map);
        }

        for (Map map:listZDLIST)
        {
            Com.listSH_ZDXX.add(map);
        }

        Logger.log("LOG_IO", Com.getOut);


        return true;
    }
}
