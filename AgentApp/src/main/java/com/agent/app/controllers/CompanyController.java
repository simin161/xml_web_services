package com.agent.app.controllers;

import com.agent.app.model.Company;
import com.agent.app.security.TokenUtils;
import com.agent.app.service.CompanyService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/getAllCompanyRequests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Company> getAllCompanyRequests(){
        return companyService.getAllCompanyRequests();
    }

    @PostMapping("/changeCompanyStatus")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean changeCompanyStatus(@RequestBody Map<String, String> message){
        return companyService.changeCompanyStatus(message);
    }

    @GetMapping("/getCompaniesForUser")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public List<Company> getCompaniesForUser(@RequestHeader HttpHeaders header){
        return companyService.getCompaniesForUser(tokenUtils.getUsernameFromToken(header.getFirst(HttpHeaders.AUTHORIZATION)));
    }

    @PostMapping("/editCompany")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public boolean editCompany(@RequestHeader HttpHeaders header, @RequestBody Map<String, String> message){
        return companyService.editCompany(message);
    }
}
