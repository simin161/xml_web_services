package com.vinsguru.grpc.controller;

import com.google.api.Http;
import com.vinsguru.grpc.dto.*;

import com.vinsguru.grpc.helperModel.User;
import com.vinsguru.grpc.helperModel.UserTokenState;
import com.vinsguru.grpc.security.TokenUtils;
import com.vinsguru.grpc.security.auth.JwtAuthenticationRequest;
import com.vinsguru.grpc.service.UsersService;
import com.vinsguru.grpc.service.FollowerService;
import com.vinsguru.grpc.service.PostService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


    @PostMapping("/register")
    public ResponseEntity<UserTokenState> addUser(@RequestBody Map<String, String> message){
        String registered = aggregatorService.addUser(message);
        if(!registered.equals("false")){
            JwtAuthenticationRequest cred = new JwtAuthenticationRequest();
            cred.setPassword(message.get("password"));
            cred.setEmail(message.get("email"));
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
        return null;
    }

    @GetMapping("/invalidateUser")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public String invalidateUser(){
        return aggregatorService.invalidateUser("");
    }

    @PostMapping("/logInUser")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest cred,
                                                                    HttpServletResponse response) {

        System.out.println(cred);

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

    @PostMapping("/personalInfo")
    public String updateUser(@RequestHeader("Authentication") HttpHeaders header, @RequestBody Map<String, String> userDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            final JSONObject obj = new JSONObject(value);
            String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(user);
            userDto.put("email",email);
            return aggregatorService.updateUser(userDto);   //note: na frontu skloniti mejl iz userdto da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("/education")
    public String updateEducation(@RequestHeader("Authentication") HttpHeaders header, @RequestBody EducationDto educationDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            final JSONObject obj = new JSONObject(value);
            String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(user);
            educationDto.setEmail(email);
            return aggregatorService.updateEducation(educationDto);   //note: na frontu skloniti mejl iz educationdto da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";

    }
    @GetMapping("/user/{email:.+}/")
    public UserDto getUserByEmail(@PathVariable("email")String email){
        return aggregatorService.getUserByEmail(email);
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_REG_USER')")
    public UserDto getUserByEmail(@RequestHeader("Authorization") HttpHeaders header){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
          //  final JSONObject obj = new JSONObject(value);
          //  String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(value);
            return aggregatorService.getUserByEmail(email);   //note: na frontu skloniti mejl iz educationdto da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping("/educations/{email:.+}/")
    public List<EducationDto> getEducationsUserByEmail(@PathVariable("email")String email){
        return aggregatorService.getEducationsUserByEmail(email);
    }

    @PostMapping("/workExperiences")
    public String updateWorkExperiences(@RequestHeader("Authentication") HttpHeaders header, @RequestBody WorkExperienceDto workExperienceDto){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            final JSONObject obj = new JSONObject(value);
            String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(user);
            workExperienceDto.setEmail(email);
            return aggregatorService.updateWorkExperiences(workExperienceDto);   //note: na frontu skloniti mejl iz educationdto da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/experiences/{email:.+}/")
    public List<WorkExperienceDto> getExperiencesByEmail(@PathVariable("email")String email){
        return aggregatorService.getExperiencesByEmail(email);
    }

    @GetMapping("/getAllUsers")
    public List<DisplayUserDto> getUsers(){
        return aggregatorService.getUsers();
    }

    @GetMapping("/searchUsers/{param}")
    public List<DisplayUserDto> searchUsers(@PathVariable("param") String param){
        return aggregatorService.searchUsers(param);
    }

    @PostMapping("/newPost")
    public String addNewPost(@RequestHeader("Authentication") HttpHeaders header, @RequestBody PostDto post){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            final JSONObject obj = new JSONObject(value);
            String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(user);
            post.setEmail(email);
            return postService.addPost(post);   //note: na frontu skloniti mejl iz post da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("/newFollower")
    public String addNewFollower(@RequestHeader("Authentication") HttpHeaders header, @RequestBody FollowDto follow){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            final JSONObject obj = new JSONObject(value);
            String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(user);
            follow.setFollowerEmail(email);
            return followerService.addFollower(follow);  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/followers/{email:.+}/")
    public List<FollowDto> getFollowers(@PathVariable("email")String email){
        return followerService.findPersonsFollowers(email);
    }

    @GetMapping("/followings/{email:.+}/")
    public List<FollowDto> getFollowings(@PathVariable("email")String email){
        return followerService.findPersonsFollowings(email);
    }

    @PostMapping("/comment")
    public String addNewComment(@RequestHeader("Authentication") HttpHeaders header, @RequestBody CommentDto comment){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            final JSONObject obj = new JSONObject(value);
            String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(user);
            comment.setCommentatorsEmail(email);
            return postService.addComment(comment);  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/getAllPosts")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/getAllUserPosts/user:{email}")
    public List<PostDto> getAllUserPosts(@PathVariable("email") String email){
        return postService.getAllUsersPosts(email);
    }
    @PostMapping("/reaction")
    public String addNewReaction(@RequestHeader("Authentication") HttpHeaders header, @RequestBody ReactionDto reaction){
        final String value = header.getFirst(HttpHeaders.AUTHORIZATION);
        try{
            final JSONObject obj = new JSONObject(value);
            String user = obj.getString("accessToken");
            String email = tokenUtils.getUsernameFromToken(user);
            reaction.setEmail(email);
            return postService.addReaction(reaction);  //note: na frontu skloniti mejl da se prosledjuje
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/postsForHomePage/{email:.+}/")
    public List<PostDto> getPosts(@PathVariable("email")String email){
        return postService.findAllPostsOfFollowingsByUserEmail(email);
    }

    @PostMapping("/numOfCommentsByPostId")
    public int findNumOfCommentsByPostId(@RequestBody Map<String, String> postId){
        return postService.getNumOfCommentsByPostId(postId.get("id"));
    }

    @PostMapping("/numOfReactionsByPostId")
    public int findNumOfReactionsByPostId(@RequestBody Map<String, String> postId){
        return postService.getNumOfReactionsByPostId(postId.get("id"));
    }
}
