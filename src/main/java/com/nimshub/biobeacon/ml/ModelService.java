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

    public String doAnalyze() {

        String outputCsv = "";
        try {
            String scriptName = "scripts/model5.py";

            String[] command = {BASH, COMMAND, SCRIPT + scriptName};

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            processBuilder.directory(new File(processDirectory));

            Process process = processBuilder.start();

            logger.info("Executing command : {}", Arrays.toString(command));

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
            e.printStackTrace();

        }
        return outputCsv;
    }

    public void predictActivities(Integer id) {
        Converters converter = new Converters();
        String output = doAnalyze();

        List<String> predictionList = converter.getStringList(output);

        int cyclingTime = Collections.frequency(predictionList, "0")*10;
        int pushUpTime = Collections.frequency(predictionList, "1")*10;
        int runTime = Collections.frequency(predictionList, "2")*10;
        int squatTime = Collections.frequency(predictionList, "3")*10;
        int tableTennisTime = Collections.frequency(predictionList, "4")*10;
        int walkTime = Collections.frequency(predictionList, "5")*10;

        Session session = sessionRepository.findById(id)
                .orElseThrow(()-> new SessionNotFoundException("Session not found"));

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