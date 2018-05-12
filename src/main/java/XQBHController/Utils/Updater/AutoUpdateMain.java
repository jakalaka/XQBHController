package XQBHController.Utils.Updater;

import XQBHController.Utils.log.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AutoUpdateMain {


    public static void main(String[] args) {
        AutoUpdateMain autoUpdateMain = new AutoUpdateMain();
        if (true != autoUpdateMain.exec(args))
            return;
    }

    private static String getVersionByFile(String path) {
        String version = null;
        byte[] b = new byte[1024];
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return null;
        }
        try {
            FileInputStream in = new FileInputStream(file);
            DataInputStream dIn = new DataInputStream(in);

            while (dIn.read(b) > 0) {
                String res = new String(b, "GBK");
                version = res;
            }
            dIn.close();
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return version;
    }

    public boolean exec(String[] args) {
        Logger.log("LOG_SYS", "�Զ�������������...");
        // �ر��������е�JavaӦ�ó���
        Logger.log("LOG_SYS", "����FTP�ϰ汾�����ļ�:");
        Logger.log("LOG_DEBUG", "Const.hostName=" + Const.hostName);
        Logger.log("LOG_DEBUG", "Const.port=" + Const.port);
        Logger.log("LOG_DEBUG", "Const.user=" + Const.user);
        Logger.log("LOG_DEBUG", "Const.pwd=" + Const.pwd);
        Logger.log("LOG_DEBUG", "Const.RemotePath=" + Const.RemotePath);
        Logger.log("LOG_DEBUG", "Const.LocalPath=" + Const.LocalPath);

        String oldVersion = getVersionByFile(Const.LocalOld + "/version.txt");
        AutoUpdateMainPro version_down = new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, "version.txt", Const.LocalPath, "version.txt");
        if (true != version_down.execute()) {
            Logger.log("LOG_DEBUG", "��ȡ�汾�ļ�ʧ��");
            return false;
        }
        String newVersion = getVersionByFile(Const.LocalPath + "/version.txt");
        try {
            Logger.log("LOG_SYS", "���ڽ��а汾�ļ��Ƚ�...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.log("LOG_SYS", "oldVersion=" + oldVersion.trim());
        Logger.log("LOG_SYS", "newVersion=" + newVersion.trim());
        Logger.log("LOG_SYS", "Const.version=" + Const.version);
        if (null != newVersion && newVersion.trim().equals(Const.version)) {
            Logger.log("LOG_SYS", "���ݲɼ�����汾û�и��±䶯");
            return true;
        }



        //ͨ��ָ��COMPLETE+����Ϊָ������,������ȫ������
        Logger.log("LOG_SYS", "��⵽���ݲɼ�����汾�и���,�����Զ���������");
        try {
            Logger.log("LOG_SYS", "���ڽ��а汾�ļ��Ƚ�...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Logger.log("LOG_DEBUG", "��ʼ����exe");
                /*����exe*/
        String exeName = "XQBHController.exe";
        AutoUpdateMainPro exe_down = new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, exeName, Const.LocalPath, exeName);
        try {
            exe_down.execute();
        } catch (Exception e) {
            Logger.logException("LOG_ERR",e);
            Logger.log("LOG_DEBUG", "�����ļ�������ʧ��");
            return false;
        }
        Logger.log("LOG_DEBUG", "exe�������");

        Logger.log("LOG_DEBUG", "��ʼ����bat");
                /*����bat*/
        String batName = "run.bat";
        AutoUpdateMainPro bat_down = new AutoUpdateMainPro(Const.hostName, Const.port, Const.user, Const.pwd, Const.RemotePath, batName, Const.LocalPath, batName);
        try {
            bat_down.execute();
        } catch (Exception e) {
            Logger.logException("LOG_ERR",e);
            Logger.log("LOG_DEBUG", "�����ļ�������ʧ��");
            return false;
        }
        Logger.log("LOG_DEBUG", "bat�������");

        try {
            Files.copy(new File(Const.LocalPath + "/version.txt" ).toPath(), new File(Const.LocalOld + "/version.txt" ).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.log("LOG_SYS", "update over");


        Logger.log("LOG_SYS", "begin restart...");
        try {
            Logger.log("LOG_SYS", "�����������!�������ݲɼ������˳�...");
            Thread.sleep(3000);
            // �����رյ�JavaӦ�ó���
            Logger.log("LOG_SYS", "�����رյ�JavaӦ�ó���");
            runbat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
        return true;
    }


    public static void runbat() {
        File file = new File(Const.LocalPath + "/run.bat");
        Logger.log("LOG_SYS", "׼��ִ�и����ļ�:" + file.getAbsolutePath());
        String cmd = "cmd /c start " + file.getAbsolutePath();// pass
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            ps.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Logger.log("LOG_SYS", "׼����������...");
    }
}
