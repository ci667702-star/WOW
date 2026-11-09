package com.example.JavaProject.domain.facilities.controller;

import com.example.JavaProject.domain.facilities.service.FacilitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO(해당 단계): 시설 API
@RestController
@RequestMapping("/api/v1/facilities")
@RequiredArgsConstructor
public class FacilitiesController {

    private final FacilitiesService facilitiesService;
}
