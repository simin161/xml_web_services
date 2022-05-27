package com.agent.app.service;

import com.agent.app.model.Company;
import com.agent.app.model.Status;
import com.agent.app.model.User;
import com.agent.app.repository.CompanyRepository;
import com.agent.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompanyService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    public boolean registerCompany(Map<String, String> message) {
        Company company = new Company();
        User user = userRepository.findByEmail(message.get("email"));
        company.setName(message.get("name"));
        company.setOwner(user);
        company.setField(message.get("field"));
        company.setDescription(message.get("description"));
        company.setStatus(Status.PENDING);
        companyRepository.save(company);
        return true;
    }
}
