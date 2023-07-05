package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

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
    void customerMonoTest() {
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

    @Test
    void fluxOfCharsFromNamesConcatMapSyncTest() {
        //given
        int minStringLength = 2;

        //when
        var charsFromNames = fluxAndMonoGeneratorService.fluxOfCharsFromNamesConcatMapSync(minStringLength);

        //then
        StepVerifier.create(charsFromNames)
                .expectNext("A", "D", "R", "I")
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapTest() {
        //given
        int minStringLength = 2;

        //when
        var listOfStrings = fluxAndMonoGeneratorService.namesMonoFlatMap(minStringLength);

        //then
        StepVerifier.create(listOfStrings)
                .expectNext(List.of("A", "D", "R", "I", "A", "N", "O"))
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapManyTest() {
        //given
        int minStringLength = 2;

        //when
        var listOfStrings = fluxAndMonoGeneratorService.namesMonoFlatMapMany(minStringLength);

        //then
        StepVerifier.create(listOfStrings)
                .expectNext("A", "D", "R", "I", "A", "N", "O")
                .verifyComplete();
    }

    @Test
    void namesFluxTransformTest() {
        //given
        int minStringLength = 2;

        //when
        var listOfStrings = fluxAndMonoGeneratorService.fluxOfNamesTransform(minStringLength);

        //then
        StepVerifier.create(listOfStrings)
                .expectNext("ADRIANO", "SARA", "MARIA")
                .verifyComplete();
    }

    @Test
    void namesFluxTransformDefaultEmptyTest() {
        //given
        int minStringLength = 20;

        //when
        var listOfStrings = fluxAndMonoGeneratorService.fluxOfNamesTransform(minStringLength);

        //then
        StepVerifier.create(listOfStrings)
                .expectNext("NOT_FOUND")
                .verifyComplete();
    }

    @Test
    void namesFluxTransformSwitchIfEmptyTest() {
        //given
        int minStringLength = 16;

        //when
        var listOfStrings = fluxAndMonoGeneratorService.fluxOfNamesTransformSwitchIfEmpty(minStringLength);

        //then
        StepVerifier.create(listOfStrings)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }


    @Test
    void concatOperatorLettersAndNumbersTest() {
        //given

        //when
        var fluxOfCharsAndNumbers = fluxAndMonoGeneratorService.concatOperatorLettersAndNumbers();

        //then
        StepVerifier.create(fluxOfCharsAndNumbers)
                .expectNext("A", "B", "C", "D", "1", "2", "3", "4")
                .verifyComplete();
    }

    @Test
    void ShouldConcatLetterAndNumberMonoToFluxTest() {
        //given

        //when
        var fluxOfCharsAndNumbers = fluxAndMonoGeneratorService.concatWithOperatorMonoToFlux();

        //then
        StepVerifier.create(fluxOfCharsAndNumbers)
                .expectNext("A", "1")
                .verifyComplete();
    }

    @Test
    void testsWithMerge() {

        //given

        //when
        var fluxOfCharsAndNumbers = fluxAndMonoGeneratorService.testsWithMerge();

        //then
        StepVerifier.create(fluxOfCharsAndNumbers)
                .expectNext("A", "1", "B", "2", "C", "3")
                .verifyComplete();

    }

    @Test
    void testsWithMergeSequential() {

        //given

        //when
        var fluxOfCharsAndNumbers = fluxAndMonoGeneratorService.testsWithMergeSequential();

        //then
        StepVerifier.create(fluxOfCharsAndNumbers)
                .expectNext("A", "B", "C", "1", "2", "3")
                .verifyComplete();

    }

    @Test
    void testsWithUserFlux() {

        //given

        //when
        var userFlux = fluxAndMonoGeneratorService.testsWithFluxUser();

        //then
        StepVerifier.create(userFlux)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    void testsWithZip() {

        //given

        //when
        var value = fluxAndMonoGeneratorService.exploreZip();

        //then
        StepVerifier.create(value)
                .expectNext("A1", "B2", "C3")
                .verifyComplete();

    }

    @Test
    void testsWithZipV2() {

        //given

        //when
        var value = fluxAndMonoGeneratorService.exploreZipV2();

        //then
        StepVerifier.create(value)
                .expectNext("A1*", "B2-", "C3+")
                .verifyComplete();

    }
}