package com.example.postservice.repository;

import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.model.Reaction;
import com.example.postservice.model.ReactionType;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.sql.Ref;
import java.util.*;

public class PostRepository {
    final static Class<? extends List> docClazz = new ArrayList<Document>().getClass();
    private static PostRepository instance = null;

    public static PostRepository getInstance(){
        if(instance == null)
            instance = new PostRepository();

        return instance;
    }

    private MongoClient mongoClient;
    private MongoDatabase postsDatabase;
    private MongoCollection<Document> postsCollection;

    private PostRepository(){
        String connectionString = "mongodb://localhost:27017";
        try {
            mongoClient = MongoClients.create(connectionString);
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            postsDatabase = mongoClient.getDatabase("posts");
            postsCollection = postsDatabase.getCollection("posts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(Post post){
        Document postToSave = new Document("_id", new ObjectId());
        postToSave.append("usersId", post.getUsersId())
                .append("text", post.getText())
                .append("pathToImage", post.getPathToImage())
                .append("link", post.getLink())
                .append("comments",post.getComments())
                .append("reactions",post.getReactions())
                .append("date", post.getDate());

        postsCollection.insertOne(postToSave);
    }

    public List<Post> getAllPosts(){
        FindIterable<Document> iterable = postsCollection.find();
        List<Post> retVal = new ArrayList<Post>();
        for(Document d : iterable){
            Post p = new Post();
            p.setId(d.getObjectId("_id"));
            p.setUsersId(d.getString("usersId"));
            p.setLink(d.getString("link"));
            p.setPathToImage(d.getString("pathToImage"));
            p.setText(d.getString("text"));
            p.setDate(d.getDate("date"));
            retVal.add(p);
        }
        return retVal;
    }


    public void addComment(String postId, Comment comment){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        Document newComment = new Document("_idComment", new ObjectId());
        newComment.append("text",comment.getText()).append("commentatorsId",comment.getCommentatorsId());
        Bson updates = Updates.combine(
                Updates.addToSet("comments",newComment)
        );
        UpdateOptions options = new UpdateOptions().upsert(true);
        postsCollection.updateOne(foundPost, updates, options);
    }

    public void addReaction(String postId, Reaction reaction){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        Document react = new Document("_idReaction", new ObjectId());
        react.append("usersId", reaction.getUsersId()).append("reactionType", reaction.getReaction());
        Bson updates = Updates.combine(  Updates.addToSet("reactions", react));
        UpdateOptions options = new UpdateOptions().upsert(true);
        postsCollection.updateOne(foundPost, updates, options);
    }

    public List<Post> findPostsByUserId(String userId) {
        FindIterable<Document> foundPosts = postsCollection.find(Filters.eq("usersId", userId));
        List<Post> posts = new ArrayList<>();
        for(Document doc: foundPosts)
            posts.add(new Post(doc.getObjectId("_id"),doc.getString("usersId"),doc.getString("text"),
                    doc.getString("pathToImage"),doc.getString("link"),doc.getDate("date")));
        return posts;
    }

    public void deleteReaction(String postId,Reaction reaction){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        List<Document> reactionsDocuments =foundPost.get("reactions",docClazz);
        Document newReaction= null;
        for(Document doc: reactionsDocuments){
            if(doc.getString("usersId").equals(reaction.getUsersId())){
                newReaction = doc;
                break;
            }
        }
        Bson updates = Updates.combine( Updates.pull("reactions",newReaction));
        postsCollection.updateOne(foundPost, updates);
    }
    public int getNumOfCommentsByPostId(String postId){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        List<Document> educationsDocuments =foundPost.get("comments",docClazz);
        return educationsDocuments.size();
    }

    public int getNumOfReactionsByPostId(String postId){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        List<Document> educationsDocuments =foundPost.get("reactions",docClazz);
        return educationsDocuments.size();
    }

    public List<Reaction> getAllReactionByPostId(String postId){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        List<Document> reactionsDocuments =foundPost.get("reactions",docClazz);
        ReactionType reaction = ReactionType.LIKE;
        List<Reaction> likes = new ArrayList<>();
        for(Document doc: reactionsDocuments){
            if(doc.getString("reactionType").equals("DISLIKE"))
                     reaction = ReactionType.DISLIKE;
                likes.add(new Reaction(doc.getObjectId("_idReaction"),doc.getString("usersId"),reaction));
        }
        return likes;
    }

    public String checkReaction(String postId,String userId){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        List<Document> reactionsDocuments =foundPost.get("reactions",docClazz);
        for(Document doc: reactionsDocuments){
            if(doc.getString("usersId").equals(userId)){
                if(doc.getString("reactionType").equals("DISLIKE"))
                    return ReactionType.DISLIKE.toString();
                else if(doc.getString("reactionType").equals("LIKE"))
                    return ReactionType.LIKE.toString();
            }
        }
        return  "NONE";
    }

    public List<Comment> getAllCommentsByPostId(String postId){
        Document foundPost = postsCollection.find(Filters.eq("_id", new ObjectId(postId))).first();
        List<Document> commentsDocuments =foundPost.get("comments",docClazz);
        List<Comment> comments = new ArrayList<>();
        for(Document doc: commentsDocuments)
            comments.add(new Comment(doc.getObjectId("_idComment"),doc.getString("text"),doc.getString("commentatorsId")));
        return comments;
    }
}
