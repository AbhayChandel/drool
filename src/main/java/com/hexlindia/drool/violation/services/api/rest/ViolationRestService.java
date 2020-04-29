package com.hexlindia.drool.violation.services.api.rest;

import com.hexlindia.drool.violation.dto.ViolationReportDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/${rest.uri.version}/violation")
public interface ViolationRestService {

    @GetMapping(value = "/template/{postType}")
    ResponseEntity<List<String>> getViolationsTemplate(@PathVariable("postType") String postType);

    @PostMapping(value = "/report/save")
    ResponseEntity<Boolean> post(@RequestBody ViolationReportDto violationReportDto);
}
