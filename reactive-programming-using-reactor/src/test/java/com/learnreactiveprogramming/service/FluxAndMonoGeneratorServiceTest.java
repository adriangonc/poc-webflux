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

    @Test
    void fluxOfNamesUpperCase() {
        //given


        //when
        var customerNames = fluxAndMonoGeneratorService.fluxOfNamesUpperCase(3);

        //then
        StepVerifier.create(customerNames)
                .expectNext("ADRIANO-7", "SARA-4", "MARIA-5")
                .verifyComplete();
    }


    @Test
    void fluxOfCharsFromNames() {
        //given


        //when
        var charsFromNames = fluxAndMonoGeneratorService.fluxOfCharsFromNames(1);

        //then
        StepVerifier.create(charsFromNames)
                .expectNextCount(16)
                .verifyComplete();
    }

    @Test
    void fluxOfCharsFromNamesFlatMapAsyncTest() {
        //given
        int minStringLength = 2;

        //when
        var charsFromNames = fluxAndMonoGeneratorService.fluxOfCharsFromNamesFlatMapAsync(minStringLength);

        //then
        StepVerifier.create(charsFromNames)
                .expectNextCount(16)
                .verifyComplete();
    }
}