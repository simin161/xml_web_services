package com.agent.app.controllers;

import com.agent.app.model.Company;
import com.agent.app.service.CompanyService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/getAllCompanyRequests")
    public List<Company> getAllCompanyRequests(){
        return companyService.getAllCompanyRequests();
    }

    @PostMapping("/changeCompanyStatus")
    public boolean changeCompanyStatus(@RequestBody Map<String, String> message){
        return companyService.changeCompanyStatus(message);
    }
}
