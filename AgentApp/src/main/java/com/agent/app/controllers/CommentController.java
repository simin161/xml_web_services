package com.agent.app.controllers;

import com.agent.app.model.Comment;
import com.agent.app.security.TokenUtils;
import com.agent.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api", produces= MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private TokenUtils tokenUtils;

    @PostMapping("/addComment")
    @PreAuthorize("hasRole('ROLE_REG_CUSTOMER')")
    public boolean addComment(@RequestHeader HttpHeaders header, @RequestBody Map<String, String> message){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        message.put("email",tokenUtils.getUsernameFromToken(value));
        return commentService.addComment(message);
    }


    @PostMapping("/commentsByOfferId")
    @PreAuthorize("hasRole('ROLE_REG_CUSTOMER') || hasRole('ROLE_COMPANY_OWNER') ")
    public List<Comment> addComment(@RequestBody Map<String, String> message){
        return commentService.commentsByOfferId(message);
    }

}
