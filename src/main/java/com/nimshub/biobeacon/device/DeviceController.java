package com.nimshub.biobeacon.device;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devices")
public class DeviceController {
    private final DeviceService deviceService;
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Device> createDevice(){
        return new ResponseEntity<>(deviceService.createDevice(), HttpStatus.CREATED) ;
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Device>> getAllDevices(){
        return new ResponseEntity<>(deviceService.getAllDevices(), HttpStatus.OK) ;
    }
}
