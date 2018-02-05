package XQBHController.Utils.PropertiesHandler;

import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.log.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class PropertiesHandler {
    public static String readKeyFromXML(File file, String Key) {
        Properties propertie;
        FileInputStream inputFile;
        if (!file.exists()) {
            Logger.log("LOG_ERR", "file" + file.getAbsolutePath() + " not found");
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取" + file.getAbsolutePath() + "错误!");

            return null;
        }

        propertie = new Properties();
        try {
            inputFile = new FileInputStream(file);
            propertie.load(inputFile);
//            propertie.loadFromXML(inputFile);//读取XML文件
            inputFile.close();
        } catch (FileNotFoundException ex) {
            Logger.log("LOG_ERR", ex.toString());
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "文件" + file.getAbsolutePath() + "无法找到!");

        } catch (IOException ex) {
            Logger.log("LOG_ERR", ex.toString());
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取文件" + file.getAbsolutePath() + "失败!");
        }
        return propertie.getProperty(Key);
    }
//
//    public static void main(String[] args) {
//        Logger.log("LOG_DEBUG",readKeyFromXML(new File("resources/userInfo.properties") ,"ZDJYM_"));
//    }


    public static Boolean writeProperties(File file, String Key, String Value) {
        Properties propertie;
        FileInputStream inputFile;
        if (!file.exists()) {
            Logger.log("LOG_ERR", "file" + file.getAbsolutePath() + " not found");
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取" + file.getAbsolutePath() + "错误!");

            return null;
        }

        propertie = new Properties();
        try {
            inputFile = new FileInputStream(file);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            Logger.log("LOG_ERR", ex.toString());
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "文件" + file.getAbsolutePath() + "无法找到!");

        } catch (IOException ex) {
            Logger.log("LOG_ERR", ex.toString());
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取文件" + file.getAbsolutePath() + "失败!");
        }

        try {
            FileWriter fileWriter=new FileWriter(file);
            propertie.put(Key,Value);
            propertie.store(fileWriter, Key + "=" + Value);

        } catch (IOException e) {
            Logger.log("LOG_ERR", e.toString());
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "读取文件" + file.getAbsolutePath() + "失败!");
        }

        return true;
    }
}