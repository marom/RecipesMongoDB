package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.unitOfMeasureRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Flux<UnitOfMeasure> listAllUoms() {

       return unitOfMeasureRepository.findAll();
    }
}
