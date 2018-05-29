package cn.dlbdata.dj.db.mapper;

import java.util.List;
import java.util.Map;

import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.vo.party.IdNameTotalScoreVo;
import cn.dlbdata.dj.db.vo.party.ObserveLowPartyMemberVo;
import cn.dlbdata.dj.db.vo.party.PioneeringPartyMemberVo;
import cn.dlbdata.dj.db.vo.party.ReportPartyMemberVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface DjPartymemberMapper extends Mapper<DjPartymember> {

    List<ReportPartyMemberVo> getReportPartyMembers(Long deptId);

    List<PioneeringPartyMemberVo> getPioneeringPartyMembers(Long deptId);
    /**
     * 
     * <p>Title: queryAllPartyMembersByDeptId</p> 
     * <p>Description: 查询支部全部党员的信息以及分数</p> 
     * @param deptId
     * @return
     */
    public List<DjPartymember> queryAllPartyMembersByDeptId(Map<String,Object> map);

    List<ObserveLowPartyMemberVo> getObserveLowPartyMember(@Param("deptId") Long deptId,@Param("year") int year);

    /**
     * 通过党员的Id查询，name和总分数
     * @param id
     * @return
     */
    IdNameTotalScoreVo getTotalScoreById(long id);
}