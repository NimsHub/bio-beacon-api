package com.nimshub.biobeacon.ml;

import com.nimshub.biobeacon.exceptions.SessionNotFoundException;
import com.nimshub.biobeacon.session.Session;
import com.nimshub.biobeacon.session.SessionMotionData;
import com.nimshub.biobeacon.session.SessionMotionDataRepository;
import com.nimshub.biobeacon.utils.Converters;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.DoubleStream;

import static com.nimshub.biobeacon.constants.Constants.COMMA;

/**
 * @Author Nirmala : 08:October:2023
 * This service implements all methods required to handle the business logic of PreProcessor
 */
@Service
@RequiredArgsConstructor
public class PreProcessorService {

    private final SessionMotionDataRepository motionDataRepository;

    @Value("${ml.scripts}")
    String scripts;
    @Value("${ml.data}")
    String data;
    Logger logger = LoggerFactory.getLogger(PreProcessorService.class);

    public void preProcessData(Session session) {
        Converters converter = new Converters();
        SessionMotionData motionData = motionDataRepository.findSessionMotionDataBySession(session)
                .orElseThrow(() -> new SessionNotFoundException("session with id : %s not found"));
        logger.info("Data pre-processing");
        List<String> motionData1 = converter.getStringList(motionData.getDeviceOneMotionData());
        List<String> motionData2 = converter.getStringList(motionData.getDeviceTwoMotionData());
        List<String> motionData3 = converter.getStringList(motionData.getDeviceThreeMotionData());
        List<String> motionData4 = converter.getStringList(motionData.getDeviceFourMotionData());
        List<String> motionData5 = converter.getStringList(motionData.getDeviceFiveMotionData());

        int numberOfSamples = (int) DoubleStream.of(
                motionData1.size(),
                motionData2.size(),
                motionData3.size(),
                motionData4.size(),
                motionData5.size()).min().orElse(1) / 3;


        StringBuilder builder = new StringBuilder();
        builder.append("x1,").append("y1,").append("z1,")
                .append("x2,").append("y2,").append("z2,")
                .append("x3,").append("y3,").append("z3,")
                .append("x4,").append("y4,").append("z4,")
                .append("x5,").append("y5,").append("z5\n");
        for (int i = 1; i < numberOfSamples; i++) {
            StringJoiner samples = new StringJoiner(COMMA);

            samples.add(getSampleFromDevice(i, motionData1));
            samples.add(getSampleFromDevice(i, motionData2));
            samples.add(getSampleFromDevice(i, motionData3));
            samples.add(getSampleFromDevice(i, motionData4));
            samples.add(getSampleFromDevice(i, motionData5));

            builder.append(samples).append("\n");
        }
        logger.info("Attempt to save csv");
        saveFile(builder.toString());
    }

    private void saveFile(String csvString) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(data))) {
            writer.write(csvString);
            logger.info("CSV file saved successfully at {}", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSampleFromDevice(int i, List<String> data) {
        StringJoiner joiner = new StringJoiner(COMMA);
        joiner.add(data.get(i * 3 - 3)).add(data.get(i * 3 - 2)).add(data.get(i * 3 - 1));
        return joiner.toString();
    }
}