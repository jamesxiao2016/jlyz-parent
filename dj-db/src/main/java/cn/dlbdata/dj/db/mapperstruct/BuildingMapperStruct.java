package cn.dlbdata.dj.db.mapperstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cn.dlbdata.dj.db.dto.building.BuildingAddOrUpdateDto;
import cn.dlbdata.dj.db.pojo.DjBuilding;

@Mapper(componentModel = "spring")
public interface BuildingMapperStruct {

    @Mapping(target = "djSectionId",source = "sectionId")
    DjBuilding dtoToEntity(BuildingAddOrUpdateDto dto);

    @Mapping(target = "djSectionId",source = "sectionId")
    DjBuilding updateToEntity(BuildingAddOrUpdateDto dto, @MappingTarget DjBuilding building);
}
