package com.learnreactiveprogramming.service;

import model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import utils.UserUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    UserUtils userUtils = new UserUtils();

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.fluxOfNames().subscribe(
                customer -> {
                    System.out.println("Customer flux names: " + customer);
                }
        );

        fluxAndMonoGeneratorService.customerMono().subscribe(
                customer -> {
                    System.out.println("Customer mono name is: " + customer);
                }
        );
    }

    public Flux<String> fluxOfNames() {
        return Flux.fromIterable(customerNamesList()).log();
    }

    public Flux<String> fluxOfNamesUpperCase(int nameSize) {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .filter(name -> name.length() > nameSize)
                .map(name -> name + "-" + name.length())
                .log();
    }

    public Mono<String> customerMono() {
        return Mono.just("Adriano");
    }

    private List<String> customerNamesList() {
        return List.of("Adriano", "Sara", "Maria");
    }

    public Flux<String> fluxOfCharsFromNames(int nameSize) {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .filter(name -> name.length() > nameSize)
                .flatMap(name -> splitStrings(name))
                .log();
    }

    private Flux<String> splitStrings(String name) {
        var nameCharArray = name.split("");
        return Flux.fromArray(nameCharArray);
    }

    public Flux<String> fluxOfCharsFromNamesFlatMapAsync(int nameSize) {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .filter(name -> name.length() > nameSize)
                .flatMap(name -> splitStringsWithDelay(name))
                .log();
    }

    //A principal diferença entre o ConcatMap e o FlatMap, e que o concat map retorna os resultados ordenados
    public Flux<String> fluxOfCharsFromNamesConcatMapSync(int nameSize) {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .filter(name -> name.length() > nameSize)
                .concatMap(name -> splitStringsWithDelay(name))
                .log();
    }

    private Flux<String> splitStringsWithDelay(String name) {
        var delay = new Random().nextInt(500);
        var nameCharArray = name.split("");
        return Flux.fromArray(nameCharArray).delayElements(Duration.ofMillis(delay));
    }


    public Mono<List<String>> namesMonoFlatMap(int stringLength) {
        return Mono.just("Adriano")
                .map(String::toUpperCase)
                .filter(str -> str.length() > stringLength)
                .flatMap(this::splitStringsMono);
    }

    public Flux<String> namesMonoFlatMapMany(int stringLenght) {
        return Mono.just("Adriano")
                .map(String::toUpperCase)
                .filter(str -> str.length() > stringLenght)
                .flatMapMany(this::splitStrings); //FlatMapMany funciona somente como Flux sendo possível mapear um Mono para flux
    }

    private Mono<List<String>> splitStringsMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);

        return Mono.just(charList).log();
    }

    public Flux<String> fluxOfNamesTransform(int nameSize) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(str -> str.length() > nameSize);

        return Flux.fromIterable(customerNamesList())
                .transform(filterMap)
                .defaultIfEmpty("NOT_FOUND")
                .log();
    }

    public Flux<String> fluxOfNamesTransformSwitchIfEmpty(int nameSize) {

        //Interfaces funcionais podem ser passadas como parâmetro
        Function<Flux<String>, Flux<String>> filterMap = name ->
                name.map(String::toUpperCase)
                        .filter(s -> s.length() > nameSize)
                        .flatMap(this::splitStrings);

        Function<Flux<String>, Flux<String>> flatMapWithoutSizeComparator = name ->
                name.map(String::toUpperCase)
                        .flatMap(this::splitStrings);

        var defaultFlux = Flux.just("default").transform(flatMapWithoutSizeComparator);

        return Flux.fromIterable(customerNamesList())
                .transform(filterMap)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> concatOperatorLettersAndNumbers() {
        var letterFlux = Flux.just("A", "B", "C", "D");

        var numberFlux = Flux.just("1", "2", "3", "4");

        return Flux.concat(letterFlux, numberFlux).log();
    }

    public Flux<String> concatWithOperatorLettersAndNumbers() {
        var letterFlux = Flux.just("A", "B", "C");

        var numberFlux = Flux.just("1", "2", "3");

        return letterFlux.concatWith(numberFlux).log();
    }

    public Flux<String> concatWithOperatorMonoToFlux() {
        var letterMono = Mono.just("A");

        var numberMono = Mono.just("1");

        return letterMono.concatWith(numberMono).log();
    }

    public Flux<String> testsWithMerge() {

        var letterFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(50));

        var numberFlux = Flux.just("1", "2", "3")
                .delayElements(Duration.ofMillis(75));

        return Flux.merge(letterFlux, numberFlux).log();

    }

    public Flux<String> testsWithMergeWith() {

        var letterFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(50));

        var numberFlux = Flux.just("1", "2", "3")
                .delayElements(Duration.ofMillis(75));

        return letterFlux.mergeWith(numberFlux).log();

    }

    public Flux<String> testsWithMergeWithMono() {

        var letterMono = Mono.just("A");

        var numberMono = Mono.just("1");

        return letterMono.mergeWith(numberMono).log();

    }

    public Flux<String> testsWithMergeSequential() {

        var letterFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(50));

        var numberFlux = Flux.just("1", "2", "3")
                .delayElements(Duration.ofMillis(75));

        return Flux.mergeSequential(letterFlux, numberFlux).log();

    }

    public Flux<User> testsWithFluxUser() {
        var userFlux = userUtils.createFakeUsers(10);

        return userFlux.filter(f -> f.getActive() == true)
                .filter(f -> f.getBirthDate().isBefore(LocalDate.of((LocalDate.now().getYear() - 18), 01, 01)))
                .filter(f -> f.getName().length() > 3)
                .log();

    }

    public Flux<String> exploreZip() {
        var letterFlux = Flux.just("A", "B", "C");
        var numberFlux = Flux.just("1", "2", "3");

        return Flux.zip(letterFlux, numberFlux,
                        (letter, number) -> letter + number)
                .log();
    }

    public Flux<String> exploreZipV2() {
        var letterFlux = Flux.just("A", "B", "C");
        var numberFlux = Flux.just("1", "2", "3");
        var separatorFlux = Flux.just("*", "-", "+");

        return Flux.zip(letterFlux, numberFlux, separatorFlux)
                .map(tupleCombination -> tupleCombination.getT1()
                        + tupleCombination.getT2()
                        + tupleCombination.getT3())
                .log();
    }

    public Flux<String> exploreZipWith() {
        var letterFlux = Flux.just("A", "B", "C");
        var numberFlux = Flux.just("1", "2", "3");

        return letterFlux.zipWith(numberFlux, (letter, number) -> letter + number)
                .log();
    }

}
