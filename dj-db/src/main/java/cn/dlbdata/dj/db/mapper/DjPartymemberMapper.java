package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.vo.party.ReportPartyMemberVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjPartymemberMapper extends Mapper<DjPartymember> {
    List<ReportPartyMemberVo> getReportPartyMember(Long deptId);
}