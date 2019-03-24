package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.UnitOfMeasure;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasure> listAllUoms();
}
