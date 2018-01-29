package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.Model.DataModel;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static XQBHController.Controller.Com.modelZipFile;

public class GetZDModel {
    public static DataModel exec(String sZDBH_U) throws Exception {
        String sIP = "";
        for (Map map :
                Com.listSH_ZDXX) {
            if (sZDBH_U.equals(map.get("ZDBH_U"))) {
                sIP = map.get("IP_UUU").toString();
                break;
            }
        }
        if (null == sIP || "".equals(sIP)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "终端未登录或找不到对应终端"+sZDBH_U+"信息");
            return null;
        }
//客户端
//1、创建客户端Socket，指定服务器地址和端口
        Logger.log("LOG_DEBUG", "IP=[" + sIP + "] Port=[" + 9001 + "]");
        Socket socket = new Socket(sIP, 9001);
//2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
        Map xmlMapIn = new HashMap();
        xmlMapIn.put("FUNCTION", "getModel");
        String sXmlIn = XmlUtils.map2XML(xmlMapIn);
        pw.write(sXmlIn);
        pw.flush();
        socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String info = null;
        info = br.readLine();

        //4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
//        System.out.println(info);

        Map map = XmlUtils.XML2map(info);
//        System.out.println(map.get("MODEL"));
        Logger.log("LOG_DEBUG", "get Model=" + map.get("MODEL"));
        Gson gson = new Gson();
        String gsonstr=map.get("MODEL").toString();
        DataModel dataModel = gson.fromJson(gsonstr, DataModel.class);
        Logger.log("LOG_DEBUG", "unZip finish!!!");
        return dataModel;
    }
}
