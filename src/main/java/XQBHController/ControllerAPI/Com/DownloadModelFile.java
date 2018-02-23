package XQBHController.ControllerAPI.Com;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static XQBHController.Controller.Com.modelZipFile;

public class DownloadModelFile {
    public static boolean exec(String sZDBH_U) throws Exception {
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
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "终端未登录找不到对应终端信息");
            return false;
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
        xmlMapIn.put("FUNCTION", "getModelFile");
        String sXmlIn = XmlUtils.map2XML(xmlMapIn);
        pw.write(sXmlIn);
        pw.flush();
        socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        byte[] buf = new byte[1024];
        File file = new File(modelZipFile.replaceAll("ZDBH_U", sZDBH_U));
        File path = new File(file.getParent());
        if (!path.exists())
            path.mkdirs();



        FileOutputStream fos = new FileOutputStream(file);
        int length = 0;
        int count = 0;
        while ((length = is.read(buf, 0, buf.length)) > 0) {
            fos.write(buf, 0, length);
        }
        fos.close();
        //4、关闭资源
        is.close();
        pw.close();
        os.close();
        socket.close();

        Logger.log("LOG_DEBUG", "transfile finish!!!");
        Logger.log("LOG_DEBUG", "begin to unZip!!!");
        decompression(Com.modelZipFile.replaceAll("ZDBH_U", sZDBH_U), "GBK", Com.modelFile.replaceAll("ZDBH_U", sZDBH_U));
        Logger.log("LOG_DEBUG", "unZip finish!!!");

        return true;
    }

    private static void decompression(String zipPath, String charset, String outPutPath) throws Exception {
        long startTime = System.currentTimeMillis();

        ZipInputStream Zin = new ZipInputStream(new FileInputStream(zipPath), Charset.forName(charset));//输入源zip路径
        BufferedInputStream Bin = new BufferedInputStream(Zin);
        String Parent = outPutPath; //输出路径（文件夹目录）
        Parent=new File(outPutPath).getParent();
        File Fout = null;
        ZipEntry entry;
        while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
            Fout = new File(Parent, entry.getName());
            if (!Fout.exists()) {
                (new File(Fout.getParent())).mkdirs();
            }
            FileOutputStream out = new FileOutputStream(Fout);
            BufferedOutputStream Bout = new BufferedOutputStream(out);
            int b;
            while ((b = Bin.read()) != -1) {
                Bout.write(b);
            }
            Bout.close();
            out.close();
            Logger.log("LOG_DEBUG", Fout + "解压成功");
        }
        Bin.close();
        Zin.close();

        long endTime = System.currentTimeMillis();
        Logger.log("LOG_DEBUG", "耗费时间： " + (endTime - startTime) + " ms");
    }

    public static void main(String[] args) {
//        try {
//            DownloadModelFile.exec("ZD0001", "192057t6r9.iask.in");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
