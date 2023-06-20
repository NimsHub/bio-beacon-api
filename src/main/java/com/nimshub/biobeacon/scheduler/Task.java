package com.nimshub.biobeacon.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Task {
    @Scheduled(fixedRate = 10000)
    @Async
    void testRun() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
    }
}
