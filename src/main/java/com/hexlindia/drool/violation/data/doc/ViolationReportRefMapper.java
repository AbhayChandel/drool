package com.hexlindia.drool.violation.data.doc;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ViolationReportRefMapper {

    ViolationReportRef toRef(ViolationReportDoc violationReportDoc);
}
