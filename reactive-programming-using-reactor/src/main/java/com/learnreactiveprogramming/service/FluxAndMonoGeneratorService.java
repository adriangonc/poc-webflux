package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    public static void main(String[] args){
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
                .filter( name -> name.length() > nameSize)
                .map(name -> name + "-" + name.length())
                .log();
    }

    public Mono<String> customerMono(){
        return Mono.just("Adriano");
    }

    private List<String> customerNamesList(){
        return List.of("Adriano", "Sara", "Maria");
    }

    public Flux<String> fluxOfCharsFromNames(int nameSize) {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .filter( name -> name.length() > nameSize)
                .flatMap( name -> splitStrings(name))
                .log();
    }

    private Flux<String> splitStrings(String name){
        var nameCharArray = name.split("");
        return Flux.fromArray(nameCharArray);
    }

    public Flux<String> fluxOfCharsFromNamesFlatMapAsync(int nameSize) {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .filter( name -> name.length() > nameSize)
                .flatMap( name -> splitStringsWhithDelay(name))
                .log();
    }

    //A principal diferença entre o ConcatMap e o FlatMap, e que o concat map retorna os resultados ordenados
    public Flux<String> fluxOfCharsFromNamesConcatMapSync(int nameSize) {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .filter( name -> name.length() > nameSize)
                .concatMap( name -> splitStringsWhithDelay(name))
                .log();
    }

    private Flux<String> splitStringsWhithDelay(String name){
        var delay = new Random().nextInt(800);
        var nameCharArray = name.split("");
        return Flux.fromArray(nameCharArray).delayElements(Duration.ofMillis(delay));
    }


    public Mono<List<String>> namesMonoFlatMap(int stringLenght){
        return Mono.just("Adriano")
                .map(String::toUpperCase)
                .filter(str -> str.length() > stringLenght)
                .flatMap(this::splitStringsMono);
    }

    private Mono<List<String>> splitStringsMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);

        return Mono.just(charList).log();
    }
}
