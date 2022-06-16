package com.vinsguru.grpc.controller;

import com.google.api.Http;
import com.vinsguru.grpc.dto.*;

import com.vinsguru.grpc.helperModel.User;
import com.vinsguru.grpc.helperModel.UserTokenState;
import com.vinsguru.grpc.security.TokenUtils;
import com.vinsguru.grpc.security.auth.JwtAuthenticationRequest;
import com.vinsguru.grpc.service.JobOfferService;
import com.vinsguru.grpc.service.UsersService;
import com.vinsguru.grpc.service.FollowerService;
import com.vinsguru.grpc.service.PostService;
import com.vinsguru.grpc.utility.LoggingStrings;
import com.vinsguru.grpc.utility.Validation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class AggregatorController {

    @Autowired
    private UsersService aggregatorService;

    @Autowired
    private PostService postService;

    @Autowired
   private FollowerService followerService;
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JobOfferService jobOfferService;


    @PostMapping("/register")
    public String addUser(@RequestBody Map<String, String> message, HttpServletRequest request){
        return aggregatorService.addUser(message, getSiteURL(request));
    }

    @PostMapping("/logInUser")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest cred,
                                                                    HttpServletResponse response) {

        System.out.println(cred);
        if(Validation.validateEmail(cred.getEmail())){
            try{
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(cred.getEmail(),
                                cred.getPassword()));
                // Ubaci korisnika u trenutni security kontekst
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Kreiraj token za tog korisnika
                User user = (User) authentication.getPrincipal();
                String jwt = tokenUtils.generateToken(user.getEmail(), "ROLE_REG_USER");
                int expiresIn = tokenUtils.getExpiredIn();


                // Vrati token kao odgovor na uspesnu autentifikaciju
                return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
            }
            catch(Exception e){
                log.error(LoggingStrings.getAuthenticationFailed("com.vinsguru.grpc.controller.AggregatorController.logInUser", e.toString()));
                return null;
            }
        }
        return null;
    }

    @PostMapping("/personalInfo")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public String updateUser(@RequestHeader("Authentication") HttpHeaders header, @RequestBody Map<String, String> userDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
            String email = tokenUtils.getUsernameFromToken(value);
            userDto.put("email",email);
            return aggregatorService.updateUser(userDto);}
        }catch(Exception e){
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.personalInfo", tokenUtils.getUsernameFromToken(value), e.toString()));
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("/education")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public String updateEducation(@RequestHeader("Authentication") HttpHeaders header, @RequestBody EducationDto educationDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
            String email = tokenUtils.getUsernameFromToken(value);
            educationDto.setEmail(email);
            return aggregatorService.updateEducation(educationDto);  }
        }catch(Exception e){
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.education", tokenUtils.getUsernameFromToken(value), e.toString()));
            e.printStackTrace();
        }
        return "";

    }
    @GetMapping("/user/{email:.+}/")
    public UserDto getUserByEmail(@PathVariable("email")String email){
        try{
            if(Validation.validateEmail(email))
                return aggregatorService.getUserByEmail(email);
        }catch(Exception e){
            log.error(LoggingStrings.getUserWithEmailDoesntExists("com.vinsguru.grpc.controller.AggregatorController.displaySomeoneProfile", email, e.toString()));
        }
        return null;
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public UserDto getUserByEmail(@RequestHeader("Authorization") HttpHeaders header){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
            String email = tokenUtils.getUsernameFromToken(value);
            return aggregatorService.getUserByEmail(email); }
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.getUserByEmail", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return null;
    }
    @GetMapping("/educations/{email:.+}/")
    public List<EducationDto> getEducationsUserByEmail(@PathVariable("email")String email){
        if(Validation.validateEmail(email))
         return aggregatorService.getEducationsUserByEmail(email);
        return null;
    }

    @PostMapping("/workExperiences")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public String updateWorkExperiences(@RequestHeader("Authentication") HttpHeaders header, @RequestBody WorkExperienceDto workExperienceDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
                String email = tokenUtils.getUsernameFromToken(value);
                workExperienceDto.setEmail(email);
                return aggregatorService.updateWorkExperiences(workExperienceDto);
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.updateWorkExperiences", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "";
    }

    @GetMapping("/experiences/{email:.+}/")
    public List<WorkExperienceDto> getExperiencesByEmail(@PathVariable("email")String email){
        if(Validation.validateEmail(email))
         return aggregatorService.getExperiencesByEmail(email);
        return null;
    }

    @GetMapping("/getAllUsers")
    public List<DisplayUserDto> getUsers(){
        return aggregatorService.getUsers();
    }

    @GetMapping("/searchUsers/{param}")
    public List<DisplayUserDto> searchUsers(@PathVariable("param") String param){
        if(!Validation.validateNonBrackets(param))
            return aggregatorService.searchUsers(param);
        return null;
    }

    @PostMapping("/newPost")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public String addNewPost(@RequestHeader("Authentication") HttpHeaders header, @RequestBody PostDto post){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
                String email = tokenUtils.getUsernameFromToken(value);
                post.setEmail(email);
                if(post.getText()== null) post.setText("");
                if(post.getLink()== null) post.setLink("");
                return postService.addPost(post);
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.addNewPost", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "";
    }

    @PostMapping("/newFollower")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public String addNewFollower(@RequestHeader("Authentication") HttpHeaders header, @RequestBody FollowDto follow){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            String email = tokenUtils.getUsernameFromToken(value);
            follow.setFollowerEmail(email);
            return followerService.addFollower(follow);
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.addNewFollower", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "";
    }
    @PostMapping("/removeFollower")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public void removeFollower(@RequestHeader("Authentication") HttpHeaders header, @RequestBody FollowDto follow){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            String email = tokenUtils.getUsernameFromToken(value);
            follow.setFollowerEmail(email);
            followerService.removeFollower(follow);
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.removeFollower", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
    }

    @GetMapping("/followers/{email:.+}/")
    public List<FollowDto> getFollowers(@PathVariable("email")String email){
        if(Validation.validateEmail(email))
            return followerService.findPersonsFollowers(email);
        return null;
    }

    @GetMapping("/followings/{email:.+}/")
    public List<FollowDto> getFollowings(@PathVariable("email")String email){
        if(Validation.validateEmail(email))
            return followerService.findPersonsFollowings(email);
        return null;
    }

    @PostMapping("/comment")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public String addNewComment(@RequestHeader("Authentication") HttpHeaders header, @RequestBody CommentDto comment){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            String email = tokenUtils.getUsernameFromToken(value);
            comment.setCommentatorsEmail(email);
            return postService.addComment(comment);
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.addNewComment", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "";
    }
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @GetMapping("/requests")
    public List<FollowDto> requests(@RequestHeader("Authentication") HttpHeaders header){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            String email = tokenUtils.getUsernameFromToken(value);
            return followerService.findRequests(email);
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.requests", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return null;
    }

    @GetMapping("/getAllPosts")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/getAllUserPosts/{email:.+}/")
    public List<PostDto> getAllUserPosts(@PathVariable("email") String email){
        if(Validation.validateEmail(email))
            return postService.getAllUsersPosts(email);
        return null;
    }
    @PostMapping("/reaction")
    @PreAuthorize("hasRole('ROLE_REG_USER')")

    public String addNewReaction(@RequestHeader("Authentication") HttpHeaders header, @RequestBody ReactionDto reaction){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
            String email = tokenUtils.getUsernameFromToken(value);
            reaction.setEmail(email);
            return postService.addReaction(reaction);}
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.addNewReaction", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "";
    }

    @PostMapping("/deleteReaction")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public String deleteReaction(@RequestHeader("Authentication") HttpHeaders header, @RequestBody ReactionDto reaction){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
                System.out.println("Usao u if kontroler");
                String email = tokenUtils.getUsernameFromToken(value);
                reaction.setEmail(email);
                return postService.deleteReaction(reaction);
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.deleteReaction", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "";
    }

    @PostMapping("/answerFollowRequest")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public void answerFollowRequest(@RequestHeader("Authentication") HttpHeaders header, @RequestBody Map<String, String> message){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        boolean approved=true;
        try{
            if(!Validation.validateNonBrackets(value)){
                String email = tokenUtils.getUsernameFromToken(value);
                if(message.get("approved").equals("false"))
                    approved= false;
                followerService.answerFollowRequest(approved,email,message.get("followerEmail"));}
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.answerFollowRequest", tokenUtils.getUsernameFromToken(value), e.toString()));
        }

    }
    @PostMapping("/checkReaction")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public String checkReaction(@RequestHeader("Authentication") HttpHeaders header, @RequestBody Map<String, String> message){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
                String email = tokenUtils.getUsernameFromToken(value);
               return postService.checkReaction(message.get("postId"),email);}  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.checkReaction", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "";
    }
    @GetMapping("/postsForHomePage")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public List<PostDto> getPosts(@RequestHeader("Authentication") HttpHeaders header){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
                String email = tokenUtils.getUsernameFromToken(value);
                return postService.findAllPostsOfFollowingsByUserEmail(email);}
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.getPosts", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return null;
    }

    @PostMapping("/deleteEducation")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public boolean deleteEducation(@RequestHeader("Authentication") HttpHeaders header,@RequestBody EducationDto educationDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);

        if(!Validation.validateNonBrackets(value)) {
            String email = tokenUtils.getUsernameFromToken(value);
            return aggregatorService.deleteEducation(email,educationDto.getId());
        }
        return false;
    }
    @PostMapping("/deleteExperience")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public boolean deleteExperience(@RequestHeader("Authentication") HttpHeaders header,@RequestBody WorkExperienceDto workExperienceDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);

        if(!Validation.validateNonBrackets(value)) {
            String email = tokenUtils.getUsernameFromToken(value);
            return aggregatorService.deleteExperience(email,workExperienceDto.getId());
        }
        return false;
    }

    @PostMapping("/findReactionsByPostId")
    public List<ReactionDto> findReactionsByPostId(@RequestBody Map<String, String> postId){
        return postService.getReactionsByPostId(postId.get("id"));
    }

    @PostMapping("/findCommentsByPostId")
    public List<CommentDto> findCommentsByPostId(@RequestBody Map<String, String> postId){
        return postService.getCommentsByPostId(postId.get("id"));
    }

    @PostMapping("/numOfCommentsByPostId")
    public int findNumOfCommentsByPostId(@RequestBody Map<String, String> postId){
        return postService.getNumOfCommentsByPostId(postId.get("id"));
    }

    @PostMapping("/numOfReactionsByPostId")
    public int findNumOfReactionsByPostId(@RequestBody Map<String, String> postId){
        return postService.getNumOfReactionsByPostId(postId.get("id"));
    }

    @PostMapping("/forgottenPassword")
    public boolean forgottenPassword(@RequestBody Map<String, String> email){
        try{
            return aggregatorService.forgottenPassword(email);
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.forgottenPassword", tokenUtils.getUsernameFromToken(email.get("email")), e.toString()));
        }
        return false;
    }

    @GetMapping("/verifyAccount")
    public String verifyUser(@Param("code") String code){
        try{
            return aggregatorService.verifyAccount(code);
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.VerifyUser("com.vinsguru.grpc.controller.AggregatorController.verifyUser", e.toString()));
        }
        return "false";
    }

    private String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @PostMapping("/passwordlessLogin")
    public boolean passwordlessLogin(@RequestBody Map<String, String> email, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        try {
            return aggregatorService.passwordlessLogin(email, getSiteURL(request));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.passwordlessLogin", tokenUtils.getUsernameFromToken(email.get("email")), e.toString()));
        }
        return false;
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestHeader("Authentication") HttpHeaders header,@RequestBody Map<String, String> message){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
                String email = tokenUtils.getUsernameFromToken(value);
                message.put("email", email);
                if(Validation.validatePassword(message.get("newPassword"))){
                    return aggregatorService.changePassword(message);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.changePassword", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return "false";
    }

    @PostMapping("/createJobOffer")
    public boolean createJobOffer(@RequestBody JobOfferDto jobOfferDto){
        try{
            return jobOfferService.createJobOffer(jobOfferDto);
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessageAPIToken("com.vinsguru.grpc.controller.AggregatorController.createJobOffer", jobOfferDto.getUserAPItoken(), e.toString()));
        }
        return false;
    }

    @GetMapping("/searchJobOffers/{param}")
    public List<JobOfferDto> searchJobOffers(@PathVariable("param") String param){
        return jobOfferService.searchJobOffers(param);
    }

    @PostMapping("/generateUserAPIToken")
    public boolean generateUserAPIToken(@RequestHeader("Authentication")HttpHeaders header){
        String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try {
            boolean retVal = false;
            String email = tokenUtils.getUsernameFromToken(value);
            String token = tokenUtils.generateToken(email, "API");
            token = token.substring(8, 26);
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            token = token.concat(Base64.getUrlEncoder().encodeToString(bytes));
            retVal = aggregatorService.saveGeneratedToken(email, token);
            return retVal;
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.generateUserAPIToken", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return false;
    }

    @PostMapping("/checkIfUserIsFollowingOtherUser")
    public boolean checkIfUserIsFollowingOtherUser(@RequestHeader("Authentication") HttpHeaders header,@RequestBody Map<String, String> message){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);

        if(!Validation.validateNonBrackets(value)) {
            String myUserEmail = tokenUtils.getUsernameFromToken(value);
            return followerService.checkIfUserIsFollowingOtherUser(myUserEmail,message.get("otherUserEmail"));

        }
        return false;
    }

    @GetMapping("/getAllJobOffers")
    public List<JobOfferDto> getAllJobOffers(){
        return jobOfferService.getAllJobOffers();
    }

    @GetMapping("/getAllFeedPosts/{value}")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public List<PostDto> getAllFeedPosts(@RequestHeader("Authentication")HttpHeaders header, @PathVariable("value") String param){
        String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try {
            String email = tokenUtils.getUsernameFromToken(value);
            return postService.getAllFeedPosts(email, Integer.parseInt(param));
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessage("com.vinsguru.grpc.controller.AggregatorController.getAllFeedPosts", tokenUtils.getUsernameFromToken(value), e.toString()));
        }
        return new ArrayList<PostDto>();
    }

    @GetMapping("/checkIfForgottenPassword")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public boolean checkIfForgottenPassword(@RequestHeader("Authentication")HttpHeaders header){
        String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        String email = tokenUtils.getUsernameFromToken(value);
        return aggregatorService.checkForgottenPassword(email);
    }

    @PostMapping("/resendVerificationMail")
    public boolean resendVerificationMail(@RequestBody Map<String, String> message, HttpServletRequest request){
        try{
            return aggregatorService.resendVerificationMail(message, getSiteURL(request));
        }catch(Exception e){
            e.printStackTrace();
            log.error(LoggingStrings.getLoggedMessageResendVerificationMail("com.vinsguru.grpc.controller.AggregatorController.resendVerificationMail", tokenUtils.getUsernameFromToken(message.get("email")), e.toString()));
        }
        return false;
    }
}
