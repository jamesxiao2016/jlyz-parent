//package cn.dlbdata.dj.db.mapstruct;
//import cn.dlbdata.dj.db.pojo.DjBuilding;
//import org.mapstruct.Mapper;
//import cn.dlbdata.dj.db.dto.building.*;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//@Mapper(componentModel = "spring")
//public interface BuildingMapStruct {
//
//    @Mapping(target = "djSectionId",source = "sectionId")
//    DjBuilding dtoToEntity(BuildingAddOrUpdateDto dto);
//
//    @Mapping(target = "djSectionId",source = "sectionId")
//    DjBuilding updateToEntity(BuildingAddOrUpdateDto dto, @MappingTarget DjBuilding building);
//}
