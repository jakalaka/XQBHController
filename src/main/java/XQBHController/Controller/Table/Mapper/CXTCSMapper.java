package XQBHController.Controller.Table.Mapper;

import XQBHController.Controller.Table.Model.CXTCS;
import XQBHController.Controller.Table.Model.CXTCSExample;
import XQBHController.Controller.Table.Model.CXTCSKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CXTCSMapper {
    int countByExample(CXTCSExample example);

    int deleteByExample(CXTCSExample example);

    int deleteByPrimaryKey(CXTCSKey key);

    int insert(CXTCS record);

    int insertSelective(CXTCS record);

    List<CXTCS> selectByExample(CXTCSExample example);

    CXTCS selectByPrimaryKey(CXTCSKey key);

    int updateByExampleSelective(@Param("record") CXTCS record, @Param("example") CXTCSExample example);

    int updateByExample(@Param("record") CXTCS record, @Param("example") CXTCSExample example);

    int updateByPrimaryKeySelective(CXTCS record);

    int updateByPrimaryKey(CXTCS record);
}