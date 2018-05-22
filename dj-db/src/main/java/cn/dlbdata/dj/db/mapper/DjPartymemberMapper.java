package cn.dlbdata.dj.db.mapper;

import java.util.List;

import cn.dlbdata.dj.db.pojo.DjPartymember;
import tk.mybatis.mapper.common.Mapper;

public interface DjPartymemberMapper extends Mapper<DjPartymember> {
    List<DjPartymember> getReportPartyMember(Long deptId);
}