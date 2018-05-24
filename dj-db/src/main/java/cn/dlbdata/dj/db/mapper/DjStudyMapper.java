package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjStudy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjStudyMapper extends Mapper<DjStudy> {
    List<DjStudy> getStudysByDeptIdAndSubTypeId(@Param("deptId") Long deptId,
                                                       @Param("subTypeId") Long subTypeId);
}