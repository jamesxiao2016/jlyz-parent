package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.db.vo.jlyz.SectionResVo;
import cn.dlbdata.dj.db.vo.party.SectionInfoVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface DjSectionMapper extends Mapper<DjSection> {
    DjSection getByPrincipalId(Long principalId);

    SectionInfoVo getSectionInfo(Long sectionId);

    boolean existWithName(@Param("name") String name,@Param("id") Long id);
    
    Integer selectSectionPartymemberCount(Long id);
    
    Integer selectSectionDeptCount(Long id);
    
    Integer selectSectionBuildingCount(Long id);
    
    List<SectionResVo> queryAllSections();
}