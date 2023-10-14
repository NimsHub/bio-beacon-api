package com.nimshub.biobeacon.scheduler;

import com.nimshub.biobeacon.ml.ModelService;
import com.nimshub.biobeacon.ml.PreProcessorService;
import com.nimshub.biobeacon.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Task {

    private final SessionRepository sessionRepository;
    private final PreProcessorService preProcessor;
    private final ModelService modelService;
    Logger logger = LoggerFactory.getLogger(Task.class);

    @Scheduled(fixedRateString = "${scheduling.fixed-rate}")
    @Async
    void runTask() {
        logger.info("Running scheduled job...");
        sessionRepository.findTopByIsCompleteTrueAndAnalysisStatusFalse().ifPresent(session -> {
            Integer sessionId = session.getId();
            preProcessor.preProcessData(session);
            session.setAnalysisStatus(true);
            sessionRepository.save(session);
            modelService.predictActivities(sessionId);
        });
    }
}
