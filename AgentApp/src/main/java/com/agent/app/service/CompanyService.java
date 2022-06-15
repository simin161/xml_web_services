package com.agent.app.service;

import com.agent.app.model.Authority;
import com.agent.app.model.Company;
import com.agent.app.model.Status;
import com.agent.app.model.User;
import com.agent.app.repository.AuthorityRepository;
import com.agent.app.repository.CompanyRepository;
import com.agent.app.repository.UserRepository;
import com.agent.app.utility.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    public boolean registerCompany(Map<String, String> message) {
        boolean retVal = false;
        if(Validation.validateTextWithNumbers(message.get("name")) && Validation.validateOnlyText(message.get("field"))
            && Validation.validateNonBrackets(message.get("description")) && (Validation.validateEmail(message.get("contactInfo"))
            || Validation.validatePhone(message.get("contactInfo")))) {
            Company company = new Company();
            User user = userRepository.findByEmail(message.get("email"));
            company.setName(message.get("name"));
            company.setOwner(user);
            company.setField(message.get("field"));
            company.setDescription(message.get("description"));
            company.setStatus(Status.PENDING);
            company.setContactInfo(message.get("contactInfo"));
            companyRepository.save(company);
            retVal = true;
        }
        return retVal;
    }

    public List<Company> getAllCompanyRequests() {
        return companyRepository.findAllByStatus(Status.PENDING);
    }

    public boolean changeCompanyStatus(Map<String, String> message) {
        boolean retVal = false;
        if(Validation.validateNonBrackets(message.get("id"))) {
            Company company = companyRepository.findById(Long.parseLong(message.get("id"))).orElse(null);
            if (message.get("status").equals("ACCEPTED")) {
                company.setStatus(Status.ACCEPTED);
                User user = company.getOwner();
                List<Authority> authorityList = new ArrayList<>();
                authorityList.add(authorityRepository.findById(2L).orElse(null));
                authorityList.add(authorityRepository.findById(4L).orElse(null));
                user.setAuthorities(authorityList);
                userRepository.save(user);
            } else {
                company.setStatus(Status.DECLINED);
            }
            companyRepository.save(company);
            retVal =  true;
        }
        return retVal;
    }

    public List<Company> getCompaniesForUser(String email) {
        return companyRepository.findAllByOwner(userRepository.findByEmail(email));
    }

    public boolean editCompany(Map<String, String> message) {
        boolean retVal = false;
        if(Validation.validateTextWithNumbers(message.get("name")) && Validation.validateOnlyText(message.get("field"))
                && Validation.validateNonBrackets(message.get("description")) && (Validation.validateEmail(message.get("contactInfo"))
                || Validation.validatePhone(message.get("contactInfo")))) {
            Company company = companyRepository.findById(Long.valueOf(message.get("id"))).orElse(null);
            company.setDescription(message.get("description"));
            company.setField(message.get("field"));
            company.setName(message.get("name"));
            company.setContactInfo(message.get("contactInfo"));
            companyRepository.save(company);
            retVal = true;
        }
        return retVal;
    }
}
