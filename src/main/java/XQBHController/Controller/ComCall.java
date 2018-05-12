package XQBHController.Controller;

import XQBHController.ControllerAPI.UI.WarmingDialog;


import XQBHController.Utils.RSA.HashUtil;
import XQBHController.Utils.RSA.RSAUtil;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ComCall {
    /**
     * client���ײ�ͨ�õ���
     *
     * @param TranMapIn
     * @param TranMapOut
     * @return
     */
    public static boolean Call(String QTJYM_, String HTJYM_, Map TranMapIn, Map TranMapOut) {
        Map XMLMapIn = new HashMap();//д����,��TranMapIn��ӱ���ͷ��Ϣ,��ΪXMLMapIn
        if (true != addInfo(QTJYM_, HTJYM_, TranMapIn, XMLMapIn)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��֯����ͷ��Ϣʧ��!");
            return false;
        }

        String XMLIn = XmlUtils.map2XML(XMLMapIn);
        Logger.log("LOG_DEBUG", "XMLIn=" + XMLIn);

        /*
        ����
         */
        byte[] buffout = XMLIn.getBytes();
        try {
            buffout = HashUtil
                    .encryptBASE64byte(RSAUtil.encryptByPrivateKey(buffout, Com.clientEncryptPrivateKey));
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�������ͱ���ʧ��!");
            return false;
        }
        /*
        ��ǩ
         */

        String signstr = null;
        try {
            signstr = RSAUtil.sign(XMLIn.getBytes(Com.charset), Com.clientSignPrivateKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ǩ���ͱ���ʧ��!");
            return false;
        }
        byte[] splitbyte = {'7', '7', '7', '7', '7', '7', '7'};
        buffout = addBytes(buffout, splitbyte, splitbyte.length);
        byte[] signbyteout = signstr.getBytes();
        buffout = addBytes(buffout, signbyteout, signbyteout.length);

        XMLIn = new String(buffout);
        Logger.log("LOG_DEBUG", "after encrypt XMLIn=[" + XMLIn + "]");
        Logger.log("LOG_DEBUG", "after encrypt XMLIn.length=[" + XMLIn.length() + "]");


//
//        CommonTran commonTran;
//        try {
//            commonTran = new CommonTranService().getCommonTranPort();
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����������ʧ��!");
//            return false;
//        }
//        String XMLOut;
//        try {
//            XMLOut = commonTran.comtran(XMLIn);
//
//        } catch (Exception e) {
//            Logger.logException("LOG_ERR", e);
//            WarmingDialog.show(WarmingDialog.Dialog_ERR, "����������!");
//            return false;
//        }
//        String IP = "192.168.31.62";
        String IP="newfangledstore.com";

        int port = 9000;
        String XMLOut = "";
        byte[] buff = new byte[1024];
        byte[] buffIn = new byte[0];
        int t = 0;
        try {


            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(IP, port), 3000);//������������ʱʱ��10 s
            socket.setSoTimeout(15000);//���ö�������ʱʱ��30 s
//2����ȡ���������������˷�����Ϣ
            OutputStream os = socket.getOutputStream();//�ֽ������
//            PrintWriter pw = new PrintWriter(os);//���������װ�ɴ�ӡ��

//            pw.write(XMLIn);
//            pw.flush();
            System.out.println(buffout.length);
            os.write(buffout);

            socket.shutdownOutput();
//3����ȡ������������ȡ�������˵���Ӧ��Ϣ
            InputStream is = socket.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
//            StringBuilder stringBuilder = new StringBuilder();

//            while ((info = br.readLine()) != null) {
//                stringBuilder.append(info);
//            }

            while ((t = is.read(buff)) != -1) {
                buffIn = addBytes(buffIn, buff, t);
            }

//            XMLOut = stringBuilder.toString();
            //4���ر���Դ
//            br.close();
            is.close();
//            pw.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "ͨѶ�쳣!");
            return false;
        }


        Logger.log("LOG_DEBUG", "XMLOut=" + new String(buffIn));

        //��ȡǩ���ָ�λ��
        int iPos = 0;
        boolean foundSplit = false;
        for (int i = 0; i < buffIn.length - 7; i++) {

            for (int j = 0; j < 7; j++) {
                if (buffIn[i + j] == '7') {
                    foundSplit = true;
                    continue;
                } else {
                    foundSplit = false;
                    break;
                }
            }
            if (foundSplit) {
                iPos = i;
                break;
            }
        }
        if (!foundSplit)//δ�ҵ��ָ��
        {

        }
        byte[] encrybyte = new byte[iPos];
        System.arraycopy(buffIn, 0, encrybyte, 0, iPos);
        /*
        ��ǩ
         */
        byte[] signbyte = new byte[buffIn.length - iPos - 7];
        System.arraycopy(buffIn, iPos + 7, signbyte, 0, signbyte.length);
        boolean verPass = false;
        try {
            verPass = RSAUtil.verify(encrybyte, Com.serverSignPublicbKey, new String(signbyte));
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�������ͨѶ��ǩʧ��!");
            return false;
        }
        if (!verPass) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ǩ����!");
            return false;
        }


        /*
        ����
         */

        byte[] datebyte = new byte[0];
        try {
            datebyte = RSAUtil.decryptByPrivateKey(HashUtil.decryptBASE64(encrybyte), Com.clientEncryptPrivateKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�������ͨѶ����ʧ��!");
            return false;
        }
        XMLOut = new String(datebyte);

        Logger.log("LOG_DEBUG", "after decrypt XMLOut="+XMLOut);
        Map XMLMapOut = XmlUtils.XML2map(XMLOut);

        if (false == minusInfo(XMLMapOut, TranMapOut)) {
            return false;
        }
        return true;
    }

    /**
     * ���Ϸ��Բ���ӱ���ͷ
     *
     * @param TranMapIn
     * @param XMLMapIn
     * @return
     */
    public static boolean addInfo(String QTJYM_, String HTJYM_, Map TranMapIn, Map XMLMapIn) {
        Map head = new HashMap();
        String[] date = Com.getDate().split("-");
        head.put("QTRQ_U", date[0]); //ǰ̨����
        head.put("QTSJ_U", date[1]); //ǰ̨ʱ��
        String sQTLS = Com.getQTLS();
        if (sQTLS.length() != 20) {
            Logger.log("LOG_ERR", "��ˮ��ȡʧ��");
            return false;
        }
        head.put("QTLS_U", sQTLS); //ǰ̨��ˮ

        head.put("QTJYM_", QTJYM_); //������
        head.put("HTJYM_", HTJYM_); //��̨������
        head.put("IP_UUU", Com.ControllerIP); //ip��ַ




        head.put("KHDLZH", Com.sKHDLZH);
        head.put("KHBH_U", Com.sKHBH_U);
        head.put("SHBH_U", Com.sSHBH_U);


        XMLMapIn.put("head", head);
        XMLMapIn.put("body", TranMapIn);
        return true;
    }

    /**
     * ���Ϸ��Բ�ȥ������ͷ
     *
     * @param XMLMapOut
     * @param TranMapOut
     * @return
     */
    public static boolean minusInfo(Map XMLMapOut, Map TranMapOut) {
        TranMapOut.clear();


        /*
        ClientͨѶ��ĺϷ��Լ��,Ҳ��֪��дʲô��,�Ժ�Ӱ�
         */

//        String CWXX_U = (String) ((Map) XMLMapOut.get("head")).get("CWXX_U");
        //����ӦҪ��
//        TranMapOut.put("CWDM_U",CWDM_U);
//        TranMapOut.put("CWXX_U",CWXX_U);
        TranMapOut.putAll((Map) XMLMapOut.get("head"));
        TranMapOut.putAll((Map) XMLMapOut.get("body"));

        String CWDM_U = (String) TranMapOut.get("CWDM_U");


        if (!"AAAAAA".equals(CWDM_U)) {
            /*
            ������ʧ�ܴ���
             */
            Logger.log("LOG_ERR", "����ʧ��");
            Logger.log("LOG_ERR", "CWDM_U=" + CWDM_U);
            return false;
        } else Logger.log("LOG_IO", "CWDM_U=" + CWDM_U);

        return true;
    }

    public static byte[] addBytes(byte[] data1, byte[] data2, int size) {
        byte[] data3 = new byte[data1.length + size];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, size);
        return data3;

    }

}
