package com.vinsguru.grpc.controller;

import com.vinsguru.grpc.dto.*;

import com.vinsguru.grpc.service.UsersService;
import com.vinsguru.grpc.service.FollowerService;
import com.vinsguru.grpc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public String addUser(@RequestBody Map<String, String> message){
        return aggregatorService.addUser(message);
    }

    @GetMapping("/invalidateUser")
    public String invalidateUser(){
        return aggregatorService.invalidateUser("");
    }

    @PostMapping("/logInUser")
    public String logInUser(@RequestBody Map<String, String> message){
        return aggregatorService.logInUser(message);
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
    public List<UserDto> getUsers(){
        return aggregatorService.getUsers();
    }

    @GetMapping("/searchUsers/{param}")
    public List<UserDto> searchUsers(@PathVariable("param") String param){
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
