package cn.dlbdata.dj.db.mapperstruct;
import cn.dlbdata.dj.db.pojo.DjBuilding;
import org.mapstruct.Mapper;
import cn.dlbdata.dj.db.dto.building.*;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BuildingMapperStruct {

    @Mapping(target = "djSectionId",source = "sectionId")
    DjBuilding dtoToEntity(BuildingAddOrUpdateDto dto);

    @Mapping(target = "djSectionId",source = "sectionId")
    DjBuilding updateToEntity(BuildingAddOrUpdateDto dto, @MappingTarget DjBuilding building);
}
