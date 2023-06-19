package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        var customerMap = Map.of("Adriano", 1988, "Sara", 1986, "Maria", 1970);
        return Flux.fromIterable(customerMap.keySet()).log();
    }

    public Mono<String> customerMono(){
        return Mono.just("Adriano");
    }

}
