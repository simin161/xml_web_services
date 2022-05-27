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
import com.vinsguru.grpc.utility.Validation;
import org.json.JSONException;
import org.json.JSONObject;
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
                //e.printStackTrace();
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
            return aggregatorService.updateUser(userDto);}   //note: na frontu skloniti mejl iz userdto da se prosledjuje
        }catch(Exception e){
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
            return aggregatorService.updateEducation(educationDto);  } //note: na frontu skloniti mejl iz educationdto da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";

    }
    @GetMapping("/user/{email:.+}/")
    public UserDto getUserByEmail(@PathVariable("email")String email){
        if(Validation.validateEmail(email))
            return aggregatorService.getUserByEmail(email);
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
            return aggregatorService.getUserByEmail(email); }  //note: na frontu skloniti mejl iz educationdto da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
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
            }   //note: na frontu skloniti mejl iz educationdto da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
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
                System.out.println("DOBIOOOOOO"+post.getPathToImage());
               // System.out.println("Sta je ovo***"+System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf("/")));

                return postService.addPost(post);
            }  //note: na frontu skloniti mejl iz post da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
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
            return followerService.addFollower(follow);  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
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
            return postService.addComment(comment);  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @GetMapping("/requests")
    public List<FollowDto> requests(@RequestHeader("Authentication") HttpHeaders header){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            String email = tokenUtils.getUsernameFromToken(value);
            return followerService.findRequests(email);  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
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
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public String addNewReaction(@RequestHeader("Authentication") HttpHeaders header, @RequestBody ReactionDto reaction){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            if(!Validation.validateNonBrackets(value)){
            String email = tokenUtils.getUsernameFromToken(value);
            reaction.setEmail(email);
            return postService.addReaction(reaction);}  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
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
                followerService.answerFollowRequest(approved,email,message.get("followerEmail"));}  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @GetMapping("/postsForHomePage")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public List<PostDto> getPosts(@RequestHeader("Authentication") HttpHeaders header){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);

        if(!Validation.validateNonBrackets(value)){
        String email = tokenUtils.getUsernameFromToken(value);
        return postService.findAllPostsOfFollowingsByUserEmail(email);}
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
        return aggregatorService.forgottenPassword(email);
    }

    @GetMapping("/verifyAccount")
    public String verifyUser(@Param("code") String code){
        return aggregatorService.verifyAccount(code);
    }

    private String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @PostMapping("/passwordlessLogin")
    public boolean passwordlessLogin(@RequestBody Map<String, String> email, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        return aggregatorService.passwordlessLogin(email, getSiteURL(request));
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

        }catch(Exception e){}
        return "false";
    }

    @PostMapping("/createJobOffer")
    public boolean createJobOffer(@RequestBody JobOfferDto jobOfferDto){
        return jobOfferService.createJobOffer(jobOfferDto);
    }

    @GetMapping("/searchJobOffers/{param}")
    public List<JobOfferDto> searchJobOffers(@PathVariable("param") String param){
        return jobOfferService.searchJobOffers(param);
    }

    @PostMapping("/generateUserAPIToken")
    public boolean generateUserAPIToken(@RequestHeader("Authentication")HttpHeaders header){
        boolean retVal = false;
        String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        String email = tokenUtils.getUsernameFromToken(value);
        String token = tokenUtils.generateToken(email, "API");
        token = token.substring(8, 26);
        SecureRandom random = new SecureRandom();
        byte []bytes = new byte[20];
        random.nextBytes(bytes);
        token = token.concat(Base64.getUrlEncoder().encodeToString(bytes));
        retVal = aggregatorService.saveGeneratedToken(email, token);
        return retVal;
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
}
