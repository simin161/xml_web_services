package com.example.postservice.service;

import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.model.Reaction;
import com.example.postservice.model.ReactionType;
import com.example.postservice.repository.PostRepository;
import com.example.postservice.utility.MicroserviceConnection;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.ArrayList;

import proto.follow.FollowServiceGrpc;
import proto.follow.Followers;
import proto.follow.InputEmail;
import proto.post.*;
import proto.user.InputForGetUserByEmail;
import proto.user.OutputId;
import proto.user.UserServiceGrpc;

import java.util.Date;
import java.util.List;

@GrpcService
public class PostService extends PostServiceGrpc.PostServiceImplBase {

    private final MicroserviceConnection msConnection = new MicroserviceConnection();
    @Override
    public void addPost(InputAddPost request, StreamObserver<OutputAddPost> responseObserver) {

        OutputAddPost output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId= blockingStub.findUserIdByEmail(input).getUsersId();
        if(usersId != null){
            PostRepository.getInstance().insert(new Post(usersId,request.getText(),"request.getPathToImage()",request.getLink(),new ArrayList<>(),new ArrayList<>(),new Date()));
            output = OutputAddPost.newBuilder().setResult("success").build();
        }else {
            output = OutputAddPost.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }


    @Override
    public void addComment(InputAddComment request, StreamObserver<Output> responseObserver) {
        Output output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId = blockingStub.findUserIdByEmail(input).getUsersId();

        if (usersId != null) {
            blockingStub.checkIfAccountIsPrivate(input).getPrivate();
            PostRepository.getInstance().addComment(request.getPostId(), new Comment(request.getText(), usersId));
            output = Output.newBuilder().setResult("success").build();
        } else {
            output = Output.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void getAllPosts(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<proto.post.AllPosts> responseObserver){
        List<Post> documentedPosts = PostRepository.getInstance().getAllPosts();
        List<PostToShow> iaps = new ArrayList<PostToShow>();

        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        try {
            for (Post p : documentedPosts) {
                OutputId userId = OutputId.newBuilder().setUsersId(p.getUsersId()).build();
                String email = blockingStub.findUserEmailById(userId).getEmail();
                PostToShow iap = PostToShow.newBuilder().setPathToImage(p.getPathToImage()).setText(p.getText())
                        .setLink(p.getLink()).setEmail(email).setPostId(p.getId().toString()).setDate(p.getDate().toString()).build();
                iaps.add(iap);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        AllPosts allPosts;
        allPosts = AllPosts.newBuilder().addAllAllPosts(iaps).build();
        responseObserver.onNext(allPosts);
        responseObserver.onCompleted();

    }
    @Override
    public void getAllUserPosts(UserEmail request, StreamObserver<AllPosts> responseObserver){
        List<Post> documentedPosts = PostRepository.getInstance().getAllPosts();
        List<PostToShow> iaps = new ArrayList<>();
        System.out.println("USER EMAIL"+request.getEmail());
        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        try {
            for (Post p : documentedPosts) {
                OutputId userId = OutputId.newBuilder().setUsersId(p.getUsersId()).build();
                String email = blockingStub.findUserEmailById(userId).getEmail();
                System.out.println("USER EMAIL"+email);
                if (request.getEmail().equals(email)) {
                    PostToShow iap = PostToShow.newBuilder().setPathToImage(p.getPathToImage()).setText(p.getText())
                            .setLink(p.getLink()).setEmail(email).setPostId(p.getId().toString()).setDate(p.getDate().toString()).build();
                    iaps.add(iap);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        AllPosts allUserPosts;
        allUserPosts = AllPosts.newBuilder().addAllAllPosts(iaps).build();
        responseObserver.onNext(allUserPosts);
        responseObserver.onCompleted();
    }

    @Override
    public void addReaction(InputAddReaction request, StreamObserver<Output> responseObserver) {
        ReactionType reactionType;
        Output output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId= blockingStub.findUserIdByEmail(input).getUsersId();
        if(request.getReactionType().equals("LIKE")){
            reactionType = ReactionType.LIKE;
        }else {
            reactionType = ReactionType.DISLIKE;
        }

        if(usersId != null ){

            PostRepository.getInstance().addReaction(request.getPostId(),new Reaction(usersId,reactionType));
            output = Output.newBuilder().setResult("success").build();
        }else {
            output = Output.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void findAllPostsOfFollowingsByUserEmail(Input request, StreamObserver<OutputPosts> responseObserver) {
        OutputPosts output;

        FollowServiceGrpc.FollowServiceBlockingStub blockingFollowStub =   msConnection.setUpCommunicationPostFollower();
        InputEmail input = InputEmail.newBuilder().setEmail(request.getEmail()).build();
        List<Followers> followings =blockingFollowStub.findPersonsFollowings(input).getFollowersList();
        List<OutputPost> posts = new ArrayList<>();
        for(Followers follower : followings){
             for(Post post: PostRepository.getInstance().findPostsByUserId(follower.getPersonEmail())){
                 OutputPost outputPost = OutputPost.newBuilder().setPostId(post.getId().toString()).setText(post.getText())
                         .setUsersId(post.getUsersId()).setLink(post.getLink()).setPathToImage(post.getPathToImage()).build();
                 posts.add(outputPost);
         }
        }
        output = OutputPosts.newBuilder().addAllPosts(posts).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void getNumOfCommentsByPostId(InputPostId request, StreamObserver<OutputNumber> responseObserver) {
        OutputNumber output;
       int num = PostRepository.getInstance().getNumOfCommentsByPostId(request.getPostId());

       output = OutputNumber.newBuilder().setNum(num).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void getNumOfReactionsByPostId(InputPostId request, StreamObserver<OutputNumber> responseObserver) {
        OutputNumber output;
        int num = PostRepository.getInstance().getNumOfReactionsByPostId(request.getPostId());
        output = OutputNumber.newBuilder().setNum(num).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }
}
