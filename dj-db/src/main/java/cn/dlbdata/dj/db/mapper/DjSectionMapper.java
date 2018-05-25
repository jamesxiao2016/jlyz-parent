package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.vo.party.SectionInfoVo;
import tk.mybatis.mapper.common.Mapper;

public interface DjSectionMapper extends Mapper<DjSection> {
    DjSection getByPrincipalId(Long principalId);
    SectionInfoVo getSectionInfo(Long sectionId);
}