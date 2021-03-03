package com.digitalinnovationone.beerapi.service;

import com.digitalinnovationone.beerapi.builder.BeerDTOBuilder;
import com.digitalinnovationone.beerapi.dto.BeerDTO;
import com.digitalinnovationone.beerapi.entity.Beer;
import com.digitalinnovationone.beerapi.exceptions.BeerAlreadyRegisteredException;
import com.digitalinnovationone.beerapi.mapper.BeerMapper;
import com.digitalinnovationone.beerapi.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    public static final long INVALID_BEER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShoulBeCreated() throws BeerAlreadyRegisteredException {

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedSavedBeer = beerMapper.toModel(beerDTO);

        when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        BeerDTO createdBeerDTO =  beerService.createBeer(beerDTO);

        assertEquals(beerDTO.getId(), createdBeerDTO.getId());
        assertEquals(beerDTO.getName(), createdBeerDTO.getName());

    }
}
