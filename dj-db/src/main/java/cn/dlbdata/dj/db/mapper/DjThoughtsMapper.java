package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjThoughts;
import cn.dlbdata.dj.db.vo.party.ReportDetailVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface DjThoughtsMapper extends Mapper<DjThoughts> {
    List<Long> getByDeptIdAndTypeIdAndSubTypeIdAndYear(@Param("deptId")long deptId,
                                                       @Param("typeId") Long typeId,
                                                       @Param("yearTimeStart")Date yearTimeStart,
                                                       @Param("yearTimeEnd") Date yearTimeEnd);

    List<ReportDetailVo> getReportDetail(@Param("id") Long id,
                                   @Param("subTypeId") Long subTypeId,
                                   @Param("yearTimeStart")Date yearTimeStart,
                                   @Param("yearTimeEnd") Date yearTimeEnd);

    /**
     * 查询党员本年内是否有重复的思想汇报记录
     * @param partyMemberId 党员Id
     * @param type 思想汇报类型
     * @return
     */
    int checkExists(@Param("partyMemberId") Long partyMemberId,
                       @Param("type") Long type,
                       @Param("yearTimeStart")Date yearTimeStart,
                       @Param("yearTimeEnd") Date yearTimeEnd);
}