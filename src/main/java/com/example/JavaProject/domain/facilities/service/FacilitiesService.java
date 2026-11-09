package com.example.JavaProject.domain.facilities.service;

import com.example.JavaProject.domain.facilities.repository.FacilitiesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// TODO(해당 단계): 시설 비즈니스 로직
@Slf4j
@Service
@RequiredArgsConstructor
public class FacilitiesService {

    private final FacilitiesRepository facilitiesRepository;
}
