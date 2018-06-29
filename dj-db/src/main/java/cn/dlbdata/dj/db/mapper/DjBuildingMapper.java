package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.dto.IdNameDto;
import cn.dlbdata.dj.db.pojo.DjBuilding;
import cn.dlbdata.dj.db.vo.jlyz.BuildingVo;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjBuildingMapper extends Mapper<DjBuilding> {
    boolean existWithCode(@Param("code") String code,@Param("id") Long id);

    boolean existWithName(@Param("name") String name,@Param("id") Long id);

    List<IdNameDto> getBuildingIdAndNameBySectionId(long sectionId);
    

	List<BuildingVo> getListBuildingBySectionId(Long id);

}