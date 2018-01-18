package XQBHController.Utils.PropertiesHandler;

import XQBHController.ControllerAPI.WarmingDialog;
import XQBHController.Utils.log.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class PropertiesReader {
    public static String readKeyFromXML(File file,String Key){
        Properties propertie;
        FileInputStream inputFile;
        if (!file.exists())
        {
            Logger.log("LOG_ERR","file"+file.getAbsolutePath()+" not found");
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡ"+file.getAbsolutePath()+"����!");

            return  null;
        }

        propertie = new Properties();
        try{
            inputFile = new FileInputStream(file);
            propertie.load(inputFile);
//            propertie.loadFromXML(inputFile);//��ȡXML�ļ�
            inputFile.close();
        } catch (FileNotFoundException ex){
            Logger.log("LOG_ERR",ex.toString());
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�ļ�"+file.getAbsolutePath()+"�޷��ҵ�!");

        } catch (IOException ex) {
            Logger.log("LOG_ERR",ex.toString());
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��ȡ�ļ�"+file.getAbsolutePath()+"ʧ��!");
        }
        return propertie.getProperty(Key);
    }
//
//    public static void main(String[] args) {
//        Logger.log("LOG_DEBUG",readKeyFromXML(new File("resources/userInfo.properties") ,"ZDJYM_"));
//    }
}