package XQBHController.ControllerAPI;

import XQBHController.Controller.Com;
import XQBHController.Controller.Table.Mapper.DSPXXMapper;
import XQBHController.Controller.Table.Model.DSPXX;
import XQBHController.Controller.Table.Model.DSPXXKey;
import XQBHController.Controller.Table.basic.DBAccess;
import XQBHController.Utils.log.Logger;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class UpdateDSPXX {
    public static boolean  exec(String thingsName,int iNum) {
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
        } catch (IOException e) {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SESSION);
            return false;
        }

        DSPXXMapper dspxxMapper=sqlSession.getMapper(DSPXXMapper.class);
        DSPXXKey dspxxKey=new DSPXXKey();
        dspxxKey.setFRDM_U("9999");
        dspxxKey.setSPMC_U(thingsName);
        DSPXX dspxx;
        try {
            dspxx = dspxxMapper.selectByPrimaryKey(dspxxKey);
        }catch (Exception e)
        {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_SELECT);
            return false;
        }
        if (null==dspxx)
        {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "更新商品库存时查询数据库无记录!");
            return false;
        }
        dspxx.setSL_UUU(dspxx.getSL_UUU()+iNum);
        try {
            dspxxMapper.updateByPrimaryKey(dspxx);

        }catch (Exception e)
        {
            Logger.logException("LOG_ERR", e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, Com.SQLERR_UPDATE);
            return false;
        }

        sqlSession.commit();
        sqlSession.close();


        return true;
    }
}
