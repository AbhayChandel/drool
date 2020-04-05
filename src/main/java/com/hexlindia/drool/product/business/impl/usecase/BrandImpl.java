package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.Brand;
import com.hexlindia.drool.product.data.repository.api.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BrandImpl implements Brand {

    private final BrandRepository brandRepository;


    @Override
    public List<String> getRatingMetrics(String id) {
        return brandRepository.getRatingMetrics(new ObjectId(id));
    }
}
