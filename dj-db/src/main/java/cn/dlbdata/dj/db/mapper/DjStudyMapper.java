package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.vo.study.PendingPtMemberVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.beans.Transient;
import java.util.List;

public interface DjStudyMapper extends Mapper<DjStudy> {
//    @Transient
//    List<PendingPtMemberVo> getStudysByDeptIdAndSubTypeId(@Param("deptId") Long deptId,
//                                                          @Param("subTypeId") Long subTypeId);
}