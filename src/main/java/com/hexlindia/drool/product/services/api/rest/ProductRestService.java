package com.hexlindia.drool.product.services.api.rest;

import com.hexlindia.drool.product.dto.ProductAspectTemplatesDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/product")
public interface ProductRestService {

    @GetMapping("/aspecttemplates/id/{id}")
    ResponseEntity<ProductAspectTemplatesDto> getAspectTemplates(@PathVariable("id") ObjectId id);
}
