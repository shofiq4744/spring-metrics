package com.shofiq.springmetrics.manger.controller;

import com.shofiq.springmetrics.manger.PrometheusMicrometerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestMetricsController {

    private final PrometheusMicrometerManager prometheusMicrometerManager;

    @Autowired
    public TestMetricsController(PrometheusMicrometerManager prometheusMicrometerManager){
        this.prometheusMicrometerManager = prometheusMicrometerManager;
    }

    @GetMapping(value = "/metrics/test")
    public Mono<?> testMetrics(){
        return Mono.just("Success")
                .flatMap(res->{
                    prometheusMicrometerManager.increaseSuccess();
                    return Mono.just(new ResponseEntity<>("SUCCESS", HttpStatus.OK));})
                .onErrorResume((err) -> {
                    prometheusMicrometerManager.increaseError();
                    return Mono.just(new ResponseEntity<>("ERROR", HttpStatus.OK));
                })
                .doFinally(rs-> System.out.println("all done "+ rs))
                .doOnCancel(()->System.out.println("subscribe cancel"));
    }
}
