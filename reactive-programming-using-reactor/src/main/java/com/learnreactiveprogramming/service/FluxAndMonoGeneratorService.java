package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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

    public Flux<String> fluxOfNamesUpperCase() {
        return Flux.fromIterable(customerNamesList())
                .map(name -> name.toUpperCase())
                .log();
    }

    public Mono<String> customerMono(){
        return Mono.just("Adriano");
    }

    private List<String> customerNamesList(){
        return List.of("Adriano", "Sara", "Maria");
    }

}
