package com.vinsguru.grpc.controller;

import com.vinsguru.grpc.dto.*;

import com.vinsguru.grpc.helperModel.User;
import com.vinsguru.grpc.helperModel.UserTokenState;
import com.vinsguru.grpc.security.TokenUtils;
import com.vinsguru.grpc.security.auth.JwtAuthenticationRequest;
import com.vinsguru.grpc.service.UsersService;
import com.vinsguru.grpc.service.FollowerService;
import com.vinsguru.grpc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public String addUser(@RequestBody Map<String, String> message){
        return aggregatorService.addUser(message);
    }

    @GetMapping("/invalidateUser")
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
        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping("/personalInfo")
    public String updateUser(@RequestBody Map<String, String> userDto){
       return aggregatorService.updateUser(userDto);
    }

    @PostMapping("/education")
    public String updateEducation(@RequestBody EducationDto educationDto){
        return aggregatorService.updateEducation(educationDto);
    }
    @GetMapping("/user/{email:.+}/")
    public UserDto getUserByEmail(@PathVariable("email")String email){
        return aggregatorService.getUserByEmail(email);
    }
    @GetMapping("/educations/{email:.+}/")
    public List<EducationDto> getEducationsUserByEmail(@PathVariable("email")String email){
        return aggregatorService.getEducationsUserByEmail(email);
    }
    @PostMapping("/workExperiences")
    public String updateWorkExperiences(@RequestBody WorkExperienceDto workExperienceDto){
        return aggregatorService.updateWorkExperiences(workExperienceDto);
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
    public String addNewPost(@RequestBody PostDto post){
        return postService.addPost(post);
    }

    @PostMapping("/newFollower")
    public String addNewFollower(@RequestBody FollowDto follow){
        return followerService.addFollower(follow);
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
    public String addNewComment(@RequestBody CommentDto comment){
        return postService.addComment(comment);
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
    public String addNewReaction(@RequestBody ReactionDto reaction){
        return postService.addReaction(reaction);
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
