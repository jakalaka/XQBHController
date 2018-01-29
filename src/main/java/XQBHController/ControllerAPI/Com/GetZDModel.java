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
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ն�δ��¼���Ҳ�����Ӧ�ն�"+sZDBH_U+"��Ϣ");
            return null;
        }
//�ͻ���
//1�������ͻ���Socket��ָ����������ַ�Ͷ˿�
        Logger.log("LOG_DEBUG", "IP=[" + sIP + "] Port=[" + 9001 + "]");
        Socket socket = new Socket(sIP, 9001);
//2����ȡ���������������˷�����Ϣ
        OutputStream os = socket.getOutputStream();//�ֽ������
        PrintWriter pw = new PrintWriter(os);//���������װ�ɴ�ӡ��
        Map xmlMapIn = new HashMap();
        xmlMapIn.put("FUNCTION", "getModel");
        String sXmlIn = XmlUtils.map2XML(xmlMapIn);
        pw.write(sXmlIn);
        pw.flush();
        socket.shutdownOutput();
//3����ȡ������������ȡ�������˵���Ӧ��Ϣ
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String info = null;
        info = br.readLine();

        //4���ر���Դ
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
