package XQBHController.Controller.Table.Mapper;

import XQBHController.Controller.Table.Model.DSPXX;
import XQBHController.Controller.Table.Model.DSPXXExample;
import XQBHController.Controller.Table.Model.DSPXXKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DSPXXMapper {
    int countByExample(DSPXXExample example);

    int deleteByExample(DSPXXExample example);

    int deleteByPrimaryKey(DSPXXKey key);

    int insert(DSPXX record);

    int insertSelective(DSPXX record);

    List<DSPXX> selectByExample(DSPXXExample example);

    DSPXX selectByPrimaryKey(DSPXXKey key);

    int updateByExampleSelective(@Param("record") DSPXX record, @Param("example") DSPXXExample example);

    int updateByExample(@Param("record") DSPXX record, @Param("example") DSPXXExample example);

    int updateByPrimaryKeySelective(DSPXX record);

    int updateByPrimaryKey(DSPXX record);
}