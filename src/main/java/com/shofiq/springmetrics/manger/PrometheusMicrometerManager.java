package com.shofiq.springmetrics.manger;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PrometheusMicrometerManager {
    private final MeterRegistry meterRegistry;
    private Counter successRequestCount;
    private Counter errorRequestCount;

    public PrometheusMicrometerManager(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        initCounters();
    }
    public void increaseSuccess(){
        successRequestCount.increment();
    }

    public void increaseError(){
        errorRequestCount.increment();
    }

    private void initCounters() {
        successRequestCount = this.meterRegistry.counter("mock.request", "type", "success"); // 1 - create a counter
        errorRequestCount = Counter.builder("mock.request")    // 2 - create a counter using the fluent API
                .tag("type", "error")
                .description("The number of request")
                .register(meterRegistry);
    }


}
