package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.Model.DataModel;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static XQBHController.Controller.Com.LogLV;
import static XQBHController.Controller.Com.modelZipFile;

public class GetZDModel {
    public static DataModel exec(String sZDBH_U) throws Exception {
        String sIP = "";
        int iPort=0;
        for (Map map :
                Com.listSH_ZDXX) {
            if (sZDBH_U.equals(DataUtils.getValue(map,"ZDBH_U"))) {
                sIP = DataUtils.getValue(map,"IP_UUU");
                break;
            }
        }
        if (null == sIP || "".equals(sIP)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "终端未登录或找不到对应终端"+sZDBH_U+"信息");
            return null;
        }
        iPort=Integer.parseInt(sIP.split(":")[1]);
        sIP=sIP.split(":")[0];

//客户端
//1、创建客户端Socket，指定服务器地址和端口
        Logger.log("LOG_DEBUG", "IP=[" + sIP + "] Port=[" + iPort + "]");
        Socket socket = new Socket(sIP, iPort);
//2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
        Map xmlMapIn = new HashMap();
        xmlMapIn.put("FUNCTION", "getModel");
        String sXmlIn = XmlUtils.map2XML(xmlMapIn);
        Logger.log("LOG_DEBUG","xmlMapIn="+xmlMapIn);
        pw.write(sXmlIn);
        pw.flush();
        socket.shutdownOutput();
        Logger.log("LOG_DEBUG","send over");


//3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String info = null;
        Logger.log("LOG_DEBUG","begin to read");

        info = br.readLine();

        Logger.log("LOG_DEBUG","read over");
        //4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
//        System.out.println(info);
        Logger.log("LOG_DEBUG","info="+info);
        Map map = XmlUtils.XML2map(info);
//        System.out.println(map.get("MODEL"));
        Logger.log("LOG_DEBUG", "get Model=" + DataUtils.getValue(map,"MODEL"));
        Gson gson = new Gson();
        String gsonstr=DataUtils.getValue(map,"MODEL");
        DataModel dataModel = gson.fromJson(gsonstr, DataModel.class);
        Logger.log("LOG_DEBUG", "get Model info finish!!!");
        return dataModel;
    }

    /**
     * @param args
     * @apiNote just for test
     */
    public static void main(String[] args) {
       Map map=new HashMap();
        map.put("IP_UUU","192.168.1.115:9001");
//        map.put("IP_UUU","192057t6r9.iask.in:24582");
       map.put("ZDBH_U","zd");
        Com.listSH_ZDXX.add(map);
        try {
            exec("zd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
