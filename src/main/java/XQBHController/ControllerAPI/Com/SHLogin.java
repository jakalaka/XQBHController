package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SHLogin {
    public static boolean exec(String sSHBH_U,String sSHMM_U,String []sMessage) {
        Logger.log("LOG_IO", Com.getIn);

        Com.sKHDLZH = "";
        Com.sKHBH_U = "";
        Com.sSHBH_U = sSHBH_U;

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
        List<HashMap> listZDLIST=(List) Out.get("ZDLIST");
        List<HashMap> listSHLIST=(List) Out.get("SHLIST");

        Com.listKH_SHXX.add(listSHLIST.get(0));


        for (Map map:listZDLIST)
        {
            System.out.println("ZDBH_U="+ DataUtils.getValue(map,"ZDBH_U")+" IP="+DataUtils.getValue(map,"IP_UUU"));
            Com.listSH_ZDXX.add(map);
        }




        Logger.log("LOG_IO", Com.getOut);



        return true;
    }
}
