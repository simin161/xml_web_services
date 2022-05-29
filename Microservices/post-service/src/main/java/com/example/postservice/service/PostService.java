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
import proto.user.InputID;
import proto.user.OutputId;
import proto.user.UserServiceGrpc;
import java.util.UUID;
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
        String image= "";

        if(usersId != null){
            try {
                image=ImageService.getInstance().saveImage(request.getPathToImage(),UUID.randomUUID().toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
            PostRepository.getInstance().insert(new Post(usersId,request.getText(),image,request.getLink(),new ArrayList<>(),new ArrayList<>(),new Date()));
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
    public void getAllPosts(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<proto.post.AllPosts> responseObserver) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllPosts allPosts;
        allPosts = AllPosts.newBuilder().addAllAllPosts(iaps).build();
        responseObserver.onNext(allPosts);
        responseObserver.onCompleted();

    }

    @Override
    public void getAllUserPosts(UserEmail request, StreamObserver<AllPosts> responseObserver) {
        List<Post> documentedPosts = PostRepository.getInstance().getAllPosts();
        List<PostToShow> iaps = new ArrayList<>();
        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        try {
            for (Post p : documentedPosts) {
                OutputId userId = OutputId.newBuilder().setUsersId(p.getUsersId()).build();
                String email = blockingStub.findUserEmailById(userId).getEmail();
                if (request.getEmail().equals(email)) {
                    PostToShow iap = PostToShow.newBuilder().setPathToImage(p.getPathToImage()).setText(p.getText())
                            .setLink(p.getLink()).setEmail(email).setPostId(p.getId().toString()).setDate(p.getDate().toString()).build();
                    iaps.add(iap);
                }
            }
        } catch (Exception e) {
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
        String usersId = blockingStub.findUserIdByEmail(input).getUsersId();
        if (request.getReactionType().equals("LIKE")) reactionType = ReactionType.LIKE;
        else  reactionType = ReactionType.DISLIKE;

        if (usersId != null) {
            PostRepository.getInstance().addReaction(request.getPostId(), new Reaction(usersId, reactionType));
            output = Output.newBuilder().setResult("success").build();
        } else {
            output = Output.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void findAllPostsOfFollowingsByUserEmail(Input request, StreamObserver<OutputPosts> responseObserver) {
        OutputPosts output;
        FollowServiceGrpc.FollowServiceBlockingStub blockingFollowStub = msConnection.setUpCommunicationPostFollower();
        InputEmail input = InputEmail.newBuilder().setEmail(request.getEmail()).build();
        List<Followers> followings = blockingFollowStub.findPersonsFollowingsIds(input).getFollowersList();

        List<OutputPost> posts = new ArrayList<>();
        for (Followers follower : followings) {
            for (Post post : PostRepository.getInstance().findPostsByUserId(follower.getPersonEmail())) {
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

    @Override
    public void getCommentsByPostId(InputPostId request, StreamObserver<OutputComments> responseObserver) {
        OutputComments output;
        List<Comment> comments = PostRepository.getInstance().getAllCommentsByPostId(request.getPostId());
        List<OutputComment> retComments = new ArrayList<>();
        for (Comment comment : comments) {
            UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
            InputID input = InputID.newBuilder().setId(comment.getCommentatorsId()).build();
            String username = blockingStub.getUserById(input).getUsername();
            OutputComment outputComment = OutputComment.newBuilder().setIdComment(comment.getIdComment().toString()).setText(comment.getText()).setCommentatorsId(username).build();
            retComments.add(outputComment);
        }
        output =OutputComments.newBuilder().addAllComments(retComments).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
}


    @Override
    public void getReactionsByPostId(InputPostId request, StreamObserver<OutputReactions> responseObserver) {
        OutputReactions output;
        List<Reaction> reactions = PostRepository.getInstance().getAllReactionByPostId(request.getPostId());
        List<OutputReaction> retReactions = new ArrayList<>();
        for (Reaction reaction : reactions) {
            UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
            InputID input = InputID.newBuilder().setId(reaction.getUsersId()).build();
            String username = blockingStub.getUserById(input).getUsername();
            OutputReaction outputReaction = OutputReaction.newBuilder().setIdReaction(reaction.getIdReaction().toString()).setReaction(reaction.getReaction().toString()).setUsersId(username).build();
            retReactions.add(outputReaction);
        }
        output =OutputReactions.newBuilder().addAllReactions(retReactions).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void checkReaction(InputCheck request, StreamObserver<OutputCheck> responseObserver) {
        OutputCheck output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId = blockingStub.findUserIdByEmail(input).getUsersId();
        String ret = PostRepository.getInstance().checkReaction(request.getPostId(),usersId);
        output = OutputCheck.newBuilder().setCheck(ret).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }
    @Override
    public void deleteReaction(InputAddReaction request, StreamObserver<Output> responseObserver) {
        ReactionType reactionType;
        Output output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = msConnection.setUpCommunicationPostUser();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId = blockingStub.findUserIdByEmail(input).getUsersId();
        if (request.getReactionType().equals("LIKE")) {
            reactionType = ReactionType.LIKE;
        } else { reactionType = ReactionType.DISLIKE;}
        if (usersId != null) {
            PostRepository.getInstance().deleteReaction(request.getPostId(), new Reaction(usersId, reactionType));
            output = Output.newBuilder().setResult("success").build();
        } else {output = Output.newBuilder().setResult("Bad request").build();}
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }
}
