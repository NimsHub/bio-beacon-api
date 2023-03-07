package com.nimshub.biobeacon.device;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    /**
     * This method creates new Device
     * @return : Device
     */
    public Device createDevice() {
        Device device = Device.builder()
                .apiKey(UUID.randomUUID().toString())
                .build();
        return deviceRepository.save(device);
    }

    /**
     * This method retrieves all the devices
     * @return : List<Device>
     */
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}
