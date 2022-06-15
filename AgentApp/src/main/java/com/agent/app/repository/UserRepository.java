package com.agent.app.repository;

import com.agent.app.model.User;
import com.agent.app.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByVerificationCode(VerificationCode verificationCode);
}
