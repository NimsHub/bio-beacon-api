package com.nimshub.biobeacon.ml;

import com.nimshub.biobeacon.activities.ActivityTime;
import com.nimshub.biobeacon.activities.ActivityTimeRepository;
import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.Session;
import com.nimshub.biobeacon.session.SessionRepository;
import com.nimshub.biobeacon.utils.Converters;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.nimshub.biobeacon.constants.Constants.*;


/**
 * @Author Nirmala : 30:August:2023
 * This service implements all methods required to handle the business logic of ModelService
 */
@Service
@RequiredArgsConstructor
public class ModelService {
    private final ActivityTimeRepository activityTimeRepository;
    private final SessionRepository sessionRepository;
    Logger logger = LoggerFactory.getLogger(ModelService.class);

    @Value("${ml.process-directory}")
    String processDirectory;
    @Value("${ml.script-init-command}")
    String scriptInitCommand;

    /**
     * This method initialize the Machine learning model
     *
     * @return : String
     */
    public String doAnalyze() {

        String outputCsv = "";
        try {
            String scriptName = "scripts/model5.py";

            String[] command = {BASH, COMMAND, scriptInitCommand + scriptName};

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            processBuilder.directory(new File(processDirectory));

            Process process = processBuilder.start();
            String executionCommand = Arrays.toString(command);
            logger.info("Executing command : {}", executionCommand);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<String> outputLines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                outputLines.add(line);
            }
            String output = outputLines.subList(3, outputLines.size()).toString()
                    .replace(", ", "")
                    .replace("[", "")
                    .replace("]", "");

            outputCsv = output.replace(" ", ",");
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                logger.info("Script executed successfully. Predicted output : {}", output);
            } else {
                logger.error("Script execution failed with exit code : {}", output);
            }

        } catch (IOException | InterruptedException e) {
            logger.warn(e.getMessage(), e);

        }
        return outputCsv;
    }

    /**
     * This method read the predicted output from the model and predict the activities
     *
     * @param id : Integer
     */
    public void predictActivities(Integer id) {
        Converters converter = new Converters();
        String output = doAnalyze();

        List<String> predictionList = converter.getStringList(output);

        int cyclingTime = Collections.frequency(predictionList, CYCLING) * SAMPLING_SIZE;
        int pushUpTime = Collections.frequency(predictionList, PUSH_UP) * SAMPLING_SIZE;
        int runTime = Collections.frequency(predictionList, RUNNING) * SAMPLING_SIZE;
        int squatTime = Collections.frequency(predictionList, SQUAT) * SAMPLING_SIZE;
        int tableTennisTime = Collections.frequency(predictionList, TABLE_TENNIS) * SAMPLING_SIZE;
        int walkTime = Collections.frequency(predictionList, WALKING) * SAMPLING_SIZE;

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));

        ActivityTime activityTime = ActivityTime.builder()
                .session(session)
                .cycling(cyclingTime)
                .pushUp(pushUpTime)
                .running(runTime)
                .squat(squatTime)
                .tableTennis(tableTennisTime)
                .walking(walkTime)
                .build();

        activityTimeRepository.save(activityTime);
    }


}