package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.BrandEntity;

import java.util.Set;

public interface BrandService {
    Set<BrandEntity> getAllBrands();
}
