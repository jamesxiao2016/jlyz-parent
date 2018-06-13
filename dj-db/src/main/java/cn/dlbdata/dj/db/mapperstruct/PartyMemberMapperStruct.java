package cn.dlbdata.dj.db.mapperstruct;

import cn.dlbdata.dj.db.dto.partymember.PartyMemberAddOrUpdateDto;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PartyMemberMapperStruct {

    @Mapping(target = "birthDate", ignore = true)
    DjPartymember dtoToEntity(PartyMemberAddOrUpdateDto dto);

    @Mapping(target = "birthDate", ignore = true)
    DjPartymember updateToEntity(PartyMemberAddOrUpdateDto dto,@MappingTarget DjPartymember partymember);
}
