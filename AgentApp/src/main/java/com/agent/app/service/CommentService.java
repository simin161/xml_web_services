package com.agent.app.service;

import com.agent.app.model.*;
import com.agent.app.repository.CommentRepository;
import com.agent.app.repository.JobOfferRepository;
import com.agent.app.repository.UserRepository;
import com.agent.app.utility.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    public boolean addComment(Map<String, String> message) {
        if(Validation.validateNonBrackets(message.get("text")) && Validation.validateNonBrackets(message.get("process"))
            && Validation.validateNumbers(message.get("salary"))) {
            Comment comment = new Comment();
            User user = userRepository.findByEmail(message.get("email"));
            JobOffer jobOffer = jobOfferRepository.getById(Long.parseLong(message.get("id")));
            comment.setOwner(user);
            comment.setText(message.get("text"));
            comment.setInterview_process(message.get("process"));
            comment.setSalary(Double.parseDouble(message.get("salary")));
            comment.setJobOffer(jobOffer);
            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    public List<Comment> commentsByOfferId(Map<String, String> message) {
        return commentRepository.commentsByOfferId(Long.parseLong(message.get("id")));
    }
}
