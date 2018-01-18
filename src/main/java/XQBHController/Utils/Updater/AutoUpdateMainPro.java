package XQBHController.Utils.Updater;

import XQBHController.Utils.log.Logger;

public class AutoUpdateMainPro {

	private FTPControllerUtil ftpController = null;

	public AutoUpdateMainPro(String hostName, int port, String user, String pwd,String remotePath, String remoteFile, String localPath, String localFile)  {
		
		ftpController = new FTPControllerUtil( hostName,  port,  user,  pwd,  remotePath,  remoteFile,  localPath,  localFile);

	}
	public boolean execute()  {

		try {
			ftpController.FTPClientRun();
		} catch (Exception e) {
			Logger.logException("LOG_ERR",e);
			return false;
		}
//		finally {
			freeResource();
//		}
		// �ͷų���ռ�õ���Դ
		return true;
	}
	public void freeResource() {
		ftpController.freeFTPClient();
	}


}
