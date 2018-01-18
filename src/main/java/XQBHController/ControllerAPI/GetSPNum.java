package XQBHController.ControllerAPI;

import XQBHController.Controller.Com;
import XQBHController.Controller.Table.Mapper.DSPXXMapper;
import XQBHController.Controller.Table.Model.DSPXX;
import XQBHController.Controller.Table.Model.DSPXXKey;
import XQBHController.Controller.Table.basic.DBAccess;
import XQBHController.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class GetSPNum {
    public static int exec(String thingsName ) {
        int iNum = 0;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SESSION);
            return 0;
        }
        DSPXXMapper dspxxMapper = sqlSession.getMapper(DSPXXMapper.class);
        DSPXXKey dspxxKey = new DSPXXKey();
        dspxxKey.setFRDM_U("9999");
        dspxxKey.setSPMC_U(thingsName);
        DSPXX dspxx = null;
        try {
            dspxx = dspxxMapper.selectByPrimaryKey(dspxxKey);
        } catch (Exception e) {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SELECT);
            return 0;
        }
        return dspxx.getSL_UUU();
    }
}
