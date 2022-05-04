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
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

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
                .append("reactions",post.getReactions());

        postsCollection.insertOne(postToSave);
    }

    public List<Post> getAllPosts(){
        FindIterable<Document> iterable = postsCollection.find();
        List<Post> retVal = new ArrayList<Post>();
        for(Document d : iterable){
            Post p = new Post();
            p.setId(d.getObjectId("id"));
            p.setLink(d.getString("link"));
            p.setPathToImage(d.getString("pathToImage"));
            p.setText(d.getString("text"));
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

        Document newReaction = new Document("_idReaction", new ObjectId());
        newReaction.append("usersId",reaction.getUsersId()).append("reactionType",reaction.getReaction());

        Bson updates = Updates.combine(
                Updates.addToSet("reactions",newReaction)
        );

        UpdateOptions options = new UpdateOptions().upsert(true);
        postsCollection.updateOne(foundPost, updates, options);
    }


    public List<Post> findPostsByUserId(String userId) {
        FindIterable<Document> foundPosts = postsCollection.find(Filters.eq("usersId", userId));
        System.out.println("USAOOOOO U METODUUUUUU" +userId);

        List<Post> posts = new ArrayList<>();

        for(Document doc: foundPosts){
            posts.add(new Post(doc.getObjectId("_id"),doc.getString("usersId"),doc.getString("text"),doc.getString("pathToImage"),doc.getString("link"),doc.getDate("date")));
            System.out.println("POSTTTTTT"+doc.getString("text"));
        }
        return posts;
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
}
