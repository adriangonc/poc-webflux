package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();


    @Test
    void namesFluxTest() {
        //given


        //when
        var namesFlux = fluxAndMonoGeneratorService.fluxOfNames();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("Adriano", "Sara", "Maria")
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    void customerMonoTest(){
        //given


        //when
        var customerMono = fluxAndMonoGeneratorService.customerMono();

        //then
        StepVerifier.create(customerMono)
                .expectNext("Adriano")
                .verifyComplete();
    }
}