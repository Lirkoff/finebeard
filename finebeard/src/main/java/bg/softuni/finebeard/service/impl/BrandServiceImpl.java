package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.BrandEntity;
import bg.softuni.finebeard.repository.BrandRepository;
import bg.softuni.finebeard.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Set<BrandEntity> getAllBrands() {
        return new HashSet<>(brandRepository.findAll());
    }
}
