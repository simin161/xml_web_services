package com.example.postservice.repository;

import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.model.Reaction;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

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
                .append("date",post.getDate());

        postsCollection.insertOne(postToSave);
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

        Document newReaction = new Document("_idReaction", new ObjectId());
        newReaction.append("usersId",reaction.getUsersId()).append("reactionType",reaction.getReaction());

        Bson updates = Updates.combine(
                Updates.addToSet("reactions",newReaction)
        );

        UpdateOptions options = new UpdateOptions().upsert(true);
        postsCollection.updateOne(foundPost, updates, options);
    }

}
