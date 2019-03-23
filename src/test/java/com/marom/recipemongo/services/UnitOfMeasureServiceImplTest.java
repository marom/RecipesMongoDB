package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.UnitOfMeasure;
import com.marom.recipemongo.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @InjectMocks
    UnitOfMeasureService unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getListOfAllUoms() {

        //given
        UnitOfMeasure spoon = UnitOfMeasure.builder().id("uom1").description("desc1").build();
        UnitOfMeasure pinch = UnitOfMeasure.builder().id("pinch").description("desc2").build();
        List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(spoon, pinch);

        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureList);

        List<UnitOfMeasure> returnedUom = unitOfMeasureService.listAllUoms();

        //then
        assertThat(returnedUom, hasSize(2));

    }
}
