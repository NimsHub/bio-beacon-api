package com.nimshub.biobeacon.device;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public Device createDevice(){
        Device device = Device.builder()
                .apiKey(UUID.randomUUID().toString())
                .build();
        return deviceRepository.save(device);
    }
    public List<Device> getAllDevices(){
        List<Device> devices = deviceRepository.findAll();
        return devices;
    }
}
