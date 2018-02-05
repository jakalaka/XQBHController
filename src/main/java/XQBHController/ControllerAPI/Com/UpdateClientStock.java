package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class UpdateClientStock {
    public static boolean exec(String ZDBH_U,String sKey, String goodsAccount,String checkPass) throws IOException {
        String sIP = "";
        for (Map map:
                Com.listSH_ZDXX) {
            String sZDBH_U=map.get("ZDBH_U").toString();
            if (sZDBH_U.equals(ZDBH_U)) {
                sIP = map.get("IP_UUU").toString();
                break;
            }
        }

        Logger.log("LOG_DEBUG", "IP=[" + sIP + "] Port=[" + 9001 + "]");
        Socket socket = new Socket(sIP, 9001);



//2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
        Map xmlMapIn = new HashMap();
        xmlMapIn.put("FUNCTION", "updClientStock");
        xmlMapIn.put("position", sKey);
        xmlMapIn.put("goodsAccount", goodsAccount);
        xmlMapIn.put("checkPass",checkPass);

        String sXmlIn = XmlUtils.map2XML(xmlMapIn);
        pw.write(sXmlIn);
        pw.flush();
        socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String info = null;
        info = br.readLine();
        socket.shutdownInput();
        //4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
        Map mapOut = XmlUtils.XML2map(info);
        Logger.log("LOG_DEBUG",mapOut.get("CWDM_U").toString());
        if (!"AAAAAA".equals(mapOut.get("CWDM_U")))
            return false;

        return true;
    }
}
