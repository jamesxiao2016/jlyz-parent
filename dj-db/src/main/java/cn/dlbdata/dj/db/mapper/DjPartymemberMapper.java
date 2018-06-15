package cn.dlbdata.dj.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.vo.DjPartyMemberVo;
import cn.dlbdata.dj.db.vo.apply.ScoreTypeVo;
import cn.dlbdata.dj.db.vo.party.AllPartyMemberVo;
import cn.dlbdata.dj.db.vo.party.IdNameTotalScoreVo;
import cn.dlbdata.dj.db.vo.party.ObserveLowPartyMemberVo;
import cn.dlbdata.dj.db.vo.party.PioneeringPartyMemberVo;
import cn.dlbdata.dj.db.vo.party.ReportPartyMemberVo;
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
    public List<AllPartyMemberVo> queryAllPartyMembersByDeptId(Map<String,Object> map);

    /**
     * 违章守纪评分党员列表
     *
     * @param deptId
     *            支部Id
     * @return
     */
    List<ObserveLowPartyMemberVo> getObserveLowPartyMember(@Param("deptId") Long deptId,@Param("year") int year);

    /**
     * 通过党员的Id查询，name和总分数
     * @param id
     * @return
     */
    IdNameTotalScoreVo getTotalScoreById(@Param("id") long id,@Param("year") int year);
    /**
     * 
     * <p>Title: getSumScoreByIdCard</p> 
     * <p>Description: 根据身份证号查询总积分</p> 
     * @param idCard
     * @return
     */
    public Float getSumScoreByIdCard(Map<String,Object> map);
    /**
     * 
     * <p>Title: selectPartymemberByDeptId</p> 
     * <p>Description: 提供给外部使用的部门党员信息</p> 
     * @param deptId
     * @return
     */
    public List<DjPartyMemberVo> selectPartymemberByDeptId(@Param("deptId")Integer deptId);
	public List<ScoreTypeVo> getRadarChartByUserId(Long userId, Integer year);
}