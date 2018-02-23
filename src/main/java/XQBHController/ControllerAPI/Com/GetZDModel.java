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
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ն�δ��¼���Ҳ�����Ӧ�ն�"+sZDBH_U+"��Ϣ");
            return null;
        }
        iPort=Integer.parseInt(sIP.split(":")[1]);
        sIP=sIP.split(":")[0];

//�ͻ���
//1�������ͻ���Socket��ָ����������ַ�Ͷ˿�
        Logger.log("LOG_DEBUG", "IP=[" + sIP + "] Port=[" + iPort + "]");
        Socket socket = new Socket(sIP, iPort);
//2����ȡ���������������˷�����Ϣ
        OutputStream os = socket.getOutputStream();//�ֽ������
        PrintWriter pw = new PrintWriter(os);//���������װ�ɴ�ӡ��
        Map xmlMapIn = new HashMap();
        xmlMapIn.put("FUNCTION", "getModel");
        String sXmlIn = XmlUtils.map2XML(xmlMapIn);
        Logger.log("LOG_DEBUG","xmlMapIn="+xmlMapIn);
        pw.write(sXmlIn);
        pw.flush();
        socket.shutdownOutput();
        Logger.log("LOG_DEBUG","send over");


//3����ȡ������������ȡ�������˵���Ӧ��Ϣ
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String info = null;
        Logger.log("LOG_DEBUG","begin to read");

        info = br.readLine();

        Logger.log("LOG_DEBUG","read over");
        //4���ر���Դ
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
