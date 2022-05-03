package com.example.postservice.repository;

import com.example.postservice.model.Post;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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
                .append("link", post.getLink());

        postsCollection.insertOne(postToSave);
    }


}
