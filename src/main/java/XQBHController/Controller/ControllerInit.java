package XQBHController.Controller;


import XQBHController.Controller.Table.Mapper.CXTCSMapper;
import XQBHController.Controller.Table.Model.CXTCS;
import XQBHController.Controller.Table.Model.CXTCSKey;
import XQBHController.Controller.Table.basic.DBAccess;
import XQBHController.ControllerAPI.WarmingDialog;
import XQBHController.ControllerUI.ControllerUIMain;
import XQBHController.Utils.FinishComListener.FinishComListener;
import XQBHController.Utils.QRReader.QRReader;
import XQBHController.Utils.Updater.AutoUpdateMain;
import XQBHController.Utils.XML.XmlUtils;
import XQBHController.Utils.log.Logger;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static XQBHController.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class ControllerInit {
    public static boolean Init() {


        Logger.log("LOG_IO", Com.getIn);



        /*
        从userInfo中获取相关配置信息
         */
        String ZDJYM_ = readKeyFromXML(new File("resources/Info/userInfo.properties"), "ZDJYM_");
        if (!"".equals(ZDJYM_) && ZDJYM_ != null)
            Com.ZDJYM_ = ZDJYM_;

        String ZDBH_U = readKeyFromXML(new File("resources/Info/userInfo.properties"), "ZDBH_U");
        if (!"".equals(ZDBH_U) && ZDBH_U != null)
            Com.ZDBH_U = ZDBH_U;

        String LogLV = readKeyFromXML(new File("resources/Info/userInfo.properties"), "LogLV");
        if (!"".equals(LogLV) && LogLV != null)
            Com.LogLV = LogLV;

        /*
        从sysInfo中获取相关配置
         */
        String sysinfo = "resources/Info/sysInfo.properties";
        File sysprop = new File(sysinfo);
        QRReader.timeOut = Integer.parseInt(readKeyFromXML(sysprop, "timeOut"));
        if (0 == QRReader.timeOut) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取timeOut错误!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "timeOut=" + QRReader.timeOut);
        }
        QRReader.frequency = Integer.parseInt(readKeyFromXML(sysprop, "frequency"));
        if (0 == QRReader.frequency) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取frequency错误!");

            return false;
        } else {
            Logger.log("LOG_DEBUG", "frequency=" + QRReader.frequency);
        }


        Com.QRReaderComName = readKeyFromXML(sysprop, "QRReaderComName");
        if (null == Com.QRReaderComName || "".equals(Com.QRReaderComName)) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取QRReaderComName错误!");
            return false;
        } else {
            Logger.log("LOG_DEBUG", "QRReaderComName=" + Com.QRReaderComName);
        }



        Logger.log("LOG_IO", Com.getOut);

        return true;
    }

}
