package com.example.JavaProject.domain.rules.service;

import com.example.JavaProject.domain.rules.repository.RulesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RulesService {
    private final RulesRepository rulesrepository;

    public RulesService(RulesRepository rulesrepository) {
        this.rulesrepository = rulesrepository;
    }


}
