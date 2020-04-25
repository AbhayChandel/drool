package com.hexlindia.drool.violation.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.*;
import com.hexlindia.drool.violation.data.doc.ViolationReportDoc;
import com.hexlindia.drool.violation.dto.ViolationReportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class, PostRefMapper.class, UserRefMapper.class})
public interface ViolationReportMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    ViolationReportDoc toDoc(ViolationReportDto violationReportDto);

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    ViolationReportDto toDto(ViolationReportDoc violationReportDoc);
}
